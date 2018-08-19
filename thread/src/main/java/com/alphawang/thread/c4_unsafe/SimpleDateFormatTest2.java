package com.alphawang.thread.c4_unsafe;

import com.alphawang.concurrency.annotations.ThreadSafe;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;


/**
 * output:
 * 
 *
 *  INFO [pool-1-thread-12] (SimpleDateFormatTest2.java:77) - [1604] SimpleDateFormat Thu Jun 07 00:00:00 CST 2018
 *  INFO [pool-1-thread-11] (SimpleDateFormatTest2.java:77) - [1604] SimpleDateFormat Thu Jun 07 00:00:00 CST 2018
 *  INFO [pool-1-thread-13] (SimpleDateFormatTest2.java:77) - [1605] SimpleDateFormat Thu Jun 07 00:00:00 CST 2018
 *  INFO [pool-1-thread-15] (SimpleDateFormatTest2.java:77) - [1605] SimpleDateFormat Thu Jun 07 00:00:00 CST 2018
 *  INFO [pool-1-thread-14] (SimpleDateFormatTest2.java:77) - [1605] SimpleDateFormat Thu Jun 07 00:00:00 CST 2018
 */
@Slf4j
@ThreadSafe
public class SimpleDateFormatTest2 {

	private static final int TOTAL = 100;
	private static final int CONCURRENT_LEVEL = 5;

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
				} catch (InterruptedException | ParseException e) {
					e.printStackTrace();
				} finally {
					countDownLatch.countDown();
				}

			});

		}

		countDownLatch.await();
		log.error("[{}] ------ END {} {}", stopwatch.elapsed(TimeUnit.MILLISECONDS), "new SimpleDateFormat");

		executorService.shutdown();
		stopwatch.stop();
	}

	private static void test() throws InterruptedException, ParseException {
		Thread.sleep(500);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		
		String output = dateFormat.format(new Date());
		Date date = dateFormat.parse(output);
		log.info("[{}] {} {}", stopwatch.elapsed(TimeUnit.MILLISECONDS), "new SimpleDateFormat", date);
	}
}
