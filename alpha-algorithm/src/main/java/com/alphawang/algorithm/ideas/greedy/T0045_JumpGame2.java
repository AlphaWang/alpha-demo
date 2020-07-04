package com.alphawang.algorithm.ideas.greedy;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/jump-game-ii/
 * Hard
 * 
 * 给定一个非负整数数组，你最初位于数组的第一个位置。
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * 你的目标是使用最少的跳跃次数到达数组的最后一个位置。
 */
public class T0045_JumpGame2 {

    /**
     * 1. DP
     *    状态: dp[n] = 索引 n 到最后位置的 minSteps
     *    状态转移方程：dp[n] = min{dp[n + 1...nums[n]]}
     *    
     *    452ms - 11%
     */
    public int jump(int[] nums) {
        if (nums == null || nums.length == 0 || nums.length == 1 || nums[0] == 0 ) {
            return 0;
        }
        if (nums.length < 3) {
            return 1;
        }
        
        int[] dp = new int[nums.length];
        
        dfs(nums, dp, nums.length - 2);
        
        return dp[0];
    }

    private void dfs(int[] nums, int[] dp, int n) {
        if (n < 0) {
            return;
        }
        
        int value = nums[n];
       
        int min = Integer.MAX_VALUE;
        for (int j = 1; j <= value; j++) {
            if (n + j < dp.length) {
                min = Math.min(min, dp[n + j]);
            }
        }
        
        dp[n] = min == Integer.MAX_VALUE ? Integer.MAX_VALUE : min + 1;
        System.out.println(String.format("[%s] --> %s", n, Arrays.toString(dp)));
        
        dfs(nums, dp, n - 1);
    }

    public static void main(String[] args) {
        T0045_JumpGame2 sut = new T0045_JumpGame2();
        /*
         * Input: [2,3,1,1,4]
         * Output: 2
         * Explanation: The minimum number of jumps to reach the last index is 2.
         *     Jump 1 step from index 0 to 1, then 3 steps to the last index.
         */
        System.out.println(sut.jump(new int[] {2,3,1,1,4}));
        
        /*
         * [2,3,0,1,4]
         * 2
         */
        System.out.println(sut.jump(new int[] {2,3,0,1,4}));
        /*
         * [1,2,3]
         *  2
         */
        System.out.println(sut.jump(new int[] {1,2,3}));
        /*
         * [5,9,3,2,1,0,2,3,3,1,0,0]
         * 3
         */
        System.out.println(sut.jump(new int[] {5,9,3,2,1,0,2,3,3,1,0,0}));
    }

}
