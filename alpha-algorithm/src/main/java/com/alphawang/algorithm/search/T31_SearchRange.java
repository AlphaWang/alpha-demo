package com.alphawang.algorithm.search;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/
 *
 * Given an array of integers nums sorted in ascending order, find the starting and ending position of a given target
 * value.
 *
 * Your algorithm's runtime complexity must be in the order of O(log n).
 *
 * If the target is not found in the array, return [-1, -1].
 *
 * Example 1:
 *
 * Input: nums = [5,7,7,8,8,10], target = 8 Output: [3,4] Example 2:
 *
 * Input: nums = [5,7,7,8,8,10], target = 6 Output: [-1,-1]
 */
public class T31_SearchRange {

    public static void main(String[] args) {
        int[] result = linearSearch(new int[]{5, 7, 7, 8, 8, 10}, 8);
        System.out.println(Arrays.toString(result)); // [3, 4]

        result = linearSearch(new int[]{5, 7, 7, 8, 8, 10}, 6);
        System.out.println(Arrays.toString(result)); // [-1, -1]

        result = binarySearch(new int[]{5, 7, 7, 8, 8, 10}, 8);
        System.out.println(Arrays.toString(result)); // [3, 4]

    }

    /**
     * O(N)
     */
    public static int[] linearSearch(int[] nums, int target) {
        int start = -1;
        int end = -1;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target && start == -1) {
                start = i;
            }
            if (nums[i] > target) {
                if (start > 0) {
                    end = (i - 1);
                    break;
                }
            }
        }

        return new int[]{start, end};
    }

    /**
     * O(logN)
     */
    public static int[] binarySearch(int[] nums, int target) {
        int[] result = new int[2];
        result[0] = findFirst(nums, target);
        result[1] = findLast(nums, target);
        return result;
    }

    private static int findFirst(int[] nums, int target) {
        System.out.println("findFirst for " + Arrays.toString(nums));
        int idx = -1;
        int start = 0;
        int end = nums.length - 1;
        
        int i = 0;
        while (start <= end) {
            int mid = (start + end) / 2;
            
            System.out.println("loop-" + (i++));
            System.out.println(String.format("start=%s, mid=%s, end=%s. idx=%s", start, mid, end, idx));
            
            if (nums[mid] >= target) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
            if (nums[mid] == target) {
                idx = mid;
            }
            System.out.println(String.format("start=%s, mid=%s, end=%s. idx=%s", start, mid, end, idx));
        }

        System.out.println("findFirst: " + idx);
        return idx;
    }

    private static int findLast(int[] nums, int target) {
        System.out.println("findLast for " + Arrays.toString(nums));
        int idx = -1;
        int start = 0;
        int end = nums.length - 1;

        int i = 0;
        while (start <= end) {
            int mid = (start + end) / 2;
            
            System.out.println("loop-" + (i++));
            System.out.println(String.format("start=%s, mid=%s, end=%s. idx=%s", start, mid, end, idx));
            
            if (nums[mid] <= target) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
            if (nums[mid] == target) {
                idx = mid;
            }
            System.out.println(String.format("start=%s, mid=%s, end=%s. idx=%s", start, mid, end, idx));
        }

        System.out.println("findLast: " + idx);
        return idx;
    }


}
