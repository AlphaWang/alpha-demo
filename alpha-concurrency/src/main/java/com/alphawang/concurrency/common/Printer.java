package com.alphawang.concurrency.common;

public class Printer {
    
    public static void print(Object msg) {
        System.out.println(String.format("[%s] [%s] - %s", System.currentTimeMillis(), Thread.currentThread().getName(), msg));
    }

}
