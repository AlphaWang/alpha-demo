package com.alphawang.algorithm.string;

/**
 * https://leetcode.com/problems/valid-palindrome-ii/
 * Easy
 * 
 * 给定一个非空字符串 s，最多删除一个字符。判断是否能成为回文字符串。
 */
public class T0680_ValidPalindrome2 {

    /**
     * 1. 双指针
     *    5ms - 95%
     */
    public boolean validPalindrome(String s) {
        char[] chars = s.toCharArray();
        int m = chars.length;
        
        int start = 0, end = m - 1;
        while (start < end) {
            if (chars[start] == chars[end]) {
                start++;
                end--;
            } else  {
                // 如果遇到不相等的
                // 跳过 start, 或跳过end
                return validPalindrome(chars, start+1, end) || validPalindrome(chars, start, end-1);
            } 
        }
        return true;
    }

    /**
     * 优化 while 判断逻辑
     */
    public boolean validPalindrome2(String s) {
        char[] chars = s.toCharArray();
        int m = chars.length;

        int start = -1, end = m;
        while (start++ < end--) {
            if (chars[start] != chars[end]) {
                return validPalindrome(chars, start+1, end) || validPalindrome(chars, start, end-1);
            }
        }
        return true;
    }

    boolean validPalindrome(char[] chars, int start, int end) {
        while (start < end) {
            if (chars[start] != chars[end]) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    public static void main(String[] args) {
        // true
        test("aba");
        // true
        test("abca");
        // true
        test("ab");
        // false
        test("abcda");
        // true
        test("aguokepatgbnvfqmgmlcupuufxoohdfpgjdmysgvhmvffcnqxjjxqncffvmhvgsymdjgpfdhooxfuupuculmgmqfvnbgtapekouga");
        
    }
    
    private static void test(String s) {
        System.out.println(String.format("%s -> %s", s, new T0680_ValidPalindrome2().validPalindrome2(s)));
    }
    
}
