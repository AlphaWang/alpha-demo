package com.alphawang.jdk.guava.rate;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Created by Alpha on 12/11/17.
 */
public class Test_RateLimiter {


	/**
	 * 第一个请求：马上执行
	 * 其后请求： 每隔一秒执行
	 *
	 * output:
	 * Sun May 20 09:46:13 CST 2018 : perform() : wait time 0.0
	 * Sun May 20 09:46:14 CST 2018 : perform() : wait time 0.937064
	 * Sun May 20 09:46:15 CST 2018 : perform() : wait time 0.994949
	 * Sun May 20 09:46:16 CST 2018 : perform() : wait time 0.997076
	 * Sun May 20 09:46:17 CST 2018 : perform() : wait time 0.997918
	 * Sun May 20 09:46:18 CST 2018 : perform() : wait time 0.99736
	 * Sun May 20 09:46:19 CST 2018 : perform() : wait time 0.996925
	 * Sun May 20 09:46:20 CST 2018 : perform() : wait time 0.995059
	 * Sun May 20 09:46:21 CST 2018 : perform() : wait time 0.996556
	 * */
	@Test
	public void testRateLimiter() {
		RateLimiter rateLimiter = RateLimiter.create(1);

		IntStream.range(1, 10)
			.forEach(index -> perform(rateLimiter));
	}

	/**
	 * 会有突发高峰
	 *
	 * Sun May 20 09:54:37 CST 2018 : perform() : wait time 0.0
	 * Sun May 20 09:54:38 CST 2018 : perform() : wait time 0.484093
	 * Sun May 20 09:54:38 CST 2018 : perform() : wait time 0.494071
	 * Sun May 20 09:54:41 CST 2018 : perform() : wait time 0.0
	 * Sun May 20 09:54:41 CST 2018 : perform() : wait time 0.0
	 * Sun May 20 09:54:41 CST 2018 : perform() : wait time 0.0
	 * Sun May 20 09:54:42 CST 2018 : perform() : wait time 0.49943
	 * Sun May 20 09:54:42 CST 2018 : perform() : wait time 0.496414
	 */
	@Test
	public void testSleep() {
		RateLimiter rateLimiter = RateLimiter.create(2);
		// 第一个请求：马上执行
		perform(rateLimiter);
		perform(rateLimiter);
		perform(rateLimiter);

		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// sleep后，这三个同时马上执行
		perform(rateLimiter);
		perform(rateLimiter);
		perform(rateLimiter);

		//这两个在一秒内执行完
		perform(rateLimiter);
		perform(rateLimiter);

	}

	/**
	 * 不会有突发的高峰
	 *
	 * Sun May 20 17:09:12 CST 2018 : perform() : wait time 0.0
	 * Sun May 20 17:09:13 CST 2018 : perform() : wait time 0.507259
	 * Sun May 20 17:09:13 CST 2018 : perform() : wait time 0.357693
	 * Sun May 20 17:09:13 CST 2018 : perform() : wait time 0.21584
	 * Sun May 20 17:09:14 CST 2018 : perform() : wait time 0.197119
	 * Sun May 20 17:09:14 CST 2018 : perform() : wait time 0.195654
	 * Sun May 20 17:09:14 CST 2018 : perform() : wait time 0.199001
	 * Sun May 20 17:09:17 CST 2018 : perform() : wait time 0.0
	 * Sun May 20 17:09:18 CST 2018 : perform() : wait time 0.519785
	 * Sun May 20 17:09:18 CST 2018 : perform() : wait time 0.354829
	 * Sun May 20 17:09:18 CST 2018 : perform() : wait time 0.219484
	 * Sun May 20 17:09:18 CST 2018 : perform() : wait time 0.199727
	 * Sun May 20 17:09:19 CST 2018 : perform() : wait time 0.195517
	 * Sun May 20 17:09:19 CST 2018 : perform() : wait time 0.194884
	 */
	@Test
	public void testSleep_SmoothWarmingUp() {
		// 底层是new SmoothWarmingUp()
		RateLimiter rateLimiter = RateLimiter.create(5, 1000, TimeUnit.MILLISECONDS);
//		RateLimiter rateLimiter = RateLimiter.create(5);

		perform(rateLimiter);
		perform(rateLimiter);
		perform(rateLimiter);
		perform(rateLimiter);
		perform(rateLimiter);
		perform(rateLimiter);
		perform(rateLimiter);

		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		perform(rateLimiter);
		perform(rateLimiter);
		perform(rateLimiter);
		perform(rateLimiter);
		perform(rateLimiter);
		perform(rateLimiter);
		perform(rateLimiter);

	}

	/**
	 * 前四次速率较慢
	 * 后面保持稳定速率
	 * Sun May 20 17:07:58 CST 2018 : perform() : wait time 0.0
	 * Sun May 20 17:07:58 CST 2018 : perform() : wait time 0.507423
	 * Sun May 20 17:07:59 CST 2018 : perform() : wait time 0.354979
	 * Sun May 20 17:07:59 CST 2018 : perform() : wait time 0.216053
	 * Sun May 20 17:07:59 CST 2018 : perform() : wait time 0.199057
	 * Sun May 20 17:07:59 CST 2018 : perform() : wait time 0.198388
	 * Sun May 20 17:08:00 CST 2018 : perform() : wait time 0.196125
	 * Sun May 20 17:08:00 CST 2018 : perform() : wait time 0.196762
	 * Sun May 20 17:08:00 CST 2018 : perform() : wait time 0.196154
	 * Sun May 20 17:08:00 CST 2018 : perform() : wait time 0.19737
	 */
	@Test
	public void testSleep_SmoothWarmingUp_all() {
		// 底层是new SmoothWarmingUp()
		RateLimiter rateLimiter = RateLimiter.create(5, 1000, TimeUnit.MILLISECONDS);
		//		RateLimiter rateLimiter = RateLimiter.create(5);

		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		perform(rateLimiter);
		perform(rateLimiter);
		perform(rateLimiter);
		perform(rateLimiter);
		perform(rateLimiter);

		perform(rateLimiter);
		perform(rateLimiter);
		perform(rateLimiter);
		perform(rateLimiter);
		perform(rateLimiter);
	}

	private static void perform(RateLimiter rateLimiter) {
		double waitTime = rateLimiter.acquire();

		System.out.println(new Date() + " : perform() : wait time " + waitTime);
	}
}
