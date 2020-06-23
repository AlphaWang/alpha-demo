package com.alphawang.algorithm.recursion;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *  https://leetcode.com/problems/majority-element/
 *  Easy
 *  
 *  Given an array of size n, find the majority element. 
 *  The majority element is the element that appears more than ⌊ n/2 ⌋ times.
 *
 * You may assume that the array is non-empty and the majority element 
 * always exist in the array.
 */
public class T0169_MajorityElement {

    /**
     * 1. 排序，取中间元素
     *    1ms - 100%
     */
    public int majorityElement(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }

    /**
     * 2. Map计数
     *    8ms - 48%
     */
    public int majorityElement2(int[] nums) {
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int num : nums) {
            Integer count = countMap.getOrDefault(num, 0);
            countMap.put(num, count + 1);
        }
        
        int majority = nums[0];
        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() > countMap.get(majority)) {
                majority = entry.getKey();
            }
        }
        
        return majority;
    }

    /**
     * 3. 分治
     *    1ms - 100%
     */
    public int majorityElement3(int[] nums) {
        return majorityInRange(nums, 0, nums.length - 1);
    }
    
    private int majorityInRange(int[] nums, int low, int high) {
        if (low == high) {
            return nums[low];
        }
        
        int mid = low + (high - low) / 2;
        int leftMajority = majorityInRange(nums, low, mid);
        int rightMajority = majorityInRange(nums, mid + 1, high);
        
        if (leftMajority == rightMajority) {
            return leftMajority;
        }
        
        int leftCount = countInRange(nums, leftMajority, low, mid);
        int rightCount = countInRange(nums, rightMajority, mid + 1, high);
        
        return leftCount > rightCount ? leftMajority : rightMajority;
    }

    private int countInRange(int[] nums, int num, int lo, int hi) {
        int count = 0;
        for (int i = lo; i <= hi; i++) {
            if (nums[i] == num) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        T0169_MajorityElement sut = new T0169_MajorityElement();
        
        /*
        Input: [3,2,3]
        Output: 3
         */
        System.out.println(sut.majorityElement3(new int[]{3, 2, 3}));

        /*
         Input: [2,2,1,1,1,2,2]
         Output: 2
         */
        System.out.println(sut.majorityElement3(new int[]{2,2,1,1,1,2,2}));
    }

}
