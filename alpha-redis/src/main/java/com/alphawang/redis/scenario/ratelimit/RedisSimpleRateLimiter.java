package com.alphawang.redis.scenario.ratelimit;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.io.IOException;

public class RedisSimpleRateLimiter {
    private Jedis jedis;

    public RedisSimpleRateLimiter(Jedis jedis) {
        this.jedis = jedis;
    }

    /**
     * ﻿整体思路：
     * 每一个行为到来时，都维护一次时间窗口。将时间窗口外的记录全部清理掉，只保留窗口内的记录。
     * zset 集合中只有 score 值非常重要，value 值没有特别的意义，只需要保证它是唯一的就可以了。
     *
     * 因为这几个连续的 Redis 操作都是针对同一个 key 的，使用 pipeline 可以显著提升 Redis 存取效率。
     * 
     * 但这种方案也有缺点，
     * 因为它要记录时间窗口内所有的行为记录，如果这个量很大，
     * 比如限定 60s 内操作不得超过 100w 次这样的参数，它是不适合做这样的限流的，因为会消耗大量的存储空间
     *
     */
    public boolean isActionAllowed(String userId, String actionKey, int period, int maxCount) throws IOException {
        String key = String.format("hist:%s:%s", userId, actionKey);
        long nowTs = System.currentTimeMillis();
        Pipeline pipe = jedis.pipelined();
        pipe.multi();
        pipe.zadd(key, nowTs, "" + nowTs);
        pipe.zremrangeByScore(key, 0, nowTs - period * 1000);
        Response<Long> count = pipe.zcard(key);
        pipe.expire(key, period + 1);
        pipe.exec();
        pipe.close();
        return count.get() <= maxCount;
    }

    public static void main(String[] args) throws IOException {
        Jedis jedis = new Jedis();
        RedisSimpleRateLimiter limiter = new RedisSimpleRateLimiter(jedis);
        for (int i = 0; i < 20; i++) {
            System.out.println(limiter.isActionAllowed("laoqian", "reply", 60, 5));
        }
    }
}
