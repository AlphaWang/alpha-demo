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
//        linearSearch(new int[]{5, 7, 7, 8, 8, 10}, 8, new int[]{3, 4});
//        linearSearch(new int[]{5, 7, 7, 8, 8, 10}, 6, new int[]{-1, -1});

        binarySearch(new int[]{5, 7, 7, 8, 8, 10}, 8, new int[]{3, 4});
        binarySearch(new int[]{5, 7, 7, 8, 8, 10}, 6, new int[]{-1, -1});
    }

    /**
     * O(N)
     */
    public static int[] linearSearch(int[] nums, int target, int[] expect) {
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

        int[] result = new int[]{start, end};
        
        System.out.println(String.format("Linear Search: %s in %s : [%s, %s]. ", target, Arrays.toString(nums), start, end));
        System.out.println(Arrays.equals(result, expect) ? "PASS" : "FAILED");
        
        return result;
    }

    /**
     * O(logN)
     */
    public static int[] binarySearch(int[] nums, int target, int[] expect) {
        int[] result = new int[2];
        result[0] = findFirst(nums, target);
        result[1] = findLast(nums, target);

        System.out.print(String.format("Binary Search: %s in %s : %s. ", target, Arrays.toString(nums), Arrays.toString(result)));
        System.out.println(Arrays.equals(result, expect) ? "PASS" : "FAILED");
        
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
            
            // 相等 --> 往左找
            if (nums[mid] == target) {
                end = mid - 1;
                idx = mid;
            } 
            // 在左侧 
            else if (nums[mid] > target) {
                end = mid - 1; 
            }
            // 在右侧
            else {
                start = mid + 1;
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

            // 相等 --> 往右找
            if (nums[mid] == target) {
                start = mid + 1;
                idx = mid;
            } 
            // 在右边
            else if (nums[mid] < target) {
                start = mid + 1;
            }
            // 在左边
            else {
                end = mid - 1;
            }
            System.out.println(String.format("start=%s, mid=%s, end=%s. idx=%s", start, mid, end, idx));
        }

        System.out.println("findLast: " + idx);
        return idx;
    }


}
