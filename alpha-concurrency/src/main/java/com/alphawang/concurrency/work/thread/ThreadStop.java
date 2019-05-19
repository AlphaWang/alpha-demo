package com.alphawang.concurrency.work.thread;

import java.util.concurrent.TimeUnit;

public class ThreadStop {

    public static void main(String[] args) throws InterruptedException {
         Proxy proxy = new Proxy();
         proxy.start();
         
         TimeUnit.SECONDS.sleep(5);
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
                while (!terminated) {
                    
                    doReport();

                    try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (InterruptedException e) {
                        System.out.println("SLEEP InterruptedException " + e);
                        Thread.currentThread().interrupt();
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
