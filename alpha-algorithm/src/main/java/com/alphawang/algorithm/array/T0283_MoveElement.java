package com.alphawang.algorithm.array;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/move-zeroes/
 * Easy
 * 
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 *
 * 升级：将所有 k 移动到数组的开头，同时保持其他元素的相对顺序
 */
public class T0283_MoveElement {

    public int[] moveElement(int[] nums, int k) {
        int index = nums.length - 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] != k) {
                nums[index--] = nums[i];
            }
        }
        
        for (int i = 0; i <= index; i++) {
            nums[i] = k;
        }
        
        return nums;
    }
    
    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }


    public static void main(String[] args) {

        test(new int[] {0,1,0,3,12}, 0 );
        test(new int[] {0}, 0);
        test(new int[] {1, 2, 0, 0 , 3}, 0);
        test(new int[] {0, 1, 2, 3, 4, 5}, 3);
        
    }
    
    private static void test(int[] nums, int k) {
        T0283_MoveElement sut = new T0283_MoveElement();
        System.out.println(Arrays.toString(nums));
        System.out.println(String.format("%s : %s --> %s", Arrays.toString(nums), k, Arrays.toString(sut.moveElement(nums, k))));
    }

}
