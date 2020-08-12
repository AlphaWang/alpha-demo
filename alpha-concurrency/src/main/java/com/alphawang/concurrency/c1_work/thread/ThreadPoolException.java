package com.alphawang.concurrency.c1_work.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolException {

    static ThreadPoolExecutor executor = buildThreadPool();

    public static void main(String[] args) {
        

        /**
         * execute() 中如果抛出异常，直接打印堆栈
         */
        executor.execute(() -> doWork("task-0-exception"));

        /**
         *  submit() 中如果抛出异常，不get()是不会打印堆栈的
         */
        executor.submit(() -> doWork("task-1-exception"));
        Future<?> future = executor.submit(() -> doWork("task-2-exception"));
        /**
         * get() 时才会打印堆栈
         */
        try {
            future.get();
        } catch (InterruptedException e) {
            System.out.println("got InterruptedException " + e.getMessage());
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println("got ExecutionException " + e.getMessage());
            e.printStackTrace();
        }

        executor.execute(() -> doWork("task-3"));
        executor.execute(() -> doWork("task-4 - exception"));
        executor.execute(() -> doWork("task-5"));
        executor.execute(() -> doWork("task-6"));
        executor.execute(() -> doWork("task-7"));
        executor.execute(() -> doWork("task-8"));
        executor.execute(() -> doWork("task-9"));
    }
    
    private static ThreadPoolExecutor buildThreadPool() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
          3,
          10,
          1000,
          TimeUnit.SECONDS,
          new ArrayBlockingQueue<>(1000)
        );
        
//        executor.setThreadFactory(new ThreadFactory() {
//            @Override
//            public Thread newThread(Runnable r) {
//                Thread t = new Thread(r, "ALPHA_THREAD_");
//                return t;
//            }
//        });
        
        return executor;
    }
    
    private static void doWork(String name) {
        String info = String.format("[%s] - %s", Thread.currentThread().getName(), name);
        if (name.contains("exception")) {
            throw new RuntimeException(info + " 抛出异常！");
        }

        System.out.println(info);
        System.out.println("thread count: " + executor.getPoolSize());
    }

}
