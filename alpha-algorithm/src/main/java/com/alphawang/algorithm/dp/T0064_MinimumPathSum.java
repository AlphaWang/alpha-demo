package com.alphawang.algorithm.dp;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/minimum-path-sum/
 * Medium
 * 
 * 给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
 * 说明：每次只能向下或者向右移动一步。
 */
public class T0064_MinimumPathSum {

    /**
     * 1. DP
     * dp[i][j] = min{dp[i][j+1], dp[i+1][j]} + nums[i][j]
     * 
     * O(M*N)
     * 3ms - 41%
     */
    public int minPathSum(int[][] grid) {
        System.out.println(Arrays.deepToString(grid));
        
        if (grid == null || grid.length == 0) {
            return 0;
        }
        
        for (int i = grid.length - 1; i >= 0; i--) {
            for (int j = grid[0].length - 1; j >=0; j--) {
                if (i == grid.length - 1 && j == grid[0].length - 1) {
                    continue;
                } 
                if (j == grid[0].length - 1) {
                    grid[i][j] = grid[i+1][j] + grid[i][j];
                } else if (i == grid.length - 1){
                    grid[i][j] = grid[i][j+1] + grid[i][j];
                } else {
                    grid[i][j] = Math.min(grid[i+1][j], grid[i][j+1]) + grid[i][j];
                }
            }
        }

        System.out.println(Arrays.deepToString(grid));

        return grid[0][0];
    }

    public static void main(String[] args) {
        /*
         * 输入:
         * [
         *   [1,3,1],
         *   [1,5,1],
         *   [4,2,1]
         * ]
         * 输出: 7
         * 解释: 因为路径 1→3→1→1→1 的总和最小。
         */
        test(new int[][]{
          {1, 3, 1},
          {1, 5, 1},
          {4, 2, 1}
        }); 
    }
    
    private static void test(int[][] grid) {
        T0064_MinimumPathSum sut = new T0064_MinimumPathSum();
        System.out.println(sut.minPathSum(grid));
    }
    
}
