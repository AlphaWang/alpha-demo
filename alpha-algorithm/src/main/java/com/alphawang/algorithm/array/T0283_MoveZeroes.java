package com.alphawang.algorithm.array;

import java.util.Arrays;

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
        T0283_MoveZeroes sut = new T0283_MoveZeroes();
        
        int[] nums = new int[] {0,1,0,3,12};
        sut.moveZeroes2(nums);
        System.out.println(Arrays.toString(nums)); // [1, 3, 12, 0, 0]

        nums = new int[] {0};
        sut.moveZeroes2(nums);
        System.out.println(Arrays.toString(nums)); // [0]

        nums = new int[] {1, 2, 0, 0 , 3};
        sut.moveZeroes2(nums);
        System.out.println(Arrays.toString(nums)); // [1,2,3,0,0]
        
    }

}
