package com.alphawang.redis.scenario.lock;

import redis.clients.jedis.Jedis;

import java.util.UUID;

public class RedisDistributedLock {
    
    private Jedis jedis;

    public RedisDistributedLock(Jedis jedis) {
        this.jedis = jedis;
    }
    
    public String acquireLock(String lockName, long acquireTimeout, long lockTimeout) {
        
        String identifier = UUID.randomUUID().toString();
        String lockKey = "lock:" + lockName;
        
        int lockExpire = (int) (lockTimeout / 1000);
        long end = System.currentTimeMillis() + acquireTimeout;
        
        while (System.currentTimeMillis() < end) {
            if (jedis.setnx(lockKey, identifier) == 1) {
                jedis.expire(lockKey, lockExpire);  // setnx + expire 可以用一条命令
                return identifier;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        return null;
    }
    
}
