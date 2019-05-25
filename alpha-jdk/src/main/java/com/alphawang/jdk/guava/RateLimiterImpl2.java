package com.alphawang.jdk.guava;

import java.util.concurrent.TimeUnit;

/**
 * RateLimiter简单实现
 * 
 * - 令牌桶容量 > 1 的实现
 */
public class RateLimiterImpl2 {
    // 令牌桶中的令牌数量
    private long storedPermits = 0;
    // 令牌桶容量
    private long maxPermits = 3;
    // 下一令牌产生时间
    private long next = System.nanoTime();
    // 发放令牌间隔
    private long interval = 1000_000_000;

    /**
     * 预占令牌，返回能够获取令牌的时间
     */
    synchronized long reserve(long now) {
        resync(now);

        // at: 能够获取令牌的时间
        long at = next;
        // rb: 令牌桶中能提供的令牌数
        long fb = Math.min(1, storedPermits);
        // nr: 令牌净需求
        long nr = 1 - fb;
        // 计算下一令牌产生时间
        next = next + nr * interval;
        
        // 重新计算令牌桶中令牌数量
        storedPermits -= fb;
        
        return at; 
    }

    /**
     * 如果请求时间在下一令牌时间之后，则
     * 1. 重新计算 令牌桶中的令牌数量
     * 2. 将下一令牌时间重置为当前时间
     */
    void resync(long now) {
        if (now > next) {
            long newPermits = (now - next) / interval;
            storedPermits = Math.min(maxPermits, storedPermits + newPermits);
            
            next = now;
        }
    }

    /**
     * 申请令牌
     */
    void acquire() throws InterruptedException {
        // 预占令牌
        long now = System.nanoTime();
        long at = reserve(now);

        // 计算要等待的时间，wait
        long waitTime = Math.max(at - now, 0);
        if (waitTime > 0) {
            TimeUnit.NANOSECONDS.sleep(waitTime);
        }
    }
    
}
