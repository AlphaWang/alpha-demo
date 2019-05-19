package com.alphawang.concurrency.work.thread;

import java.util.concurrent.TimeUnit;

public class ThreadInterrupt {

    private static int i;

    public static void main(String[] args) throws InterruptedException {
        testInterrupt();
        testInterrupted();
    }

    private static void testInterrupt() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                i++;
            }
            System.out.println(i);
        }, "interruptDemo");

        thread.start();
        TimeUnit.MILLISECONDS.sleep(100);

        //false
        System.out.println("before interrupt: " + thread.isInterrupted());
        thread.interrupt(); //设置interrupt标识为true

        //true
        System.out.println("after interrupt: " + thread.isInterrupted());
    }

    private static void testInterrupted() throws InterruptedException {
        Thread thred = new Thread(() -> {
            while (true) {
                boolean in = Thread.currentThread().isInterrupted();
                if (in) {
                    // true
                    System.out.println("before interrupted: " + in);
                    /**
                     * interrupted 会复位中断状态！
                     */
                    Thread.interrupted();
                    // false
                    System.out.println("after interrupted: " + Thread.currentThread().isInterrupted());
                }
            }
        });
        thred.start();
        TimeUnit.MILLISECONDS.sleep(100);
        thred.interrupt(); //终端
    }

}
