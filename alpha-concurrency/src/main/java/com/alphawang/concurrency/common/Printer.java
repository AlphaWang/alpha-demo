package com.alphawang.concurrency.common;

public class Printer {
    
    public static void print(Object msg) {
        System.out.println(String.format("[%s] - %s", Thread.currentThread().getName(), msg));
    }

}
