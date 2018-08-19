package com.alphawang.concurrency.c1_atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 两个对象的同一个静态方法：顺序执行
 * >> 静态锁 会影响当前类的所有对象！！
 * 
 * 279  ms [INFO ] [pool-1-thread-1 ] SynchronizedTest2:39 - test1 1 - 0
 * 283  ms [INFO ] [pool-1-thread-1 ] SynchronizedTest2:39 - test1 1 - 1
 * 283  ms [INFO ] [pool-1-thread-1 ] SynchronizedTest2:39 - test1 1 - 2
 * 283  ms [INFO ] [pool-1-thread-1 ] SynchronizedTest2:39 - test1 1 - 3
 * 283  ms [INFO ] [pool-1-thread-1 ] SynchronizedTest2:39 - test1 1 - 4
 * 284  ms [INFO ] [pool-1-thread-1 ] SynchronizedTest2:39 - test1 1 - 5
 * 284  ms [INFO ] [pool-1-thread-1 ] SynchronizedTest2:39 - test1 1 - 6
 * 284  ms [INFO ] [pool-1-thread-1 ] SynchronizedTest2:39 - test1 1 - 7
 * 284  ms [INFO ] [pool-1-thread-1 ] SynchronizedTest2:39 - test1 1 - 8
 * 284  ms [INFO ] [pool-1-thread-1 ] SynchronizedTest2:39 - test1 1 - 9
 * 284  ms [INFO ] [pool-1-thread-2 ] SynchronizedTest2:39 - test1 2 - 0
 * 284  ms [INFO ] [pool-1-thread-2 ] SynchronizedTest2:39 - test1 2 - 1
 * 284  ms [INFO ] [pool-1-thread-2 ] SynchronizedTest2:39 - test1 2 - 2
 * 284  ms [INFO ] [pool-1-thread-2 ] SynchronizedTest2:39 - test1 2 - 3
 * 284  ms [INFO ] [pool-1-thread-2 ] SynchronizedTest2:39 - test1 2 - 4
 * 284  ms [INFO ] [pool-1-thread-2 ] SynchronizedTest2:39 - test1 2 - 5
 * 285  ms [INFO ] [pool-1-thread-2 ] SynchronizedTest2:39 - test1 2 - 6
 * 285  ms [INFO ] [pool-1-thread-2 ] SynchronizedTest2:39 - test1 2 - 7
 * 285  ms [INFO ] [pool-1-thread-2 ] SynchronizedTest2:39 - test1 2 - 8
 * 285  ms [INFO ] [pool-1-thread-2 ] SynchronizedTest2:39 - test1 2 - 9
 */
@Slf4j
public class SynchronizedTest2 {
    // 修饰一个代码块
    public void test1(int j) {
        synchronized (SynchronizedTest2.class) {
            for (int i = 0; i < 10; i++) {
                log.info("test1 {} - {}", j, i);
            }
        }
    }

    // 修饰一个方法
    public static synchronized void test2(int j) {
        for (int i = 0; i < 10; i++) {
            log.info("test2 {} - {}", j, i);
        }
    }

    public static void main(String[] args) {
        SynchronizedTest2 example1 = new SynchronizedTest2();
        SynchronizedTest2 example2 = new SynchronizedTest2();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            example1.test1(1);
        });
        executorService.execute(() -> {
            example2.test1(2);
        });
    }
}
