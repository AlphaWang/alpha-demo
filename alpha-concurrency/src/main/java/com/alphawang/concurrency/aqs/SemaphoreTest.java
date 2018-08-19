package com.alphawang.concurrency.aqs;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * semaphore.tryAcquire(3000, TimeUnit.MILLISECONDS)
 * 如果获取不到信号量，则等待。
 * 如果等待超时，则丢弃本次请求。
 */
@Slf4j
public class SemaphoreTest {
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

					// tryAcquire with timeout:
					// 当获取不到信号量，则等待。超时后直接丢弃本次请求。
					if (semaphore.tryAcquire(3000, TimeUnit.MILLISECONDS)) {
						test();
						semaphore.release();
					} else {
						log.info("[{}] {}", stopwatch.elapsed(TimeUnit.MILLISECONDS), "Discard action.");
					}


				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					countDownLatch.countDown();
				}

			});

		}

		countDownLatch.await();
		log.error("[{}] ------ END {} {}", stopwatch.elapsed(TimeUnit.MILLISECONDS), "action", longAdder.sum());

		executorService.shutdown();
		stopwatch.stop();
	}

	private static void test() throws InterruptedException {
		Thread.sleep(1000);

		longAdder.increment();
		log.info("[{}] {} {}", stopwatch.elapsed(TimeUnit.MILLISECONDS), "action", longAdder.longValue());
	}
}
