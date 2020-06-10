package com.alphawang.algorithm.hash;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/two-sum/
 * Easy
 */
public class T0001_TwoSum {
    
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> expected = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (expected.containsKey(nums[i])) {
                return new int[] {i, expected.get(nums[i])};
            } else {
                expected.put(target - nums[i], i);
            }
        }
        
        return null;
    }

    public static void main(String[] args) {
        T0001_TwoSum sut = new T0001_TwoSum();
        
        int[] nums = new int[] {2, 7, 11, 15};
        System.out.println(Arrays.toString(sut.twoSum(nums, 9))); //[0, 1]
    }

}
