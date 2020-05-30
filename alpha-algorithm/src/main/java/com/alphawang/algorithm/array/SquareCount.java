package com.alphawang.algorithm.array;

import java.util.HashSet;

/**
 * 给你一个有序整数数组，数组中的数可以是正数、负数、零，
 * 请实现一个函数，这个函数返回一个整数：返回这个数组所有数的平方值中有多少种不同的取值。
 * 
 * 举例：
 *
 * nums = {-1,1,1,1},
 *   那么你应该返回的是：1。因为这个数组所有数的平方取值都是1，只有一种取值
 * nums = {-1,0,1,2,3}
 *   你应该返回4，因为nums数组所有元素的平方值一共4种取值：1,0,4,9
 */
public class SquareCount {

    /**
     * 思路1：暴力法
     * 计算绝对值相同的个数
     * 
     * 问题：输入是有序数组，未用到此条件
     */
    public static int squareCount1(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(Math.abs(num));
        }
        
        return set.size();
    }

    /**
     * 思路2：有序数组一般可用 "双指针"
     */
    public static int squareCount2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int count = 0;
        int start = 0; 
        int end = nums.length - 1;
        while (start <= end) {
            int startNum = Math.abs(nums[start]);
            int endNum = Math.abs(nums[end]);
            
            // 相等：移动start/end
            if (startNum == endNum) {
                count++;
                
                while (start <= end && Math.abs(nums[start]) == startNum) {
                    start++;
                }
                while (start <=end && Math.abs(nums[end]) == endNum) {
                    end--;
                }
            } 
            // 左边小: 移动end
            else if (start < end) {
                count++;
                
                while (start <=end && Math.abs(nums[end]) == endNum) {
                    end--;
                }
            } 
            // 右边小：移动start
            else {
                count++;

                while (start <= end && Math.abs(nums[start]) == startNum) {
                    start++;
                }
            }
        }
        
        return count;
    }


    public static void main(String[] args) {
        System.out.println(squareCount1(new int[] {-1,1,1,1}));
        System.out.println(squareCount1(new int[] {-1,0,1,2,3}));

        System.out.println(squareCount2(new int[] {-1,1,1,1}));
        System.out.println(squareCount2(new int[] {-1,0,1,2,3}));
    }

}
