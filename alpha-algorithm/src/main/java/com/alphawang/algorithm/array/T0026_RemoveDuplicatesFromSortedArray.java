package com.alphawang.algorithm.array;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/remove-duplicates-from-sorted-array/ 
 * Easy
 * 
 * 给定一个排序数组，你需要在 原地 删除重复出现的元素，使得每个元素只出现一次，
 * 返回移除后数组的新长度。
 */
public class T0026_RemoveDuplicatesFromSortedArray {
    
    //TODO wrong
    public int removeDuplicates0(int[] nums) {
        if (nums == null || nums.length < 1) {
            return 0;
        }
        
        int index = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                while (i < nums.length && nums[i] == nums[i - 1]) {
                    i++;
                }
                if (i < nums.length)
                    nums[index++] = nums[i];
            } else {
                index++;
            }
        }

        return index;
    }

    /**
     * 一次循环，双指针，遇到不相等的则复制
     *   - index : 去重后的 tail
     *   - i : 当前处理的位置
     */
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length < 1) {
            return 0;
        }

        int index = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[index]) {
                nums[++index] = nums[i];
            }
        }

        return ++index;  //faster than `index + 1` ?
    }
    
    public static void main(String[] args) {

        test(new int[] {1,2});
        test(new int[] {1,2,3});
        test(new int[] {1,1,2});
        test(new int[] {1,1,2, 2});
        test(new int[] {1,1,2, 3});
        test(new int[] {0,0,1,1,1,2,2,3,3,4});
    }
    
    private static void test(int[] nums) {
        T0026_RemoveDuplicatesFromSortedArray sut = new T0026_RemoveDuplicatesFromSortedArray();
        System.out.println(Arrays.toString(nums));
        System.out.println(String.format("%s --> %s", sut.removeDuplicates(nums), Arrays.toString(nums)));
    }
    

}
