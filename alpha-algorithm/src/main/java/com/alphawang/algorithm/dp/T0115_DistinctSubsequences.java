package com.alphawang.algorithm.dp;

/**
 * https://leetcode.com/problems/distinct-subsequences/submissions/
 * Hard
 * 
 * 给定一个字符串 S 和一个字符串 T，计算在 S 的子序列中 T 出现的个数。
 */
public class T0115_DistinctSubsequences {

    /**
     * 1: DP
     * 
     *    状态：dp[i][j] 表示 T 的前i字符串可以有 S j字符串组成的最多个数
     *    方程： if S[j] == T[i], dp[i][j] = dp[i-1][j-1] + dp[i][j-1]
     *         if S[j] != T[i], dp[i][j] = dp[i][j-1]
     *         
     *    5ms - 93%     
     */
    public int numDistinct(String s, String t) {
        int sLength = s.length();
        int tLength = t.length();
        
        int[][] dp = new int[tLength+1][sLength+1];
        // row-0: == 1, 第0行表示 t==空串，s子序列 包含空串的个数 == 1
        for (int i = 0; i < sLength; i++) {
            dp[0][i] = 1;
        }
        
        for (int i = 1; i <= tLength; i++) { // 行
            for (int j = 1; j <= sLength; j++) {  // 列
                if (t.charAt(i-1) == s.charAt(j-1)) {
                    //TODO
                    dp[i][j] = dp[i-1][j-1] + dp[i][j-1];
                } else {
                    //TODO
                    dp[i][j] = dp[i][j-1];
                }
            }
        }

        return dp[tLength][sLength];
    }

    public static void main(String[] args) {
        /*
         * Input: S = "rabbbit", T = "rabbit"
         * Output: 3
         * 
         * rabbbit
         * ^^^^ ^^
         * rabbbit
         * ^^ ^^^^
         * rabbbit
         * ^^^ ^^^
         */
        test("rabbbit", "rabbit");

        /*
         * Input: S = "babgbag", T = "bag"
         * Output: 5
         * 
         * babgbag
         * ^^ ^
         * babgbag
         * ^^    ^
         * babgbag
         * ^    ^^
         * babgbag
         *   ^  ^^
         * babgbag
         *     ^^^
         */
        test("babgbag", "bag");
    }

    public static void test(String s, String t) {
        System.out.println(String.format("%s - %s --> %s", s, t, new T0115_DistinctSubsequences().numDistinct(s, t)));
    }
}
