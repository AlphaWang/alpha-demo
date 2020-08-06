package com.alphawang.algorithm.string;

/**
 * https://leetcode.com/problems/reverse-string-ii/
 * Easy
 */
public class T0541_ReverseString2 {

    /**
     * 1. 左右指针夹逼
     *    1ms - 85%
     */
    public String reverseStr(String s, int k) {
        if (s == null) {
            return s;
        }
        
        int left = 0, right = k;
        int m = s.length();
        char[] chars = s.toCharArray();
        while (left < m) {
            int i = left, j = Math.min(right, m) - 1; 
            while (i <= j) {
                swap(chars, i++, j--);
            }
            
            left += 2 * k;
            right += 2 * k;
        }
        
        return String.valueOf(chars);
    }
    
    private void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }

    public static void main(String[] args) {
        // bacdfeg
        test("abcdefg", 2);
        // gfedcba
        test("abcdefg", 8);
        
        test(  "hyzqyljrnigxvdtneasepfahmtyhlohwxmkqcdf" 
                + "ehybknvdmfrfvtbsovjbdhevlfxpdaovjgunjql" 
                + "imjkfnqcqnajmebeddqsgl",39);
    }
    
    private static void test(String s, int k) {
        System.out.println(String.format("%s - %s --> %s", s, k, new T0541_ReverseString2().reverseStr(s, k)));
    }
    
}
