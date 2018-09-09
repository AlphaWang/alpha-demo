package com.alphawang.redis.scenario.queue;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Type;
import java.util.Set;
import java.util.UUID;

public class RedisDelayingQueue<T> {
    
    static class TaskItem<T> {
        public String id;
        public T msg;
    }
    
    private Type taskType = new TypeReference<TaskItem<T>>() {}.getType();
    
    private Jedis jedis;
    private String queueKey;
    
    public RedisDelayingQueue(Jedis jedis, String queueKey) {
        this.jedis = jedis;
        this.queueKey = queueKey;
    }
    
    public void delay(T msg) {
        TaskItem<T> task = new TaskItem<>();
        task.id = UUID.randomUUID().toString();
        task.msg = msg;
        
        // 塞入延时队列 ,5s 后再试
        String s = JSON.toJSONString(task);
        System.out.println("+++ ADD QUEUE: " + s);
        jedis.zadd(queueKey, System.currentTimeMillis() + 5000, s);
    }
    
    public void loop() {
        while (!Thread.interrupted()) {
            //取一条
            Set<String> value = jedis.zrangeByScore(queueKey, 0, System.currentTimeMillis(), 0 , 1);
            
            if (value.isEmpty()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
                continue;
            }
            
            String s = value.iterator().next();
            System.out.println("--- GET QUEUE: " + s);
            
            if (jedis.zrem(queueKey, s) > 0) {
                TaskItem<T> task = JSON.parseObject(s, taskType);
                this.handleMsg(task.msg);
            }
            
        }
    } 
    

    private void handleMsg(T msg) {
        System.out.println(msg);
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        RedisDelayingQueue<String> queue = new RedisDelayingQueue<>(jedis, "alpha-redis-queue");
        
        Thread producer = new Thread() {
             @Override
             public void run() {
                 for (int i = 0; i < 10; i++) {
                     queue.delay("msg-" + i);
                 }
             }
        };

        Thread consumer = new Thread() {
            @Override
            public void run() {
                queue.loop();
            }
        };
        
        producer.start();
        consumer.start();

        try {
            producer.join();
            Thread.sleep(6000);
            consumer.interrupt();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }

}
