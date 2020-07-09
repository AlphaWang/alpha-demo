package com.alphawang.algorithm.number;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.com/problems/4sum/
 */
public class T0018_FourSum {

    /**
     * 1. 排序，比 三数之和 多一层循环
     * 42ms - 15%
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();

        Arrays.sort(nums);
        System.out.println("init. " + Arrays.toString(nums));
        int length = nums.length;
        for (int i = 0; i < length - 3; i++) {
            int v0 = nums[i];
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            for (int j = i + 1; j < length - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                int v1 = nums[j];
                int left = j + 1;
                int right = length - 1;

                while (left < right) {
                    int v2 = nums[left];
                    int v3 = nums[right];
                    int sum = v0 + v1 + v2 + v3;

                    System.out.println(Arrays.asList(i, j, left, right));
                    System.out.println(Arrays.asList(v0, v1, v2, v3));
                    if (sum == target) {
                        res.add(Arrays.asList(v0, v1, v2, v3));
                        left ++;
                        while (left < right && nums[left] == nums[left - 1]) left++;
                        right --;
                        while (left < right && nums[right] == nums[right + 1]) right--;
                    } else if (sum < target) {
                        left++;
                        while (left < right && nums[left] == nums[left - 1]) left++;
                    } else if (sum > target) {
                        right--;
                        while (left < right && nums[right] == nums[right + 1]) right--;
                    }
                }
            }
        }

        return res;
    }

    public static void main(String[] args) {
        T0018_FourSum sut = new T0018_FourSum();

        /*
         * 给定数组 nums = [1, 0, -1, 0, -2, 2]，和 target = 0。
         *
         * 满足要求的四元组集合为：
         * [
         *   [-1,  0, 0, 1],
         *   [-2, -1, 1, 2],
         *   [-2,  0, 0, 2]
         * ]
         *
         */
        System.out.println(sut.fourSum(new int[] {1, 0, -1, 0, -2, 2}, 0));
        /*
         * [-3,-2,-1,0,0,1,2,3]
         * 0
         */
        System.out.println(sut.fourSum(new int[] {-3,-2,-1,0,0,1,2,3}, 0));
        /*
         * [0,0,0,0]
         * 0
         */
        System.out.println(sut.fourSum(new int[] {0,0,0,0}, 0));
        /*
         * [-1,2,2,-5,0,-1,4]
         * 3
         */
        System.out.println(sut.fourSum(new int[] {-1,2,2,-5,0,-1,4}, 3));
    }

}
