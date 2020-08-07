package com.alphawang.algorithm.array;

import java.util.Arrays;

public class T0042_TrappingRainWater_2Pointers {
    /**
     * https://leetcode.com/problems/trapping-rain-water/
     * 
     * Given n non-negative integers representing an elevation map where the width of each bar is 1, 
     * compute how much water it is able to trap after raining.
     * 
     */

    /**
     * 思路：左右指针依次往中间遍历
     */
    public static int trappingCount(int[] bars) {
        int length = bars.length;
        if (length < 3) {
            return 0;
        }
        
        int left = 0;
        int right = length - 1;
        
        // 去除两侧
        while (left < right && bars[left] <= bars[left + 1]) {
            left++;
        }
        while (left < right && bars[right - 1] >= bars[right]) {
            right--;
        }

        System.out.println(Arrays.toString(bars));
        System.out.println(String.format("---- left = %s, right = %s", left, right));
        
        // TODO 计算
        int count = 0;
        while (left < right) {
            int leftHeight = bars[left];
            int rightHeight = bars[right];
            // 左侧更小
            if (leftHeight <= rightHeight) {
                // 如果left往右走更低，计算面积
                while (left < right && leftHeight >= bars[++left]) {
                    count += leftHeight - bars[left];
                }
            } 
            // 右侧更小
            else {
                // 如果 right 往左走更低，计算面积
                while (left < right && rightHeight >= bars[--right]) {
                    count += rightHeight - bars[right];
                }
            }
        }
        
        return count;
    }

    public static void main(String[] args) {
        System.out.println(trappingCount(new int[] {0,1,0,2,1,0,1,3,2,1,2,1}));  //6
    }

}
