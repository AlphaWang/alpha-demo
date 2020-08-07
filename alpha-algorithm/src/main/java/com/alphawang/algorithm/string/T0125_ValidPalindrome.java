package com.alphawang.algorithm.string;

/**
 * https://leetcode.com/problems/valid-palindrome/
 * Easy
 * 
 * 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
 */
public class T0125_ValidPalindrome {

    /**
     * 1. 双指针夹逼：遍历原始字符串
     *    注意跳过非字符、toLowerCase
     *    
     *    2ms - 99%
     */
    public boolean isPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }

        char[] chars = s.toCharArray();
        int start = 0, end = chars.length - 1;
        while (start < end) {
            // 忽略右侧非法字符
            while (start < end && !Character.isLetterOrDigit(chars[end])) {
                end--;
            }

            // 忽略左侧非法字符
            while (start < end && !Character.isLetterOrDigit(chars[start])) {
                start++;
            }
            
            if (Character.toLowerCase(chars[start]) != Character.toLowerCase(chars[end])) {
                return false;
            }
            start++;
            end--;
        }
            
        return true;
    }

    public static void main(String[] args) {
        // true
        test("A man, a plan, a canal: Panama");
        // false
        test("race a car");
    }

    public static void test(String s) {
        System.out.println(String.format("%s --> %s", s, new T0125_ValidPalindrome().isPalindrome(s)));
    }

}
