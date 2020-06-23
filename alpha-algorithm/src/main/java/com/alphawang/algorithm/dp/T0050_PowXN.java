package com.alphawang.algorithm.dp;

/**
 * https://leetcode.com/problems/powx-n/
 * Medium
 * 
 * Implement pow(x, n), which calculates x raised to the power n (xn).
 */
public class T0050_PowXN {

    /**
     * 1. 暴力
     *    StackOverflowError for 0.00001, 2147483647
     */
    public double myPow(double x, int n) {
        if (n == 0) {
            return 1.0;
        }
        if (n == 1) {
            return x;
        }
        if (n < 0) {
            return 1 / myPow(x, -n);
        }
        
        return x * myPow(x, n - 1);
    }

    /**
     * 2. 分治 + 递归
     * 
     *    StackOverflow for 1.00000  -2147483648  
     *    TODO why? --> -n 溢出!
     */
    public double myPow2(double x, int n) {
        if (n == 0) {
            return 1.0;
        }
        if (n == 1) {
            return x;
        }
        if (n < 0) {
            return 1 / myPow2(x, -n);
        }
        
        double halfPow = myPow2(x, n / 2);
        
        return n % 2 == 0 ? halfPow * halfPow : halfPow * halfPow * x;
    }

    /**
     * 3. 分治 + 递归 
     */
    public double myPow3(double x, int n) {
        long longN = n;
        if (longN < 0) {
            return 1 / _myPow3(x, -longN);
        }
        return _myPow3(x, longN);
    }
    
    private double _myPow3(double x, long n) {
        if (n == 0) {
            return 1.0;
        }
        if (n == 1) {
            return x;
        }
        
        double y = _myPow3(x, n / 2);
        return n % 2 == 0 ? y * y : y * y * x;
    }

    /**
     * 4. 二分 + 迭代
     */
    public double myPow4(double x, int n) {
        long longN = n;
        if (longN < 0) {
            longN = -longN;
            x = 1/x;
        }

        double res = 1;
        double tmp = x;
        
        while(longN > 0) {
            if ((longN & 1) == 1) res *= tmp;
//            if (longN % 2 == 1) res *= tmp;
            tmp *= tmp;
            
            longN >>= 1;
//            longN = longN / 2;
        }
        return res;
    }

    public static void main(String[] args) {
        T0050_PowXN sut = new T0050_PowXN();

        System.out.println(sut.myPow4(2.0, 10)); //1024
        System.out.println(sut.myPow4(2.1, 3)); //9.261
        System.out.println(sut.myPow4(2, -2)); //0.25
        System.out.println(sut.myPow4(0.00001, 2147483647)); //0.00
        System.out.println(sut.myPow4(1.00000, -2147483648)); //1.00
        System.out.println(sut.myPow4(2.00000, -2147483648)); //0.00
        
    }

}
