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


        result = search2(new int[] {4,5,6,7,0,1,2}, 0);
        System.out.println(result);
        result = search2(new int[] {4,5,6,7,0,1,2}, 3);
        System.out.println(result);

        System.out.println("----");
        result = search3(new int[] {4,5,6,7,0,1,2}, 0);
        System.out.println(result);
        result = search3(new int[] {4,5,6,7,0,1,2}, 3);
        System.out.println(result);

        System.out.println("----");
        result = search4(new int[] {4,5,6,7,0,1,2}, 0);
        System.out.println(result);
        result = search4(new int[] {2,3,4,5,6,7,8,0,1}, 3);
        System.out.println(result);
    }

    /**
     * 1. 分割数组 + 二分搜索
     */
    private static int search(int[] nums, int target) {
        int smallest = findSmallestIndex(nums);
        
        // 如果小于第一个，则搜索 [smallest, n] 
        if (nums[0] > target) {
            int index = BinarySearch.binarySearch(nums, smallest, nums.length - 1, target);
            return index;
        }
        // 如果大于第一个，则搜索 [0, smallest-1] 
        if (nums[0] < target) {
            int index = BinarySearch.binarySearch(nums, 0, smallest - 1, target);
            return index;
        }
        // 如果等于第一个，bingo
        if (nums[0] == target) {
            return 0;
        }
        
        return -1;
    }

    /**
     * 2. real mid [HARD]
     */
    private static int search2(int[] nums, int target) {
        int smallest = findSmallestIndex(nums);
        int low = 0;
        int high = nums.length - 1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int realMid = (mid + smallest) % nums.length; // Hard to understand.
            
            if (nums[realMid] == target) {
                return realMid;
            }
            if (nums[realMid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        
        return -1;
    }

    /**
     * 3. 
     */
    private static int search3(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            
            // right part 
            if (nums[low] <= nums[mid]) {
                // target [low, mid] --> 查左侧
                if (target >= nums[low] && target <= nums[mid]) {
                    high = mid - 1;
                }
                // target [mid, high] --> 查右侧
                else {
                    low = mid + 1;
                }
            }
            //left part 
            else {
                // target [mid, high] --> 查右侧
                if (target >= nums[mid] && target <= nums[high]) {
                    low = mid + 1;
                }
                // target [low, mid] --> 有左侧
                else {
                    high = mid - 1;
                }
            }
        }
        
        return nums[low] == target ? low : -1;
    }

    private static int search4(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] == target) {
                return mid;
            }

//            // right part 
//            if (nums[low] <= nums[mid]) {
//                // target [low, mid] --> 查左侧
//                if (target >= nums[low] && target <= nums[mid]) {
//                    high = mid - 1;
//                }
//                // target [mid, high] --> 查右侧
//                else {
//                    low = mid + 1;
//                }
//            }
//            //left part 
//            else {
                // target [low, mid] --> 查左侧
                if (target >= nums[low] && target <= nums[mid]) {
                    high = mid - 1;
                }
                // target [mid, high] --> 查右侧
                else {
                    low = mid + 1;
                }
//            }
        }

        return nums[low] == target ? low : -1;
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

        System.out.println(String.format("smallest item in %s: nums[%s]=%s ", Arrays.toString(nums), high, nums[high]));
        return high;
    }

}
