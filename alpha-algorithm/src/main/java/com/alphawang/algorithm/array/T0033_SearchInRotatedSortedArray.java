package com.alphawang.algorithm.array;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/search-in-rotated-sorted-array/
 * Medium
 */
public class T0033_SearchInRotatedSortedArray {

    /**
     * 1: 二分法，mid值 与 target 对比，分两段
     */
    public int search(int[] nums, int target) {
        int start = 0, end = nums.length - 1;
        while (start < end) {
            if (start + 1 == end) {
                return nums[start] == target ? start :
                  nums[end] == target ? end : -1;
            }
            int mid = start + (end - start) / 2;
            System.out.println(String.format("%s - [%s, %s] : %s",
                  Arrays.toString(nums), start, end, mid));
            if (nums[mid] > target) {
                if (nums[start] > target && nums[end] >= target) {
                    start = mid;
                } else {
                    end = mid;
                }
            } else if (nums[mid] < target) {
                if (nums[start] >= target && nums[end] > target) {
                    end = mid; 
                } else {
                    start = mid;
                }
            } else {
                return mid;
            }
        }
        
        return -1;
    }

    /**
     * 2: 二分法，找到有序区间
     */
    public int search2(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;
        
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            if (nums[left] == target) return left;
            if (nums[right] == target) return right;
            
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) return mid;
            
            // 1. 左侧有序
            if (nums[left] < nums[mid]) { 
                // 如果目标值在左侧，则搜索左侧有序数组
                if (nums[left] < target && target < nums[mid]) {
                    right = mid - 1;
                    left = left + 1; //!!!优化速度
                } else {
                    left = mid + 1;
                    right = right - 1; //!!!优化速度
                }
            }
            // 2. 右侧有序
            else {
                // 如果目标值在右侧，则搜索右侧有序数组
                if (nums[mid] < target && target < nums[right]) {
                    left = mid + 1;
                    right = right - 1; //!!!优化速度
                } else {
                    right = mid - 1;
                    left = left +1;  //!!!优化速度
                }
                
            }
        }
        
        return -1;
    }

    /**
     * 3: 二分法，判断有序区间
     */
    public int search3(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;
        
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            System.out.println(String.format("%s - [%s, %s] : %s",
                Arrays.toString(nums), left, right, mid));
            
            if (nums[mid] == target) return mid;
            
            // 左侧有序
            if (nums[left] <= nums[mid]) {
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } 
            // 右侧有序
            else {
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        
        return -1;
    }
    

    public static void main(String[] args) {
        /*
         * Input: nums = [4,5,6,7,0,1,2], target = 0
         * Output: 4
         */
        test(new int[] {4,5,6,7,0,1,2}, 0);

        /*
         * Input: nums = [4,5,6,7,0,1,2], target = 3
         * Output: -1
         */
        test(new int[] {4,5,6,7,0,1,2}, 3);

        /*
         * Input: nums = [3,1], target = 1
         * Output: 1
         */
        test(new int[] {3,1}, 1);

        /*
         * Input: nums = [5,1,3], target = 3
         * Output: 2
         */
        test(new int[] {5,1,3}, 3);

        /*
         * Input: nums = [5,1,3], target = 5
         * Output: 0
         * TODO 返回-1？
         */
        test(new int[] {5,1,3}, 5);
    }
    
    private static void test(int[] nums, int target) {
        T0033_SearchInRotatedSortedArray sut = new T0033_SearchInRotatedSortedArray();
        int res = sut.search3(nums, target);

        System.out.println(String.format("search %s in %s --> %s", target, Arrays.toString(nums), res));
    }

}
