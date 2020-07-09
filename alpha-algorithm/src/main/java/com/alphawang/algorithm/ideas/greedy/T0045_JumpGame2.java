package com.alphawang.algorithm.ideas.greedy;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

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
     *    状态转移方程：dp[n] = min{dp[n + 1], ..., dp[n + nums[n]]} + 1
     *    
     *    452ms - 11%
     *    378ms - 17%  优化循环跳出条件
     */
    public int jump(int[] nums) {
        if (nums == null || nums.length == 0 || nums.length == 1 || nums[0] == 0 ) {
            return 0;
        }
        if (nums.length == 2) {
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
            if (n + j >= dp.length) { 
                break;
            }
            
            min = Math.min(min, dp[n + j]);
        }
        
        dp[n] = min == Integer.MAX_VALUE ? Integer.MAX_VALUE : min + 1;
        System.out.println(String.format("[%s] --> %s", n, Arrays.toString(dp)));
        
        dfs(nums, dp, n - 1);
    }

    /**
     * 2. BFS 
     *    Time Limit Exceeded
     */
    public int jump2(int[] nums) {
        if (nums == null || nums.length == 0 || nums.length == 1 || nums[0] == 0 ) {
            return 0;
        }
        if (nums.length == 2) {
            return 1;
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        int level = 0;
        while (!queue.isEmpty()) {
            level++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int index = queue.poll();
                if (index >= nums.length - 1) {
                    return level - 1;
                }
                int value = nums[index]; 
                for (int j = 1; j <= value; j++) {
                    if (index + j >= nums.length - 1) {
                        return level;
                    }
                    queue.offer(index + j);
                }
                
            }
        }
        
        return Integer.MAX_VALUE;
    }

    /**
     * 3. 贪心，从后往前查找，每次找出跨度最大的位置
     *    370ms - 17% //TODO ???
     */
    public int jump3(int[] nums) {
//        if (nums == null || nums.length == 0 || nums.length == 1 || nums[0] == 0 ) {
//            return 0;
//        }
//        if (nums.length == 2) {
//            return 1;
//        }
        
        int steps = 0;
        
        // 改成while, 370ms - 17% --> 279ms - 24% 
//        for (int target = nums.length - 1; target > 0; ) {
        int target = nums.length - 1;
        while (target > 0) {
            for (int i = 0; i < target; i++) {
                if (i + nums[i] >= target) {
                    target = i;
                    steps++;
                    break;
                }
            }
        }
        
        return steps;
    }

    /**
     * TODO 
     * 4. 贪心 
     *    Let's say the range of the current jump is [curBegin, curEnd], 
     *    `reachable` is the farthest point that all points in [curBegin, curEnd] can reach. 
     *    
     *    Once the current point reaches `curEnd`, then trigger another jump, 
     *    and set the new curEnd with `reachable`, then keep the above steps:
     */
    public int jump4(int[] nums) {
        if (nums == null || nums.length == 0 || nums.length == 1) {
            return 0;
        }

        int curEnd = 0;
        int reachable = 0;
        int steps = 0;
        int target = nums.length - 1;
        for (int i = 0; i < target; i++){
            reachable = Math.max(reachable, nums[i] + i);
            if (curEnd == i) {
                curEnd = reachable;
                steps++;
            }
//            if (reachable >= target) {
//                return steps; 
//            }
        }
        return curEnd >= nums.length - 1 ? steps : -1;
    }

    public static void main(String[] args) {
       
        /*
         * Input: [2,3,1,1,4]
         * Output: 2
         * Explanation: The minimum number of jumps to reach the last index is 2.
         *     Jump 1 step from index 0 to 1, then 3 steps to the last index.
         */
        test(new int[] {2,3,1,1,4});
        
        /*
         * [2,3,0,1,4]
         * 2
         */
        test(new int[] {2,3,0,1,4});
        /*
         * [1,2,3]
         *  2
         */
        test(new int[] {1,2,3});
        /*
         * [5,9,3,2,1,0,2,3,3,1,0,0]
         * 3
         */
        test(new int[] {5,9,3,2,1,0,2,3,3,1,0,0});
    }
    
    private static void test(int[] nums) {
        T0045_JumpGame2 sut = new T0045_JumpGame2();
        System.out.println(Arrays.toString(nums));
        System.out.println(sut.jump4(nums));
    }

}
