package com.alphawang.concurrency.mutex.atomic;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * AtomicStampedReference：解决CAS的 A-B-A 问题
 * 
 * 当AtomicStampedReference对应的数值被修改时，除了更新数据本身外，还必须要更新时间戳。
 * 当AtomicStampedReference设置对象值时，对象值以及时间戳都必须满足期望值，写入才会成功。
 * 
 * 因此，即使对象值被反复读写，写回原值，只要时间戳发生变化，就能防止不恰当的写入。
 */
@Slf4j
public class AtomicStampedReferenceTest {

	private static final int TOTAL = 100;
	private static final int CONCURRENT_LEVEL = 5;

	private static Integer initValue = new Integer(1);
	private static AtomicStampedReference<Integer> stampedReference = new AtomicStampedReference<>(initValue, 0);
	
	private static Stopwatch stopwatch = Stopwatch.createStarted();

	public static void main(String[] args) throws InterruptedException {

		log.info("[{}] ----- START ", stopwatch.elapsed(TimeUnit.MILLISECONDS));

		ExecutorService executorService = Executors.newCachedThreadPool();

		executorService.execute(() -> { 
			test(2, 1);
		});

		Thread.sleep(500);
		
		executorService.execute(() -> {
			test(1, 2);
		});
		


		log.error("[{}] ------ END {} {}", stopwatch.elapsed(TimeUnit.MILLISECONDS), "action", stampedReference.getReference());

		executorService.shutdown();
		stopwatch.stop();
	}

	private static void test(Integer value, int stamp) {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		 stamp = stampedReference.getStamp();
		stampedReference.compareAndSet(initValue, value, stamp, stamp + 1);
//		stampedReference.set(value, stamp);
		log.info("[{}] {} value: {} stamp: {}", stopwatch.elapsed(TimeUnit.MILLISECONDS),
			"action", stampedReference.getReference(), stampedReference.getStamp());
	}
}
