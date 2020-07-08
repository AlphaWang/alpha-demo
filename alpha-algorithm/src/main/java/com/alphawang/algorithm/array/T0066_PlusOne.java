package com.alphawang.algorithm.array;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/plus-one/
 * Easy
 * 
 * 给定一个由整数组成的非空数组所表示的非负整数，在该数的基础上加一。
 * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
 * 你可以假设除了整数 0 之外，这个整数不会以零开头。
 */
public class T0066_PlusOne {

    /**
     * 从后往前遍历 +1
     * 0 ms
     */
    public int[] plusOne(int[] digits) {
        for (int i = digits.length - 1; i >= 0; i--) {
            digits[i] = digits[i] + 1;
            if (digits[i] < 10) {
                return digits;
            }

            digits[i] = digits[i] % 10;
        }

        // 可继续优化：走到这一步，表示一定要进位，且后面的数字一定是全零--> 无需拷贝
        if (digits[0] == 0) {
            int[] res = new int[digits.length + 1];
            res[0] = 1;
            System.arraycopy(digits, 0, res, 1, digits.length);
            return res;
        }

        return digits;
    }

    /**
     * 优化空间复杂度
     */
    public int[] plusOne2(int[] digits) {
        for (int i = digits.length - 1; i >= 0; i--) {
            digits[i] = digits[i] + 1;
            if (digits[i] < 10) {
                return digits;
            }

            digits[i] = digits[i] % 10;
        }

        digits = new int[digits.length + 1];
        digits[0] = 1;

        return digits;
    }

    public static void main(String[] args) {
        T0066_PlusOne sut = new T0066_PlusOne();
        
        int[] nums = new int[] {1, 2, 3};
        System.out.println(Arrays.toString(sut.plusOne2(nums))); //[1, 2, 4]

        nums = new int[] {4,3,2,1};
        System.out.println(Arrays.toString(sut.plusOne2(nums))); //[4, 3, 2, 2]

        nums = new int[] {9, 9, 9};
        System.out.println(Arrays.toString(sut.plusOne2(nums))); //[1, 0, 0, 0]
    }

}
