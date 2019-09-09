package com.alphawang.jdk.exception;

import java.util.concurrent.ThreadLocalRandom;

public class RuntimeExceptionDemo {

    public static void main(String[] args) {

        try {
            System.out.println(method2());
        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException(e.getCause());
        }

        /**
         * will not executed if thrown exception.
         */
        System.out.println("END.");
    }
    
    private static int method2() {
        int num = method1();

        System.out.println(num);

        if (num % 2 == 0) {
            throw new RuntimeException("test");
        }
        
        return num;
    }
    
    private static int method1() {
        return ThreadLocalRandom.current().nextInt();
    }
}
