package com.alphawang.algorithm.string;

/**
 * https://leetcode.com/problems/reverse-only-letters/
 * Easy
 */
public class T0917_ReverseOnlyLetters {

    /**
     * 1. 双指针夹逼
     * 
     *    1ms - 63%
     */
    public String reverseOnlyLetters(String S) {
         if (S == null) {
             return S;
         }
         
         char[] chars = S.toCharArray();
         int m = chars.length;
         int start = 0, end = m - 1;
         while (start < end) {
             while (start < end && !isLetter(chars[start])) {
                 start++;
             }

             while (start < end && !isLetter(chars[end])) {
                 end--;
             }
             swap(chars, start, end);
             start++;
             end--;
         }
         
         return String.valueOf(chars);
    }
    
    private boolean isLetter(char c) {
        return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
    }
    
    private void swap(char[] chars, int i, int j) {
        char tmp = chars[i];
        chars[i] = chars[j];
        chars[j] = tmp;
    }

    public static void main(String[] args) {
        /*
         * Input:  "ab-cd"
         * Output: "dc-ba"
         */
        test("ab-cd");

        /*
         * Input:  "a-bC-dEf-ghIj"
         * Output: "j-Ih-gfE-dCba" 
         */
        test("a-bC-dEf-ghIj");

        /* 
         * Input:  "Test1ng-Leet=code-Q!"
         * Output: "Qedo1ct-eeLg=ntse-T!"
         */
        test("Test1ng-Leet=code-Q!");
    }
    
    private static void test(String s) {
        System.out.println(String.format("%s -> \n%s", s, new T0917_ReverseOnlyLetters().reverseOnlyLetters(s)));
    }

}
