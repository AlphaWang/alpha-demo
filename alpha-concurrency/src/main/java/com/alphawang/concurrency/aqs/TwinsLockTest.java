package com.alphawang.concurrency.aqs;

import com.alphawang.concurrency.common.Printer;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.stream.IntStream;

public class TwinsLockTest {
    Lock mutex = new TwinsLock(); // = new ReentrantLock();

    public static void main(String[] args) {
//        testNonLock();
        testLock();
    }

    /**
     * [Thread-0] -  loop-0
     * [Thread-3] -  loop-0
     * [Thread-1] -  loop-0
     * [Thread-2] -  loop-0
     * ...
     */
    private static void testNonLock() {
        TwinsLockTest sut = new TwinsLockTest();

        IntStream.rangeClosed(0, 3).forEach(i -> {
            Thread t = new Thread(() -> {
                sut.doWithoutLock();
            });
            t.start();
        });
    }

    /**
     * 同时有两个线程获取锁
     * 
     * [Thread-0] -  loop-0
     * [Thread-1] -  loop-0
     * [Thread-0] -  loop-1
     * [Thread-1] -  loop-1
     * [Thread-0] -  loop-2
     * [Thread-1] -  loop-2
     * ...
     */
    private static void testLock() {
        TwinsLockTest sut = new TwinsLockTest();

        IntStream.rangeClosed(0, 3).forEach(i -> {
            Thread t = new Thread(() -> {
                sut.doWithLock();
            });
            t.start();
        });
    }


    public void doWithoutLock() {
        IntStream.rangeClosed(0, 5).forEach(i -> {
            Printer.print(" loop-" + i);
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }


    public void doWithLock() {
        mutex.lock();

        try {
            IntStream.rangeClosed(0, 5).forEach(i -> {
                Printer.print(" loop-" + i);
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mutex.unlock();
        }

    }
}
