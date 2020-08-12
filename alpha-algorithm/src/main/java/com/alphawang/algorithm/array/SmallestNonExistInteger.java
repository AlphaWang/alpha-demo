package com.alphawang.algorithm.array;

import java.util.Arrays;

/**
 * 给定一个整数数组，找出未出现在其中的最小正整数。
 */
public class SmallestNonExistInteger {
    
    public int findSmallest(int[] array) {
        if (array == null || array.length == 0) {
            return 1;
        }
        
        int max = 0;
        for (int i : array) {
            max = Math.max(i, max);
        }
        
        int[] count = new int[max + 1];
        for (int i : array) {
            count[i]++;
        }
        
        for (int i = 1; i < count.length; i++) {
            if (count[i] == 0) {
                return i;
            }
        }
        
        return 1;
    }

    public static void main(String[] args) {
        test(null); // 1
        test(new int[] {0}); //1
        test(new int[] {0, 1, 2, 4}); //3
        test(new int[] {0, 2, 2, 4}); //3
        test(new int[] {0, 4, 3, 2}); //1
        test(new int[] {4, 3, 2}); //1
        
    }

    public static void test(int[] array) {
        SmallestNonExistInteger sut = new SmallestNonExistInteger();
        System.out.println(String.format("%s -> %s", Arrays.toString(array), sut.findSmallest(array)));
    }

}
