package com.alphawang.concurrency.c1_work.future;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CompletionServiceForkingTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        CompletionService<Integer> cs = new ExecutorCompletionService<>(executorService);
        List<Future<Integer>> futures = Lists.newArrayList();

        futures.add(cs.submit(() -> {
            log.warn("Service 1: start.");
            sleep(500);
            log.warn("Service 1: finish.");

            return 333;
        }));

        futures.add(cs.submit(() -> {
            log.warn("Service 2: start.");
            sleep(10000);
            log.warn("Service 2: finish.");

            return 222;
        }));

        futures.add(cs.submit(() -> {
            log.warn("Service 3: start.");
            sleep(700);
            log.warn("Service 3: finish.");

            return 111;
        }));

        /**
         * 获取到任意一个返回后，即结束
         */
        Integer result = null;
        try {
            for (int i = 0; i < 3; i++) {
                result = cs.take().get();
                if (result != null) {
                    break;
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            for (Future<Integer> f : futures) {
                f.cancel(true);
            }
        }

        log.warn("RESULT: " + result);

    }

    private static void sleep(int timeout) {
        try {
            TimeUnit.MILLISECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
