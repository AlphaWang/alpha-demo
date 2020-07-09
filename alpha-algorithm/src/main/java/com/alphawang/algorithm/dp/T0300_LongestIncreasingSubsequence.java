package com.alphawang.algorithm.dp;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/longest-increasing-subsequence/
 * Medium
 * 
 * 给定一个无序的整数数组，找到其中最长上升子序列的长度。
 *
 * 输入: [10,9,2,5,3,7,101,18]
 * 输出: 4 
 * 解释: 最长的上升子序列是 [2,3,7,101]，它的长度是 4。
 */
public class T0300_LongestIncreasingSubsequence {

    /**
     * 1. DP
     *    状态：dp[n] = n索引处的LIS长度
     *    方程：j = 0~i-1, if a[j]<a[i], then dp[i] = max(dp[j]) + 1
     *    
     *    13ms - 33%
     */
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            int maxLis = 0;
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    maxLis = Math.max(maxLis, dp[j]);
                }
            }
            dp[i] = maxLis + 1;
            res = Math.max(res, dp[i]);  // 记录出现过的最大值！
        }

        System.out.println(Arrays.toString(dp));
        return res;
    }

    public static void main(String[] args) {
        /*
         * Input: [10,9,2,5,3,7,101,18]
         * Output: 4 
         * Explanation: LIS = [2,3,7,101]
         */
        
        test(new int[] {10,9,2,5,3,7,101,18});
        /*
         * [1,3,6,7,9,4,10,5,6]
         * 6
         */
        test(new int[] {1,3,6,7,9,4,10,5,6});
    }

    private static void test(int[] nums) {
        T0300_LongestIncreasingSubsequence sut = new T0300_LongestIncreasingSubsequence();
        System.out.println(String.format("%s -> %s", Arrays.toString(nums), sut.lengthOfLIS(nums)));
    }

}
