package com.alphawang.concurrency.aqs;

import com.alphawang.concurrency.common.Printer;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class MutexTest {
    
    Lock mutex = new Mutex(); // = new ReentrantLock();

    public static void main(String[] args) {
//        testNonMutex();
        testMutex();
    }

    /**
     * [Thread-0] -  loop-0
     * [Thread-3] -  loop-0
     * [Thread-1] -  loop-0
     * [Thread-2] -  loop-0
     * ...
     */
    private static void testNonMutex() {
        MutexTest sut = new MutexTest();

        IntStream.rangeClosed(0, 3).forEach(i -> {
            Thread t = new Thread(() -> {
                sut.doWithoutMutex();
            });
            t.start();
        });
    }

    /**
     * [Thread-0] -  loop-0
     * [Thread-0] -  loop-1
     * [Thread-0] -  loop-2
     * [Thread-0] -  loop-3
     * [Thread-0] -  loop-4
     * [Thread-0] -  loop-5
     * [Thread-1] -  loop-0
     * ...
     */
    private static void testMutex() {
        MutexTest sut = new MutexTest();

        IntStream.rangeClosed(0, 3).forEach(i -> {
            Thread t = new Thread(() -> {
                sut.doWithMutex();
            });
            t.start();
        });
    }


    public void doWithoutMutex() {
        IntStream.rangeClosed(0, 5).forEach(i -> {
            Printer.print(" loop-" + i);
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }


    public void doWithMutex() {
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
