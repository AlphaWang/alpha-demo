package com.alphawang.redis.scenario.lock;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

public class RedisWithReentrantLock {

    private ThreadLocal<Map<String, Integer>> lockers = new ThreadLocal<>();

    private Jedis jedis;

    public RedisWithReentrantLock(Jedis jedis) {
        this.jedis = jedis;
    }

    private boolean _lock(String key) {
        /**
         * ﻿set key true ex 5 nx
         * ﻿使得 setnx 和 expire 指令可以一起执行
         */
        System.err.println("LOCK " + key);
        String rs = jedis.set(key, "", "nx", "ex", 5L);
        System.err.println("LOCKED " + key + " rs: " + rs);
        
        return rs != null;
    }

    private void _unlock(String key) {
        System.err.println("UNLOCK " + key);
        Long rs = jedis.del(key);
        System.err.println("UNLOCKED " + key + " rs: " + rs);
    }

    private Map<String, Integer> currentLockers() {
        Map<String, Integer> refs = lockers.get();
        if (refs != null) {
            return refs;
        }
        lockers.set(new HashMap<>());
        return lockers.get();
    }

    /**
     * 加锁
     */
    public boolean lock(String key) {
        //1. 已加过锁，count++
        Map<String, Integer> refs = currentLockers();
        Integer refCnt = refs.get(key);
        if (refCnt != null) {
            refs.put(key, refCnt + 1);
            System.out.println("LOCK: " + refs.get(key));
            return true;
        }
        
        //2. 否则拿锁，并初始化count=1
        boolean ok = this._lock(key);
        if (!ok) {
            return false;
        }
        refs.put(key, 1);
        System.out.println("LOCK: " + refs.get(key));
        return true;
    }

    public boolean unlock(String key) {
        //1. 若未拿过锁，则不能unlock
        Map<String, Integer> refs = currentLockers();
        Integer refCnt = refs.get(key);
        if (refCnt == null) {
            System.err.println("UNLOCK fail");
            return false;
        }
        
        //2. 否则，count--
        refCnt -= 1;
        if (refCnt > 0) {
            refs.put(key, refCnt);
        } else {
            refs.remove(key);
            this._unlock(key);
        }

        System.out.println("UNLOCK: " + refCnt);
        return true;
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        RedisWithReentrantLock redis = new RedisWithReentrantLock(jedis);
        redis.lock("codehole");
        redis.lock("codehole");
        redis.unlock("codehole");
        redis.unlock("codehole");
    }

}