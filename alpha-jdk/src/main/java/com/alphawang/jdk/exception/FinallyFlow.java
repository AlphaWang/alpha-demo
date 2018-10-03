package com.alphawang.jdk.exception;

public class FinallyFlow {
    public static void main(String[] args) {
        System.out.println(testFinal());
    }
    
    private static int testFinal() {
        try {
            int i  = 10;
            return i / 0;
        } catch (Exception e) {
            System.err.println("Exception! " + e.getMessage());
            return 100;
        } finally {
            System.err.println("finally! ");
            return 200;
        }
        
    }
}
