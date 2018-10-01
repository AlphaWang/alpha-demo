package com.alphawang.redis.scenario.lock;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Slf4j
public class RedisDistributedLock {
    
    private Jedis jedis;

    public RedisDistributedLock(Jedis jedis) {
        this.jedis = jedis;
    }
    
    public String acquireLock(String lockName, long acquireTimeout, long lockTimeout) {
        String identifier = UUID.randomUUID().toString();
        String lockKey = "lock:" + lockName;
        
        log.info("LOCKING {}", lockKey);
        
        int lockExpire = (int) (lockTimeout / 1000);
        long end = System.currentTimeMillis() + acquireTimeout;
        
        try {
            while (System.currentTimeMillis() < end) {  // acquireTimeout 获取锁的限定时间

                if (jedis.setnx(lockKey, identifier) == 1) { // 设值
                    jedis.expire(lockKey, lockExpire);  // 设置超时时间 
                    // setnx + expire 可以用一条命令  :  jedis.set(key, "", "nx", "ex", 5L)
                    
                    log.info("LOCKED {}, id: {}", lockKey, identifier);
                    return identifier;
                }

                if (jedis.ttl(lockKey) == -1) {
                    jedis.expire(lockKey, lockExpire); // 设置超时时间
                }

                try {
                    Thread.sleep(100); // 等待片刻，重新尝试获取锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
//            jedis.close();
        }
        
        return null;
    }
    
    public boolean releaseLock(String lockName, String identifier) {
        String lockKey="lock:"+lockName;
        log.info("RELEASING {}, id: {}", lockKey, identifier);
        
        boolean isReleased = false;
        
        try {
            while (true) {
                jedis.watch(lockKey);

                if (identifier.equals(jedis.get(lockKey))) {

                    Transaction tx = jedis.multi();
                    tx.del(lockKey);
                    List<Object> exec = tx.exec();

                    if (exec.isEmpty()) {
                        continue;
                    }

                    isReleased = true;
                }

                jedis.unwatch();
                break;
            }
        } finally {
//            jedis.close();
        }
        
        return isReleased;
    }
    
    public boolean releaseWithLua(String lockName, String identifier) {
        String lockKey="lock:"+lockName;
        log.info("RELEASING with Lua {}, id: {}", lockKey, identifier);
        
        String lua = "if redis.call(\"get\", KEYS[1]) == ARGV[1] " 
            + " then " 
            + "    return redis.call(\"del\", KEYS[1]) " 
            + " else " 
            + "    return 0 " 
            + "  end";
        
        Long rs = (Long) jedis.eval(lua, 1, new String[] {lockKey, identifier});
        
        return rs.intValue() > 0;
    }

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override 
            public void run() {
                while (true) {
                    Jedis jedis = new Jedis();
                    RedisDistributedLock lock = new RedisDistributedLock(jedis);

                    String identifier = lock.acquireLock("updateCart", 2000, 5000);

                    if (identifier != null) {
                        System.out.println(Thread.currentThread().getName() + " get lock: " + identifier);

                        try {
                            Thread.sleep(1000);

                            lock.releaseWithLua("updateCart", identifier);
                            System.out.println(Thread.currentThread().getName() + " release lock: " + identifier);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        };

//        for (int i = 0; i < 10; i++) {
//            new Thread(runnable, "thread-" + i).start();
//        }
        IntStream.range(0, 10).forEach(index -> new Thread(runnable, "thread-" + index).start());
    }
    
}


