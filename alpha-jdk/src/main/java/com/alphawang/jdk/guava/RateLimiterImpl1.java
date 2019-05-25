package com.alphawang.jdk.guava;

import java.util.concurrent.TimeUnit;

/**
 * 简单RateLimiter实现
 * 
 * - 令牌通容量为 1 
 * - 记录下一个令牌产生的时间，并动态更新它
 */
public class RateLimiterImpl1 {
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
        next = next + interval;
        return Math.max(at, 0L);
    }

    /**
     * 如果请求时间 在下一令牌时间之后，则将下一令牌时间重置为当前时间
     */
    void resync(long now) {
        if (now > next) {
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

    public static void main(String[] args) {
        
    }
}
