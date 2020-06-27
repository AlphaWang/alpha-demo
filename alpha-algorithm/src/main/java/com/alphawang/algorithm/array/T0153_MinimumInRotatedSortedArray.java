package com.alphawang.algorithm.array;

/**
 * https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/
 */
public class T0153_MinimumInRotatedSortedArray {

    /**
     * 1. 遍历，找波谷
     *    0ms - 100%
     */
    public int findMin(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i - 1]) {
                return nums[i];
            }
        }
        
        return nums[0];
    }

    /**
     * 2. 二分法
     *    0ms - 100%
     */
    public int findMin2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        if (nums.length == 1) {
            return nums[0];
        }

        return findMinBinary(nums, 0, nums.length - 1);
    }
    
    private int findMinBinary(int[] nums, int left, int right) {
        if (left >= right) {
            return nums[left];
        }
        if (right - left == 1) {
            return nums[left] > nums[right] ? nums[right] : nums[left];
        }
        int mid = left + (right - left) / 2;
        System.out.println(String.format("[%s, %s] - %s", left, right, mid));
        
        int midValue = nums[mid];
        int leftValue = nums[left];
        int rightValue = nums[right];
        // 第一段
        if (midValue > leftValue && midValue > rightValue) {
            return findMinBinary(nums, mid, right);
        } 
        // 只有第一段
        if (midValue >= leftValue && midValue < rightValue) {
            return findMinBinary(nums, left, mid);
        }
        // 第二段
        if (midValue < leftValue && midValue < rightValue) {
            return findMinBinary(nums, left, mid);
        }
//        if (midValue <= leftValue) {
            return findMinBinary(nums, left, mid);
//        }
    }
    
    public static void main(String[] args) {
        T0153_MinimumInRotatedSortedArray sut = new T0153_MinimumInRotatedSortedArray();

        /*
         * Input: [3,4,5,1,2] 
         * Output: 1
         */
        System.out.println(sut.findMin2(new int[] {3,4,5,1,2}));

        /*
         * Input: [4,5,6,7,0,1,2]
         * Output: 0
         */
        System.out.println(sut.findMin2(new int[] {4,5,6,7,0,1,2}));

        /*
         * Input: [2,1]
         * Output: 1
         */
        System.out.println(sut.findMin2(new int[] {2,1}));
    }

}
