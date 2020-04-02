package com.alphawang.concurrency.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Mutex implements Lock {
    
    private final Sync sync = new Sync();

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }
    
    public boolean isLocked() {
        return sync.isHeldExclusively();
    }
    
    public boolean hasQueuedThreads() {
        return sync.hasQueuedThreads();
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    /**
     * AQS 实现
     */
    private static class Sync extends AbstractQueuedSynchronizer {

        /**
         * 获取锁：当 state == 0 时允许
         */
        @Override
        protected boolean tryAcquire(int acquires) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            
            return false;
        }

        /**
         * 释放锁：当 state == 1 时允许
         */
        @Override
        protected boolean tryRelease(int releases) {
            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            }
            
            setExclusiveOwnerThread(null);
            setState(0);  // compareAndSetState(1, 0);
            return true;
        }

        /**
         * 判断当前是否处于占用状态
         */
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }
        
        Condition newCondition() {
            return new ConditionObject();
        }
    }

}
