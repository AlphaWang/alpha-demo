package com.alphawang.concurrency.common.c4_unsafe;

import com.alphawang.concurrency.common.annotations.ThreadSafe;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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
public class SimpleDateFormatTest3_joda {

	private static final int TOTAL = 100;
	private static final int CONCURRENT_LEVEL = 5;

	private static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyyMMdd");
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
		log.error("[{}] ------ END {} {}", stopwatch.elapsed(TimeUnit.MILLISECONDS), "joda DateTimeFormatter");

		executorService.shutdown();
		stopwatch.stop();
	}

	private static void test() throws InterruptedException {
		Thread.sleep(500);

		DateTime parsed = DateTime.parse("20180607", dateTimeFormatter);
		log.info("[{}] {} {}", stopwatch.elapsed(TimeUnit.MILLISECONDS), "joda DateTimeFormatter", parsed);
	}
}
