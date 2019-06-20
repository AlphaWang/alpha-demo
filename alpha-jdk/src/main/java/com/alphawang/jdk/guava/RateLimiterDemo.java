package com.alphawang.jdk.guava;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class RateLimiterDemo {

    public static void main(String[] args) {
        RateLimiter limiter = RateLimiter.create(2);

        ExecutorService es = Executors.newFixedThreadPool(1);

        AtomicLong prev = new AtomicLong(System.nanoTime());

        for (int i = 0; i < 20; i++) {
            limiter.acquire();

            es.execute(() -> {
                long cur = System.nanoTime();
                System.out.println("interval: " + (cur - prev.get()) / 1000_000);
                prev.set(cur);
            });
        }
    }
}
