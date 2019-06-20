package com.alphawang.concurrency.c3_mutex.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 两个对象的同一个实例方法：交叉执行
 *
 * 22:57:49 [pool-1-thread-1] INFO  c.a.c.c1_atomic.SynchronizedTest1 - test2 1 - 0
 * 22:57:49 [pool-1-thread-2] INFO  c.a.c.c1_atomic.SynchronizedTest1 - test2 2 - 0
 * 22:57:49 [pool-1-thread-1] INFO  c.a.c.c1_atomic.SynchronizedTest1 - test2 1 - 1
 * 22:57:49 [pool-1-thread-2] INFO  c.a.c.c1_atomic.SynchronizedTest1 - test2 2 - 1
 * 22:57:49 [pool-1-thread-1] INFO  c.a.c.c1_atomic.SynchronizedTest1 - test2 1 - 2
 * 22:57:49 [pool-1-thread-2] INFO  c.a.c.c1_atomic.SynchronizedTest1 - test2 2 - 2
 * 22:57:49 [pool-1-thread-1] INFO  c.a.c.c1_atomic.SynchronizedTest1 - test2 1 - 3
 * 22:57:49 [pool-1-thread-1] INFO  c.a.c.c1_atomic.SynchronizedTest1 - test2 1 - 4
 * 22:57:49 [pool-1-thread-2] INFO  c.a.c.c1_atomic.SynchronizedTest1 - test2 2 - 3
 * 22:57:49 [pool-1-thread-1] INFO  c.a.c.c1_atomic.SynchronizedTest1 - test2 1 - 5
 * 22:57:49 [pool-1-thread-2] INFO  c.a.c.c1_atomic.SynchronizedTest1 - test2 2 - 4
 * 22:57:49 [pool-1-thread-1] INFO  c.a.c.c1_atomic.SynchronizedTest1 - test2 1 - 6
 * 22:57:49 [pool-1-thread-2] INFO  c.a.c.c1_atomic.SynchronizedTest1 - test2 2 - 5
 * 22:57:49 [pool-1-thread-1] INFO  c.a.c.c1_atomic.SynchronizedTest1 - test2 1 - 7
 * 22:57:49 [pool-1-thread-2] INFO  c.a.c.c1_atomic.SynchronizedTest1 - test2 2 - 6
 * 22:57:49 [pool-1-thread-1] INFO  c.a.c.c1_atomic.SynchronizedTest1 - test2 1 - 8
 * 22:57:49 [pool-1-thread-2] INFO  c.a.c.c1_atomic.SynchronizedTest1 - test2 2 - 7
 * 22:57:49 [pool-1-thread-1] INFO  c.a.c.c1_atomic.SynchronizedTest1 - test2 1 - 9
 * 22:57:49 [pool-1-thread-2] INFO  c.a.c.c1_atomic.SynchronizedTest1 - test2 2 - 8
 * 22:57:49 [pool-1-thread-2] INFO  c.a.c.c1_atomic.SynchronizedTest1 - test2 2 - 9
 */
@Slf4j
public class SynchronizedTest1 {
    // 修饰一个代码块
    public void test1(int j) {
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                log.info("test1 {} - {}", j, i);
            }
        }
    }

    // 修饰一个方法
    public synchronized void test2(int j) {
        for (int i = 0; i < 10; i++) {
            log.info("test2 {} - {}", j, i);
        }
    }

    public static void main(String[] args) {
        SynchronizedTest1 example1 = new SynchronizedTest1();
        SynchronizedTest1 example2 = new SynchronizedTest1();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            example1.test2(1);
        });
        executorService.execute(() -> {
            example2.test2(2);
        });
    }
}
