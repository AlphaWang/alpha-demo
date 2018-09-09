package com.alphawang.redis.scenario.hyperloglog;

import redis.clients.jedis.Jedis;

/**
 * ﻿HyperLogLog 提供了两个指令 pfadd 和 pfcount，根据字面意义很好理解，一个是增加计数，一个是获取计数。
 * pfadd 用法和 set 集合的 sadd 是一样的，来一个用户 ID，就将用户 ID 塞进去就是
 */
public class RedisPfAdd {

    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        
        int testCount = 100000;
        for (int i = 0; i < testCount; i++) {
            jedis.pfadd("codehole", "user" + i);
        }
        long total = jedis.pfcount("codehole");

        /**
         * pfadd 会有误差
         */
        System.out.printf("expected: %d, actual: %d\n", 100000, total);
        jedis.close();
    }
    
    
}

