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

    public static void main(String[] args) {
        T0153_MinimumInRotatedSortedArray sut = new T0153_MinimumInRotatedSortedArray();

        /*
         * Input: [3,4,5,1,2] 
         * Output: 1
         */
        System.out.println(sut.findMin(new int[] {3,4,5,1,2}));

        /*
         * Input: [4,5,6,7,0,1,2]
         * Output: 0
         */
        System.out.println(sut.findMin(new int[] {4,5,6,7,0,1,2}));
    }

}
