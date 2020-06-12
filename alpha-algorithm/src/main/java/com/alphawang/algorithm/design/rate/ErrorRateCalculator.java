package com.alphawang.algorithm.design.rate;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ErrorRateCalculator {
    public static void main(String[] args) throws Exception {
        IServiceImpl iService = new IServiceImpl();
        for (int i = 0; i < 10; i++) {
            iService.test();
//            Thread.sleep(ThreadLocalRandom.current().nextInt(100, 300));
        }
    }
}

class IServiceImpl {

    private LoadingCache<Integer, Stats> statsCache = CacheBuilder
      .newBuilder()
//      .expireAfterAccess(15, TimeUnit.SECONDS)
//      .expireAfterWrite(15, TimeUnit.SECONDS)
      .maximumSize(100)
      .build(new CacheLoader<Integer, Stats>() {
          @Override
          public Stats load(Integer integer) throws Exception {
              return new Stats(integer);
          }
      });

    public void test() {
        int currSecode = (int) (System.currentTimeMillis() / 1000);
        int status = call();
        try {
            Stats stats = statsCache.get(currSecode);
            if (status == 200) {
                stats.getSuccess().incrementAndGet();
            } else {
                stats.getFail().incrementAndGet();
            }
            printPrevStats(currSecode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printPrevStats(int currSecode) throws Exception {
        int total = 0;
        int fail = 0;
        for (int i = currSecode - 1; i > currSecode - 10000; i--) {
            Stats stats = statsCache.get(i);
            total += stats.getSuccess().get() + stats.getFail().get();
            fail += stats.getFail().get();

            System.out.println(String.format("Cache %s = %s", i, stats));
        }
        
        
        System.out.println(String.format("Last 10 seconds, total=%d, fail=%d, failRate=%d \n", total, fail,
                                         total == 0 ? 0 : fail * 100 / total));
    }

    private int call() {
        return ThreadLocalRandom.current().nextInt(0, 2) == 0 ? 200 : 500;
    }

    private static class Stats {
        
        @Override
        public String toString() {
            return String.format("Time %s : %s - %s", second, fail.get(), success.get());
        }

        private int second;
        private AtomicInteger success = new AtomicInteger(0);
        private AtomicInteger fail = new AtomicInteger(0);

        public Stats() {
        }

        public Stats(int second) {
            this.second = second;
        }

        public int getSecond() {
            return second;
        }

        public void setSecond(int second) {
            this.second = second;
        }

        public AtomicInteger getSuccess() {
            return success;
        }

        public AtomicInteger getFail() {
            return fail;
        }
    }

}
