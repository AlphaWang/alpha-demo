package com.alphawang.algorithm.dp;

/**
 * https://leetcode.com/problems/edit-distance/
 * Hard
 * 
 * 给你两个单词 word1 和 word2，请你计算出将 word1 转换成 word2 所使用的最少操作数 。
 */
public class T0072_EditDistance {

    /**
     *  DP
     *  状态：`dp[i,j]` word1的前i个字符，替换为word2前j个字符，需要的最少步数   
     *  方程：if w1[i] == w2[j], `dp[i,j] = dp[i-1,j-1]`;   
     *       if w1[i] != w2[j], `dp[i,j] = 1 + min{ dp[i-1,j], dp[i,j-1], dp[i-1,j-1] }`; //分别对应增/删/替换
     *       
     *  可画矩阵帮助理解   
     *  https://leetcode-cn.com/problems/edit-distance/solution/bian-ji-ju-chi-by-leetcode-solution/
     *  
     *  9ms - 16%
     */
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        if (m == 0) return n;
        if (n == 0) return m;
        
        int[][] dp = new int[m + 1][n + 1]; 
        for (int i = 1; i <= m; i++) {
            dp[i][0] = i;
            // dp[i][0] = dp[i - 1][0] + 1;
        }
        for (int i = 1; i <= n; i++) {
            dp[0][i] = i;
            // dp[0][i] = dp[0][i - 1] + 1;
        }
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j], Math.min(dp[i][j - 1], dp[i - 1][j - 1]));
                }
            }
        }
        
        return dp[m][n];
    }

    public static void main(String[] args) {
        /*
         * 输入：word1 = "horse", word2 = "ros"
         * 输出：3
         * 解释：
         * horse -> rorse (将 'h' 替换为 'r')
         * rorse -> rose (删除 'r')
         * rose -> ros (删除 'e')
         */
        test("horse", "ros");

        /*
         * 输入：word1 = "intention", word2 = "execution"
         * 输出：5
         * 解释：
         * intention -> inention (删除 't')
         * inention -> enention (将 'i' 替换为 'e')
         * enention -> exention (将 'n' 替换为 'x')
         * exention -> exection (将 'n' 替换为 'c')
         * exection -> execution (插入 'u')
         *
         */
        test("intention", "execution");
        
    }
    
    private static void test(String word1, String word2) {
        T0072_EditDistance sut = new T0072_EditDistance();
        System.out.println(String.format("%s --> %s : %s", word1, word2, sut.minDistance(word1, word2)));
    }
}
