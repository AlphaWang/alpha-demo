package com.alphawang.algorithm.ideas.greedy;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/jump-game/
 * Medium
 * 
 * 给定一个非负整数数组，你最初位于数组的第一个位置。
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * 判断你是否能够到达最后一个位置。
 */
public class T0055_JumpGame {

    /**
     * 1. 遍历 + 标记 
     *    752ms - 12%
     */
    public boolean canJump(int[] nums) {
        int length = nums.length;
        boolean[] status = new boolean[length];
        status[0] = true;
        for (int i = 0; i < length; i++) {
            if (!status[i]) {
                continue;
            }
            int step = nums[i];
            for (int j = 1; j <= step; j++) {
                if (i + j >= length) {
                    break;
                }
                if (i + j == length - 1) {
                    return true;
                }
                status[i + j] = true;
            }
        }

        return status[length - 1];
    }

    /**
     * 2. 贪心，从前往后遍历，维护最大可达位置
     *    1ms - 99%
     */
    public boolean canJump2(int[] nums) {
        int max = 0, length = nums.length;
        for (int i = 0; i < length; i++) {
            if (i > max) {
                return false; // 跳不到位置i
            }
            max = Math.max(max, nums[i] + i);
        }
        return max >= length - 1;
    }

    /**
     * 3. 贪心，从后往前遍历，if (nums[i] + i >= endReachable) endReachable = i;
     *    1ms - 99%
     */
    public boolean canJump3(int[] nums) {
        int endReachable = nums.length - 1;
        for (int i = endReachable - 1; i >= 0; i--) {
            if (i + nums[i] >= endReachable) {
                endReachable = i;
            }
        }
        
        return endReachable <= 0;
    }

    public static void main(String[] args) {
        /*
         * Input: nums = [2,3,1,1,4]
         * Output: true
         * Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
         */
        test(new int[] {2,3,1,1,4});

        /*
         * Input: nums = [3,2,1,0,4]
         * Output: false
         * Explanation: You will always arrive at index 3 no matter what. Its maximum jump length is 0, which makes it impossible to reach the last index.
         */
        test(new int[] {3,2,1,0,4});
    }
    
    private static void test(int[] nums) {
        T0055_JumpGame sut = new T0055_JumpGame();
        System.out.println("input " + Arrays.toString(nums));
        System.out.println(sut.canJump3(nums));
    }
}
