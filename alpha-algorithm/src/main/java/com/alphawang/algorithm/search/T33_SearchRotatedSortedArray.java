package com.alphawang.algorithm.search;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/search-in-rotated-sorted-array/
 * 
 * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
 * (i.e., [0,1,2,4,5,6,7] might become [4,5,6,7,0,1,2]).
 *
 * You are given a target value to search. 
 * If found in the array return its index, otherwise return -1.
 *
 * You may assume no duplicate exists in the array.
 * Your algorithm's runtime complexity must be in the order of O(log n).
 *
 * Example 1:
 * Input: nums = [4,5,6,7,0,1,2], target = 0
 * Output: 4
 * 
 * Example 2:
 * Input: nums = [4,5,6,7,0,1,2], target = 3
 * Output: -1
 */
public class T33_SearchRotatedSortedArray {

    public static void main(String[] args) {
        int result = search(new int[] {4,5,6,7,0,1,2}, 0);
        System.out.println(result);

        result = search(new int[] {4,5,6,7,0,1,2}, 3);
        System.out.println(result);
    }

    private static int search(int[] nums, int target) {
        int smallest = findSmallestIndex(nums);
        int smallestValue = nums[smallest];
        
        System.out.println(String.format("smallest item in %s: nums[%s]=%s ", Arrays.toString(nums), smallest, smallestValue));
        // reset 
        if (nums[0] > target) {
            int index = BinarySearch.binarySearch(nums, smallest, nums.length - 1, target);
            return index;
        }
        if (nums[0] < target) {
            int index = BinarySearch.binarySearch(nums, 0, smallest - 1, target);
            return index;
        }
        if (nums[0] == target) {
            return 0;
        }
        
        return -1;
    }
    
    private static int findSmallestIndex(int[] nums) {
        int low = 0;
        int high = nums.length - 1;

        // find smallest item
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] > nums[high]) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        
        return high;
    }

}
