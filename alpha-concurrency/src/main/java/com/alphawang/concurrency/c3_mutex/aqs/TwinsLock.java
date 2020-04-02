package com.alphawang.concurrency.c3_mutex.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TwinsLock implements Lock {
    
    private final Sync sync = new Sync(2);

    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }


    private static class Sync extends AbstractQueuedSynchronizer {
        
        Sync(int count) {
            setState(count);
        }

        /**
         * 不能用 tryAcquire() ?
         * 
         * @return >= 0 时，获取成功
         */
        @Override
        protected int tryAcquireShared(int acquires) {
            for (;;) {
                int currentCount = getState();
                int newCount = currentCount - acquires;
                if (newCount < 0 || compareAndSetState(currentCount, newCount)) {
                    return newCount;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int releases) {
            for (;;) {
                int currentCount = getState();
                int newCount = currentCount + releases;
                if (compareAndSetState(currentCount, newCount)) {
                    return true;
                }
            }
            
        }
    }

}
