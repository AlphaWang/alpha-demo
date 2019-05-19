package com.alphawang.concurrency.common.c4_unsafe;

import com.alphawang.concurrency.common.annotations.NotThreadSafe;
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
 * Exception in thread "pool-1-thread-2" Exception in thread "pool-1-thread-3" Exception in thread "pool-1-thread-1" java.lang.NumberFormatException: multiple points
 * 	at sun.misc.FloatingDecimal.readJavaFormatString(FloatingDecimal.java:1914)
 * 	at sun.misc.FloatingDecimal.parseDouble(FloatingDecimal.java:110)
 * 	at java.lang.Double.parseDouble(Double.java:538)
 * 	at java.text.DigitList.getDouble(DigitList.java:169)
 * 	at java.text.DecimalFormat.parse(DecimalFormat.java:2056)
 * 	at java.text.SimpleDateFormat.subParse(SimpleDateFormat.java:1867)
 * 	at java.text.SimpleDateFormat.parse(SimpleDateFormat.java:1514)
 * 	at java.text.DateFormat.parse(DateFormat.java:364)
 * 	at com.alphawang.concurrency.unsafe.SimpleDateFormatTest.test(SimpleDateFormatTest.java:66)
 */
@Slf4j
@NotThreadSafe
public class SimpleDateFormatTest {

	private static final int TOTAL = 100;
	private static final int CONCURRENT_LEVEL = 5;

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
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
		log.error("[{}] ------ END {} {}", stopwatch.elapsed(TimeUnit.MILLISECONDS), "static SimpleDateFormat");

		executorService.shutdown();
		stopwatch.stop();
	}

	private static void test() throws InterruptedException, ParseException {
		Thread.sleep(500);

		String output = dateFormat.format(new Date());
		Date date = dateFormat.parse(output);
		log.info("[{}] {} {}", stopwatch.elapsed(TimeUnit.MILLISECONDS), "static SimpleDateFormat", date);
	}
}
