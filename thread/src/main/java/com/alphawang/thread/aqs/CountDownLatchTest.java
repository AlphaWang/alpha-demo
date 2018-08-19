package com.alphawang.thread.aqs;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * 23:27:50 [main] ERROR c.a.c.aqs.CountDownLatchTest - [2069] ------ END action 15
 * 会在timeout到期后执行；而非所有任务结束后执行。
 */
@Slf4j
public class CountDownLatchTest {

    private static final int TOTAL = 100;
    private static final int CONCURRENT_LEVEL = 5;

    private static LongAdder longAdder = new LongAdder();
    private static Stopwatch stopwatch = Stopwatch.createStarted();

    public static void main(String[] args) throws InterruptedException {

        log.info("[{}] ----- START ", stopwatch.elapsed(TimeUnit.MILLISECONDS));

        ExecutorService executorService = Executors.newCachedThreadPool();
        Semaphore semaphore = new Semaphore(CONCURRENT_LEVEL);
        CountDownLatch countDownLatch = new CountDownLatch(TOTAL);

        for (int i = 0; i < TOTAL; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    test();
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }

            });

        }

        countDownLatch.await(2000, TimeUnit.MILLISECONDS);
        log.error("[{}] ------ END {} {}", stopwatch.elapsed(TimeUnit.MILLISECONDS), "action", longAdder.sum());

        executorService.shutdown();
        stopwatch.stop();
    }

    private static void test() throws InterruptedException {
        Thread.sleep(500);

        longAdder.increment();
        log.info("[{}] {} {}", stopwatch.elapsed(TimeUnit.MILLISECONDS), "action", longAdder.longValue());
    }
}
