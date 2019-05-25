package com.alphawang.concurrency.c3_mutex.threadlocal;

import java.util.concurrent.atomic.AtomicLong;

public class ThreadId {
    

    /**
     * Q: 应该如何使用？？
     */
    public static void main(String[] args) {
        Runnable task = new Runnable() {
            @Override public void run() {
                System.out.println(ThreadIdHolder.getThreadId());
            }
        };
        
        for (int i = 0; i < 20; i++) {
            Thread t = new Thread() {
                public void run() {
                    System.out.println(ThreadIdHolder.getThreadId());
                }
            };
            t.run();
        }
    }
    
    static class ThreadIdHolder {
        private static final AtomicLong nextId = new AtomicLong(0);
        private static final ThreadLocal<Long> idThreadLocal = ThreadLocal.withInitial(() -> nextId.incrementAndGet());

        public static long getThreadId() {
            return idThreadLocal.get();
        }

    }
}
