package com.alphawang.algorithm.dp;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/palindromic-substrings/
 * Medium
 * 
 * 给定一个字符串，你的任务是计算这个字符串中有多少个回文子串。
 */
public class T0647_PalindromicSubstrings {

    /**
     * 1. DP
     * 状态：dp[i][j] 表示字符串s在[i,j]区间的子串是否是一个回文串。
     * 状态转移方程：当 s[i] == s[j] && (j - i <= 1 || dp[i + 1][j - 1]) 时，dp[i][j]=true，否则为false
     * 
     * 17ms - 24%
     */
    public int countSubstrings(String s) {
        System.out.println("---" + s);
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int length = s.length();
        boolean[][] dp = new boolean[length][length];
        int res = 0;
        
        for (int i = 0; i < length; i++) {
            for (int j = 0; j <= i; j++) {
                if (s.charAt(i) == s.charAt(j)
                    && (i - j <= 1 || dp[j+1][i-1] )) {
                    dp[j][i] = true;
                    res++;
                }
                System.out.println(String.format("[%s, %s] : [%s, %s] - %s", j, i, s.charAt(j), s.charAt(i), Arrays.deepToString(dp)));
            }
        }

        return res;
    }

    /**
     * 2. 从中心扩展
     *    区分长度为奇数、偶数的子串，分别处理
     *    
     *    10ms - 42%
     */
    public int countSubstrings2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int length = s.length();
        int res = 0;
        for (int center = 0; center < length; center++) {
            // 1. 长度为奇数的回文子串
            int left = center;
            int right = center;
            System.out.println(String.format("center = %s, [%s, %s]", center, left, right));
            while (left >= 0 && right < length && s.charAt(left) == s.charAt(right)) {
                res++;
                left--;
                right++;
            }

            // 2. 长度为偶数的回文子串
            left = center;
            right = center + 1;
            System.out.println(String.format("center = %s, [%s, %s]", center, left, right));
            while (left >= 0 && right < length && s.charAt(left) == s.charAt(right)) {
                res++;
                left--;
                right++;
            }
            
        }
        
        return res;
    }

    public static void main(String[] args) {
        /*
         * 输入: "abc"
         * 输出: 3
         * 解释: 三个回文子串: "a", "b", "c".
         */
        test("abc");

        /*
         * 输入: "aaa"
         * 输出: 6
         * 说明: 6个回文子串: "a", "a", "a", "aa", "aa", "aaa".
         */
        test("aaa");
    }
    
    private static void test(String s) {
        T0647_PalindromicSubstrings sut = new T0647_PalindromicSubstrings();
        System.out.println(s + " --> " + sut.countSubstrings2(s));
    }

}
