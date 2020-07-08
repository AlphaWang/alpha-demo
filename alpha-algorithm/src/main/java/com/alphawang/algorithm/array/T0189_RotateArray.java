package com.alphawang.algorithm.array;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/rotate-array/
 * Easy
 * 
 * 给定一个数组，将数组中的元素向右移动 k 个位置，其中 k 是非负数。
 */
public class T0189_RotateArray {

    /**
     * 1: 计算需要移动的步数，for steps, 从后往前遍历数组 移动元素
     *    214 ms  O(K*N)
     */
    public void rotate(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return;
        }
        
        int steps = k % nums.length;  // in case k larger than array size
        if (steps == 0) return;
        
        for (int step = 1; step <= steps; step++) {
            int tmp  = nums[nums.length - 1];
            for (int i = nums.length - 1; i > 0; i--) {
                nums[i] = nums[i-1];
            }
            nums[0] = tmp;
        }
    }

    /**
     * 2-1. 使用额外数组，暂存末尾待移动的元素
     *    0 ms
     */
    public void rotate2_1(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return;
        }
        int steps = k % nums.length;
        if (steps == 0) return;
        
        // save tmp array
        int[] tmp = new int[steps];
        int index = 0;
        for (int i = nums.length - steps; i < nums.length; i++) {
            tmp[index++] = nums[i];
        }
        // set tail elements
        for (int i = nums.length - 1; i >= steps; i--) {
            nums[i] = nums[i - steps];
        }
        // set head elements
        for (int i = 0; i < steps; i++) {
            nums[i] = tmp[i];
        }
    }

    /**
     * 2-2. 使用额外数组，暂存头部待移动的元素
     *    0 ms
     */
    public void rotate2_2(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return;
        }
        int steps = k % nums.length;
        if (steps == 0) return;

        // save tmp array
        int[] tmp = new int[nums.length - steps];
        for (int i = 0; i < nums.length - steps; i++) {
            tmp[i] = nums[i];
        }
        // set head elements
        for (int i = 0; i < steps; i++) {
            nums[i] = nums[nums.length - steps + i];
        }
        // set tail elements
        for (int i = steps; i < nums.length; i++) {
            nums[i] = tmp[i - steps];
        }
    }

    /**
     * 3. 使用额外数组，直接计算得到目标下标
     *    2 ms
     */
    public void rotate3(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return;
        }
        int steps = k % nums.length;
        if (steps == 0) return;
        
        int[] tmp = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            tmp[(i + k) % nums.length] = nums[i];
        }
        for (int i = 0; i < nums.length; i++) {
            nums[i] = tmp[i];
        }
    }

    /**
     * 4. Reverse Array
     *    0 ms
     */
    public void rotate4(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return;
        }
        k = k % nums.length;
        if (k == 0) return;

        reverse(nums, 0, nums.length - k - 1);
        reverse(nums, nums.length - k, nums.length - 1);
        reverse(nums, 0, nums.length - 1);
    }
    
    public void reverse(int[] nums, int left, int right) {
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }
    
    private void swap(int[] nums, int i, int j) {
        int tmp = nums[j];
        nums[j] = nums[i];
        nums[i] = tmp;
    }

    public static void main(String[] args) {
        T0189_RotateArray sut = new T0189_RotateArray();
        
        int[] nums = new int[] {1,2,3,4,5,6,7};
        sut.rotate4(nums, 3);
        System.out.println(Arrays.toString(nums)); //5,6,7,1,2,3,4

        nums = new int[] {1,2,3,4,5,6,7};
        sut.rotate4(nums, 9);
        System.out.println(Arrays.toString(nums)); //6,7,1,2,3,4,5

        nums = new int[] {-1,-100,3,99};
        sut.rotate4(nums, 2);
        System.out.println(Arrays.toString(nums)); //3,99,-1,-100
    }

}
