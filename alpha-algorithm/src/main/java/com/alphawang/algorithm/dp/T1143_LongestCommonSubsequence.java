package com.alphawang.algorithm.dp;

/**
 * https://leetcode.com/problems/longest-common-subsequence/
 * Medium
 */
public class T1143_LongestCommonSubsequence {

    /**
     *   > 1: DP
     *   >    状态：二维数组，行: text1, 列: text2
     *   >    方程：if (s1[i-1] != s2[j-1]) LCS[i, j] = max{ LCS[i - 1, j], LCS[i, j - 1] }
     *   >        if (s1[i-1] == s2[j-1]) LCS[i, j] = LCS[i - 1, j -1] + 1
     *   
     *   9ms - 90%
     */
    public int longestCommonSubsequence(String text1, String text2) {
        if (text1 == null || text2 == null) {
            return 0;
        }
        
        int m = text1.length();
        int n = text2.length();
        
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i < m + 1; i++) {
            dp[i][0] = 0;
        }
        for (int i = 0; i < n + 1; i++) {
            dp[0][i] = 0;
        }
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (text1.charAt(i) == text2.charAt(j)) {
                    dp[i+1][j+1] = dp[i][j] + 1;
                } else {
                    dp[i+1][j+1] = Math.max(dp[i][j+1], dp[i+1][j]);
                }
            }
        }
        
        return dp[m][n];
    }

    public static void main(String[] args) {
        // 3
        test("abcde", "ace");
        // 3
        test("abc", "abc");
        // 0 
        test("abc", "def");
    }
    
    private static void test(String t1, String t2) {
        System.out.println(String.format("%s : %s --> %s", t1, t2, 
          new T1143_LongestCommonSubsequence().longestCommonSubsequence(t1, t2)));
    }
}
