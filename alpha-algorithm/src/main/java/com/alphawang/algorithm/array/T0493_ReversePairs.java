package com.alphawang.algorithm.array;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/reverse-pairs/
 * Hard
 * 
 * 给定一个数组 nums ，如果 i < j 且 nums[i] > 2*nums[j] 我们就将 (i, j) 称作一个重要翻转对。
 * 你需要返回给定数组中的重要翻转对的数量。
 */
public class T0493_ReversePairs {

    /**
     * 1. 暴力
     *    超时
     */
    public int reversePairs(int[] nums) {
        int res = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if ((long) nums[i] > (long) nums[j] * 2) {
                    res++;
                }
            }
        }
        
        return res;
    }

    /**
     * 2. 归并
     *    TODO
     */
    public int reversePairs2(int[] nums) {
        return mergeSort(nums, 0, nums.length - 1);
    }
    
    private int mergeSort(int[] nums, int start, int end) {
        if (start >= end) {
            return 0;
        }
        int mid = start + (end - start) / 2;
        
        int count = mergeSort(nums, 0, mid) + mergeSort(nums, mid + 1, end);
        int currCount = 0;
        
        int right = mid + 1;
        for (int left = start; left <= mid; left++) {
            while (right <= end && nums[left] / 2.0 > nums[right]) {
                right++;
                currCount++;
            }
            count += currCount;
        }
        Arrays.sort(nums, start, end + 1);
        return count;
    }

    public static void main(String[] args) {
        // 2
        test(new int[] {1,3,2,3,1});
        // 3
        test(new int[] {2,4,3,5,1});
    }
    
    private static void test(int[] nums) {
        System.out.println(Arrays.toString(nums));
        System.out.println("-->");
        System.out.println(new T0493_ReversePairs().reversePairs2(nums));
    }

}
