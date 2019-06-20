package com.alphawang.concurrency.common.c4_unsafe;

import com.alphawang.concurrency.common.annotations.ThreadSafe;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * output:
 *
 *
 */
@Slf4j
@ThreadSafe
public class SimpleDateFormatTest3_DateTimeFormatter {

    private static final int TOTAL = 100;
    private static final int CONCURRENT_LEVEL = 5;

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
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

        countDownLatch.await();
        log.error("[{}] ------ END {} {}", stopwatch.elapsed(TimeUnit.MILLISECONDS), "DateTimeFormatter");

        executorService.shutdown();
        stopwatch.stop();
    }

    private static void test() throws InterruptedException {
        Thread.sleep(500);

        LocalDateTime now = LocalDateTime.now();
        String output = now.format(dateTimeFormatter);
        LocalDate date = LocalDate.parse(output, dateTimeFormatter);
        log.info("[{}] {} {}", stopwatch.elapsed(TimeUnit.MILLISECONDS), "DateTimeFormatter", date);
    }
}
