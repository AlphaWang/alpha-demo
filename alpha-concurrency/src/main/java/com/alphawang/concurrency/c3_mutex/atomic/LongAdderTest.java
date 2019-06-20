package com.alphawang.concurrency.c3_mutex.atomic;

import com.alphawang.concurrency.common.annotations.ThreadSafe;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

@Slf4j
@ThreadSafe
public class LongAdderTest {

    private static final int TOTAL = 100;
    private static final int CONCURRENT_LEVEL = 5;

    public static void main(String[] args) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        LongAdder longAdder = new LongAdder();
        log.info("[{}] ---- START ", stopwatch.elapsed(TimeUnit.MILLISECONDS));

        ExecutorService executorService = Executors.newCachedThreadPool();

        Semaphore semaphore = new Semaphore(CONCURRENT_LEVEL);
        CountDownLatch countDownLatch = new CountDownLatch(TOTAL);

        for (int i = 0; i < TOTAL; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    test(stopwatch, longAdder);
                    semaphore.release();

                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("countDownLatch.await() ERROR. " + longAdder.sum());
            e.printStackTrace();
        }

        log.error("[{}] ------ LongAdder.sum {}", stopwatch.elapsed(TimeUnit.MILLISECONDS), longAdder.sum());

        executorService.shutdown();
        stopwatch.stop();
    }

    private static void test(Stopwatch stopwatch, LongAdder longAdder) throws InterruptedException {
        Thread.sleep(500);

        longAdder.increment();
        log.info("[{}] LongAdder.increment {} ", stopwatch.elapsed(TimeUnit.MILLISECONDS), longAdder.longValue());
    }
}
