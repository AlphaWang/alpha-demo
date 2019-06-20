package com.alphawang.concurrency.c3_mutex.threadlocal;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalWithThreadPool {

    public static void main(String[] args) {

        ExecutorService threadpool = Executors.newFixedThreadPool(10);

        for (int i = 1; i <= 100; i++) {
            int finalI = i;
            threadpool.execute(new Runnable() {
                @Override
                public void run() {
                    /**
                     * 如果不reset, 则可能取到线程 上一个生命周期内 set的值！！！
                     *
                     * pool-1-thread-4 + SET 70
                     * pool-1-thread-4 - GET 70
                     * pool-1-thread-4 + SET NOOOOOOOOOO
                     * pool-1-thread-4 - GET 70
                     */
                    ThreadLocalHolder.reset();

                    if (new Random().nextInt() % 2 == 0) {
                        System.out.println(Thread.currentThread().getName() + " + SET " + finalI);

                        ThreadLocalHolder.setClient(String.valueOf(finalI));

                    } else {
                        System.out.println(Thread.currentThread().getName() + " + SET NOOOOOOOOOO");
                    }

                    System.out.println(Thread.currentThread().getName() + " - GET " + ThreadLocalHolder.getClient());
                }
            });

            //            Thread.sleep(10);
        }

        threadpool.shutdown();

    }

    static class ThreadLocalHolder {
        private static final ThreadLocal<String> CLIENT = new ThreadLocal<>();

        public static String getClient() {
            return CLIENT.get();
        }

        public static void setClient(String client) {
            CLIENT.set(client);
        }

        public static void reset() {
            CLIENT.remove();
            CLIENT.set(null);
        }

    }
}
