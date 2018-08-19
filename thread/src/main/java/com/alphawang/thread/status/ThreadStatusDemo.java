package com.alphawang.thread.status;

import java.util.concurrent.TimeUnit;

/**
 * 执行之后通过 `jstack` 查看线程状态
 * 
 * 
 */
public class ThreadStatusDemo {

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
                synchronized (ThreadStatusDemo.class){
                    try {
                        ThreadStatusDemo.class.wait();
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
