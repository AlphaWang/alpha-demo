package com.alphawang.concurrency.c4_unsafe;

import com.alphawang.concurrency.annotations.ThreadSafe;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;


/**
 * output:
 * 
 * ------ END StringBuffer.length() 100
 */
@Slf4j
@ThreadSafe
public class StringBufferTest {

	private static final int TOTAL = 100;
	private static final int CONCURRENT_LEVEL = 5;

	private static StringBuffer stringBuffer = new StringBuffer();
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
		log.error("[{}] ------ END {} {}", stopwatch.elapsed(TimeUnit.MILLISECONDS), "StringBuffer.length()", stringBuffer.length());

		executorService.shutdown();
		stopwatch.stop();
	}

	private static void test() throws InterruptedException {
		Thread.sleep(500);

		stringBuffer.append("1");
		log.info("[{}] {} ", stopwatch.elapsed(TimeUnit.MILLISECONDS), "StringBuffer.append()", stringBuffer.length());
	}
}
