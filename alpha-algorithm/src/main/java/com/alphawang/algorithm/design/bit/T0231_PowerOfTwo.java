package com.alphawang.algorithm.design.bit;

/**
 * 231: Power of Two `***` `E`  
 * https://leetcode.com/problems/power-of-two/    
 *   > 1: 不断 mod 2, 测试是否能被2整除   
 *   > 2: 数学求 log2     
 *   > 3: 位运算：特点 最前面是1，后面全0：x & (x-1) == 0    
 */
public class T0231_PowerOfTwo {

    /**
     * 1. 位运算：去除最后一位1
     *    1ms - 100%
     */
    public boolean isPowerOfTwo(int n) {
        if (n <= 0) {
            return false;
        }
        n = n & (n-1);
        
        return n == 0;
    }

    public boolean isPowerOfTwo1_1(int n) {
        return n > 0 && (n & (n-1)) == 0;
    }

    /**
     * 2. 位运算：依次右移一位
     *    1ms - 100%
     */
    public boolean isPowerOfTwo2(int n) {
        if (n <= 0 ) return false;
        while (n != 0) {
            if ((n & 1) != 0) { // 最后一位为1
                return n == 1;
            }
            n = n >> 1;
        }
        
        return true;
    }

    public static void main(String[] args) {
        test(1); // true
        test(16); // true
        test(218); // false
        test(-2147483648); // false
    }
    
    private static void test(int n) {
        T0231_PowerOfTwo sut = new T0231_PowerOfTwo();
        System.out.println(String.format("%s (%s) --> %s", n, Integer.toBinaryString(n), 
                                         sut.isPowerOfTwo2(n)));
    }
}
