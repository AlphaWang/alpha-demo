package com.alphawang.algorithm.dp;

/**
 * https://leetcode.com/problems/longest-palindromic-substring/
 * Medium
 */
public class T0005_LongestPalindromicSubstring {

    /**
     * 1. 暴力: 两层循环遍历 start / end；再来一层循环判断是否回文 
     * 
     *    O(N^3)
     *    Time Limit Exceeded
     */
    public String longestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        
        int n = s.length();
        int maxLength = 1;
        String res = s.substring(0, 1);
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (isPalindrome2(s, i, j) && j - i + 1 > maxLength) {
                    maxLength = j - i;
                    res = s.substring(i, j + 1);
                }
            }
        }
        
        return res;
    }
    
    private boolean isPalindrome(String s, int start, int end) {
        while (start <= end && s.charAt(start) == s.charAt(end)) {
            if (start == end || start + 1 == end) {
                return true;
            }
            start++;
            end--;
        }
        
        return false;
    }

    private boolean isPalindrome2(String s, int start, int end) {
        while (start < end) {
            if (s.charAt(start) != s.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    /**
     * 2. 从中心往左右扩展：注意奇偶，分开处理
     * 
     *    O(N^2)
     *    25ms - 73%
     */
    public String longestPalindrome2(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }

        int n = s.length();
        int maxLength = 0;
        String res = null;
        
        for (int i = 0; i < n; i++) {
            String even = findPalindrome(s, i, i, n); 
            String odd = findPalindrome(s, i, i+1, n);
            String maxStr = even.length() > odd.length() ? even : odd;
            if (maxStr.length() > maxLength) {
                maxLength = maxStr.length();
                res = maxStr;
            }
        }
        
        return res;
    }
    
    // 返回回文串
    private String findPalindrome(String s, int left, int right, int n) {
        int l = -1, r = -1;
        while (left >= 0 && right < n && s.charAt(left) == s.charAt(right)) {
             l = left; r = right;
             left--;
             right++;
        }
        
        return l >= 0
            ? s.substring(l, r + 1)
            : "";
    }

    /**
     * 2. 从中心往左右扩展: 优化 子方法返回回文串长度，而非直接返回回文
     * 
     *    O(N^2)
     *    25ms - 73%
     */
    public String longestPalindrome2_1(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }

        int n = s.length();
        int maxLength = 0;
        int start = 0, end = 0;

        for (int i = 0; i < n; i++) {
            int len1 = findPalindrome2(s, i, i, n);
            int len2 = findPalindrome2(s, i, i+1, n);
            int maxLen = Math.max(len1, len2);
            if (maxLen > maxLength) {
                maxLength = maxLen;
                start = i - (maxLen - 1) / 2; //TODO
                end = i + (maxLen) / 2; 
            }
        }

        return s.substring(start, end + 1);
    }

    // 返回回文串长度
    private int findPalindrome2(String s, int left, int right, int n) {
        while (left >= 0 && right < n && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        
        return right - left - 1;
    }

    /**
     * 3. DP
     *     状态 dp[i][j] : [i, j] 子串是否回文 !!!!!
     *     
     *     方程  if s[i] == s[j], dp[i][j] = dp[i+1][j-1]  
     *          if s[i] != s[j], dp[i][j] = 0
     * 
     *    78ms - 40%
     */
    public String longestPalindrome3(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }

        int n = s.length();
        int maxLength = 0;
        int start = 0, end = 0;
        boolean[][] dp = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dp[i][j]) {
                    continue;
                }
                if (s.charAt(i) != s.charAt(j)) {
                    dp[i][j] = false;
                } else {
                    if (j - i <= 2) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i+1][j-1];
                    }
                }
                
                if (dp[i][j] && j - i + 1 > maxLength) {
                    maxLength = j - 1 + 1;
                    start = i;
                    end = j;
                }
            }
        }
        
        return s.substring(start, end + 1);
    }

    public static void main(String[] args) {
        // "bab"
        test("babad");
        // "bb"
        test("cbbd");
        // "a"
        test("a");
    }
    
    private static void test(String s) {
        System.out.println(String.format("%s --> %s", s, 
          new T0005_LongestPalindromicSubstring().longestPalindrome3(s)));
    }

}
