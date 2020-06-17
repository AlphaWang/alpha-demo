package com.alphawang.algorithm.number;

/**
 * https://leetcode.com/problems/ugly-number/
 */
public class T0263_UglyNumber {

    /**
     * 1. 递归
     */
    public boolean isUgly(int num) {
        int[] factors = new int[] {2, 3, 5};
        if (num == 1) {
            return true;
        }
        return help(num, factors);
    }
    
    public boolean help(int num, int[] factors) {
        for (int factor : factors) {
            if (num == factor) {
                return true;
            } else if (num > factor && num % factor == 0) {
                return help(num / factor, factors);
            }
        }
        return false;
    }

    /**
     * 2. 递归2
     */
    public boolean isUgly2(int num) {
        int[] factors = new int[] {2, 3, 5};
        if (num < 1) {
            return false;
        }
        return help2(num, factors);
    }

    private boolean help2(int num, int[] factors) {
        if (num == 1) 
            return true;
        
        for (int factor : factors) {
            if (num % factor == 0)
                return help2(num / factor, factors);
        }
        return false;
    }

    /**
     * 3. 迭代
     */
    public boolean isUgly3(int num) {
        if (num <= 0) return false;
        if (num == 1) return true;
        while (num != 1) {
            if (num % 2 == 0) num /= 2;
            else if (num % 3 == 0) num /= 3;
            else if (num % 5 == 0) num /= 5;
            else return false;
        }
        
        return true;
    }
    
    

    public static void main(String[] args) {
        T0263_UglyNumber sut = new T0263_UglyNumber();
        sut.test(1); //true
        sut.test(6); //true
        sut.test(8); //true
        sut.test(14); //false
    }
    
    private void test(int num) {
        System.out.println(String.format("%s : %s", num, isUgly3(num)));
    }

}
