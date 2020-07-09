package com.alphawang.algorithm.array;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/move-zeroes/
 * Easy
 * 
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 *
 * 输入: [0,1,0,3,12]
 * 输出: [1,3,12,0,0]
 */
public class T0283_MoveZeroes {

    /**
     * 1. 双指针，一个遍历数组，一个记录已处理的位置
     */
    public void moveZeroes(int[] nums) {
        int nonZeroIndex = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[nonZeroIndex++] = nums[i];
            }
        }
        
        for (; nonZeroIndex < nums.length; nonZeroIndex++) {
            nums[nonZeroIndex] = 0;
        }
    }

    /**
     * 快排思想 
     * TODO
     */
    public void moveZeroes2(int[] nums) {
        for (int i = 0, j = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                if (i != j) {  // can be removed.
                    int tmp = nums[i];
                    nums[i] = nums[j];
                    nums[j++] = tmp;
                } else {
                    j++;
                }
                
            }
        }
    }

    public static void main(String[] args) {

        test(new int[] {0,1,0,3,12});
        test(new int[] {0});
        test(new int[] {1, 2, 0, 0 , 3});
        
    }
    
    private static void test(int[] nums) {
        T0283_MoveZeroes sut = new T0283_MoveZeroes();
        System.out.println(Arrays.toString(nums));
        sut.moveZeroes(nums);
        System.out.println(String.format("--> %s", Arrays.toString(nums)));
    }

}
