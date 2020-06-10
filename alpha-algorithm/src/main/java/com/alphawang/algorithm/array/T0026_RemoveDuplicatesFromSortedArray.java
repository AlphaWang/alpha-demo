package com.alphawang.algorithm.array;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/remove-duplicates-from-sorted-array/ 
 * Easy
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
        T0026_RemoveDuplicatesFromSortedArray sut = new T0026_RemoveDuplicatesFromSortedArray();

        int[] nums = new int[] {1,2};
        System.out.println(sut.removeDuplicates(nums)); // 2
        System.out.println(Arrays.toString(nums)); // [1, 2]

        nums = new int[] {1,2,3};
        System.out.println(sut.removeDuplicates(nums)); // 3
        System.out.println(Arrays.toString(nums)); // [1, 2, 3]
        
        nums = new int[] {1,1,2};
        System.out.println(sut.removeDuplicates(nums)); // 2
        System.out.println(Arrays.toString(nums)); // [1, 2, ..]

        nums = new int[] {1,1,2, 2};
        System.out.println(sut.removeDuplicates(nums)); // 2
        System.out.println(Arrays.toString(nums)); // [1, 2, ..]

        nums = new int[] {1,1,2, 3};
        System.out.println(sut.removeDuplicates(nums)); // 3
        System.out.println(Arrays.toString(nums)); // [1, 2, 3..]

        nums = new int[] {0,0,1,1,1,2,2,3,3,4};
        System.out.println(sut.removeDuplicates(nums)); // 5
        System.out.println(Arrays.toString(nums)); // [0, 1, 2, 3, 4, ..]
    }

}
