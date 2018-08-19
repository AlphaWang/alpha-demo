package com.alphawang.concurrency.c01.thread;

import java.util.concurrent.TimeUnit;

/**
 * 执行之后通过 `jstack` 查看线程状态
 * 
 * 
 * java.lang.Thread.State
 * 
 * 
 *﻿ 1. NEW: 
 * Thread state for a thread which has not yet started.
 *
 * 2. RUNNABLE: 
 * Thread state for a runnable thread.
 *
 * 3. BLOCKED:
 * Thread state for a thread blocked waiting for a monitor lock.
 * A thread in the blocked state is waiting for a monitor lock 
 * to enter a synchronized block/method 
 * or reenter a synchronized block/method after calling Object.wait.
 *
 * 4. WAITING:
 * Thread state for a waiting thread.
 * A thread is in the waiting state due to calling one of the following methods:
 *   - Object.wait() with no timeout
 *   - Thread.join() with no timeout
 *   - LockSupport#park() 
 *
 * A thread in the waiting state is waiting for another thread to perform a particular action.
 * - For example, a thread that has called <tt>Object.wait()</tt> on an object 
 * is waiting for another thread to call `Object.notify() or Object.notifyAll()`  on that object. 
 * - A thread that has called <tt>Thread.join()</tt> is waiting for a specified thread to terminate.
 *
 * 5. TIMED_WAITING:
 * Thread state for a waiting thread with a specified waiting time.
 * A thread is in the timed waiting state due to calling one of the following methods with a specified positive waiting time:
 *  - Thread.sleep
 *  - Object#wait(long) with timeout
 *  - Thread.join(long) with timeout
 *  - LockSupport.parkNanos
 *  - LockSupport.parkUntil
 *
 * 6. TERMINATED:
 * The thread has completed execution.
 *
 * 
 */
public class ThreadStatus {

    public static void main(String[] args) {
        /**
         * "timewaiting"
         *  java.lang.Thread.State: TIMED_WAITING (sleeping)
         */
        new Thread(()->{
            while(true){
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"timewaiting").start();

        /**
         * "waiting" 
         *  java.lang.Thread.State: WAITING (on object monitor)
         */
        new Thread(()->{
            while(true){
                synchronized (ThreadStatus.class){
                    try {
                        ThreadStatus.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"waiting").start();

        /**
         * "BlockDemo-0" 
         * java.lang.Thread.State: TIMED_WAITING (sleeping)
         */
        new Thread(new BlockDemo(),"BlockDemo-0").start();

        /**
         * BlockDemo-1" 
         * java.lang.Thread.State: BLOCKED (on object monitor)
         */
        new Thread(new BlockDemo(),"BlockDemo-1").start();

    }

    static class BlockDemo extends Thread{
        public void run(){
            synchronized (BlockDemo.class){
                while(true){
                    try {
                        TimeUnit.SECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    
}
