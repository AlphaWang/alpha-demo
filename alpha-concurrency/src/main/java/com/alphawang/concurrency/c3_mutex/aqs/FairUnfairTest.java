package com.alphawang.concurrency.c3_mutex.aqs;

import com.alphawang.concurrency.common.Printer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * NOT WORK ?
 */
public class FairUnfairTest {
    
    private static class MyReentrantLock extends ReentrantLock {
        
        public MyReentrantLock(boolean fair) {
            super(fair);
        }
        
        public Collection<String> getQueuedThreadNames() {
            return getQueuedThreads()
              .stream()
              .map(t -> t.getName())
              .collect(Collectors.toList());
        }
        
        public Collection<Thread> getQueuedThreads() {
            List<Thread> threads = new ArrayList(super.getQueuedThreads());
            Collections.reverse(threads);
            
            return threads;
        }
    }
    
    
    private static MyReentrantLock fairLock = new MyReentrantLock(true);
    private static MyReentrantLock unfairLock = new MyReentrantLock(false);

    public static void main(String[] args) {
        FairUnfairTest sut = new FairUnfairTest();

        sut.testFairLock();
//        sut.testUnfairLock();
        
    }

    private void testFairLock() {
        IntStream.rangeClosed(0, 5).forEach(i -> {
            new Job(fairLock).start();
        });
    }

    private void testUnfairLock() {
        IntStream.rangeClosed(0, 5).forEach(i -> {
            new Job(unfairLock).start();
        });
    }
    
    private static class Job extends Thread {
        private MyReentrantLock lock;
        
        public Job(MyReentrantLock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            lock.lock();
//            Printer.print(String.format("Loop-%s, waiting by %s", "AAAA", lock.getQueuedThreadNames()));
            try {
                Printer.print(String.format("Loop-%s, waiting by %s", 0, lock.getQueuedThreadNames()));
                Printer.print(String.format("Loop-%s, waiting by %s", 1, lock.getQueuedThreadNames()));
            } finally {
                lock.unlock();
            }
//            Printer.print(String.format("Loop-%s, waiting by %s", "BBBB", lock.getQueuedThreadNames()));
            
        }
    }

}
