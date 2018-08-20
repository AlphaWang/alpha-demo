package com.alphawang.concurrency.c01.thread;

public class VolatileDemo {
    
    private static int x = 0, y = 0;
    private static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            /**
             * 可能存在指令重排
             */
            a = 1;
            x = b;
        });
        
        Thread t2 = new Thread(() -> {
            b = 1;
            y = a;
        });
        
        t1.start();
        t2.start();
        
        t1.join();
        t2.join();

        /**
         * 0,1
         * 1,0
         * 1,1
         * 0,0 -->重排序导致
         */
        System.out.println("x=" + x + ", y=" + y);
    }

    private static void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
