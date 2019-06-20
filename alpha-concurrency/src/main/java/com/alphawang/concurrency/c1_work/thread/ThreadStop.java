package com.alphawang.concurrency.c1_work.thread;

import java.util.concurrent.TimeUnit;

public class ThreadStop {

    public static void main(String[] args) throws InterruptedException {
        Proxy proxy = new Proxy();
        proxy.start();

        TimeUnit.SECONDS.sleep(1);
        proxy.stop();
    }

    static class Proxy {
        volatile boolean terminated = false;
        boolean started = false;

        Thread reportThread;

        synchronized void start() {
            if (started) {
                return;
            }

            started = true;
            terminated = false;

            reportThread = new Thread(() -> {
                /**
                 * 1. 仅检查终止标志位是不够的，因为线程可能处于休眠状态
                 * 2. 仅检查isInterrupt也是不够的，因为第三方类库可能没有正确处理中断异常
                 */
                // while (!Thread.currentThread().isInterrupted()) {
                // while (!Thread.currentThread().isInterrupted() && !terminated) {
                // while (true) {
                while (!terminated) {

                    doReport();

                    try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (InterruptedException e) {
                        /**
                         * Q: 捕获 InterruptedException 究竟要做什么处理？？？
                         *
                         * 这里看起来只需要设置 terminated = true 就行？
                         */
                        System.out.println("SLEEP InterruptedException " + e);
                        System.out.println("isInterrupted: " + Thread.currentThread().isInterrupted());
                        Thread.currentThread().interrupt();
                        System.out.println("isInterrupted: " + Thread.currentThread().isInterrupted());

                        // terminated = true;
                        // break;
                    }
                }

                started = false;
            });

            reportThread.start();
        }

        synchronized void stop() {
            terminated = true;
            reportThread.interrupt();
        }

        void doReport() {
            System.out.println(Thread.currentThread().getName() + " "
                + System.currentTimeMillis()
                + " REPORTING...");
        }

    }
}
