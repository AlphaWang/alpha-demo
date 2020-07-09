package com.alphawang.algorithm.array;

import java.util.Arrays;

public class T0042_TrappingRainWater_DP {
    /**
     * https://leetcode.com/problems/trapping-rain-water/
     * 
     * Given n non-negative integers representing an elevation map where the width of each bar is 1, 
     * compute how much water it is able to trap after raining.
     * 
     */

    /**
     * 思路：动态规划
     * 先找到每个位置对应的左右 max 值
     */
    public static int trappingCount(int[] bars) {
        int length = bars.length;
        
        // 先找到每个位置对应的左右 max 值
        int[] leftMax = new int[length];
        int[] rightMax = new int[length];
        leftMax[0] = bars[0];
        for (int i = 1; i < length; i++) {
            leftMax[i] = Math.max(bars[i], leftMax[i - 1]);
        }
        rightMax[length - 1] = bars[length - 1];
        for (int i = length - 2; i >= 0; i--) {
            rightMax[i] = Math.max(bars[i], rightMax[i + 1]);
        }

        System.out.println(Arrays.toString(bars));
        System.out.println(Arrays.toString(leftMax));
        System.out.println(Arrays.toString(rightMax));
        // 在遍历数组，根据左右最大值 计算当前位置的水位高度
        int count = 0;
        for (int i = 0; i < length; i++) {
            int height = Math.min(leftMax[i], rightMax[i]) - bars[i];
            count += height;
        }
        
        return count;
    }

    public static void main(String[] args) {
        System.out.println(trappingCount(new int[] {0,1,0,2,1,0,1,3,2,1,2,1}));  //6
    }

}
