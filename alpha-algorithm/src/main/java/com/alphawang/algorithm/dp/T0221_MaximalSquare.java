package com.alphawang.algorithm.dp;

import com.alphawang.algorithm.design.timingwheel.Test;
import java.util.Arrays;

/**
 * https://leetcode.com/problems/maximal-square/
 * Medium
 * 
 * 在一个由 0 和 1 组成的二维矩阵内，找到只包含 1 的最大正方形，并返回其面积。
 */
public class T0221_MaximalSquare {

    /**
     * 1. DP
     *    状态 dp[i][j] : 以(i,j)元素为左上角的最大边长 
     *    方程 dp[i][j] = min{ dp[i+1][j], dp[i][j+1], dp[i+1][j+1] } + 1
     *                   min{ 右方、下方、右下方 的最大边长 } + 1
     *    
     *    此题状态方程，怎么想到的！！！
     *    
     *    10ms - 15%
     */
    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }
        
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dp = new int[m][n];
        
        int maxLength = 0;
        for (int i = m - 1; i >=0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (j == n - 1 || i == m - 1) {
                    dp[i][j] = matrix[i][j] - '0';
                } else {
                    if (matrix[i][j] == '1') {
                        dp[i][j] = Math.min(Math.min(dp[i + 1][j], dp[i][j + 1]), dp[i + 1][j + 1]) + 1;
                    }
                }
                maxLength = Math.max(maxLength, dp[i][j]);
            }
        }
          
        return maxLength * maxLength;
    }

    public static void main(String[] args) {
        
        // 4
        test(new char[][] {
          {'1', '0', '1', '0', '0'},
          {'1', '0', '1', '1', '1'},
          {'1', '1', '1', '1', '1'},
          {'1', '0', '0', '1', '0'}
        });
    }
    
    private static void test(char[][] matrix) {
        T0221_MaximalSquare sut = new T0221_MaximalSquare();
        System.out.println(Arrays.deepToString(matrix));
        System.out.println(sut.maximalSquare(matrix));
    }

}
