package com.alphawang.algorithm.array;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/sort-colors/
 * Medium
 * 
 * 
 */
public class T0075_SortColors {

    /**
     * 1. 记录左右边界，一次遍历
     * 0ms - 100%
     */
    public void sortColors(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }
        int left = 0; // 左侧 0 的右边界
        int right = nums.length - 1; // 右侧2的左边界
        int i = 0;
        while (i <= right) {
            if (nums[i] == 0) {
                // 如果当前值为0，插到左侧；注意left边界要移动，i也要移动！
                swap(nums, left++, i);
                i++;
            } else if (nums[i] == 2) {
                // 如果当前值为2，插到右侧；right边界左移
                swap(nums, right--, i);
            } else {
                i++;
            }
        }

    }
    
    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static void main(String[] args) {
        /*
         * 输入: [2,0,2,1,1,0]
         * 输出: [0,0,1,1,2,2] 
         */
        test(new int[] {2,0,2,1,1,0});
    }

    private static void test(int[] nums) {
        T0075_SortColors sut = new T0075_SortColors();
        System.out.println(Arrays.toString(nums) + " -->");
        sut.sortColors(nums);
        System.out.println(Arrays.toString(nums));
    }
}
