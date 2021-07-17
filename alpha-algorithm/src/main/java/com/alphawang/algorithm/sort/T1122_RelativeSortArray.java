package com.alphawang.algorithm.sort;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/relative-sort-array/
 * Easy
 * 
 * 给你两个数组，arr1 和arr2，
 *
 * arr2 中的元素各不相同
 * arr2 中的每个元素都出现在arr1中
 * 
 * 对 arr1中的元素进行排序，使 arr1 中项的相对顺序和arr2中的相对顺序相同。
 * 未在arr2中出现过的元素需要按照升序放在arr1的末尾。
 *
 * Example 1:
 *   Input: arr1 = [2,3,1,3,2,4,6,7,9,2,19], arr2 = [2,1,4,3,9,6]
 *   Output: [2,2,2,1,4,3,3,9,6,7,19]
 *
 * Constraints:
 *   arr1.length, arr2.length <= 1000
 *   0 <= arr1[i], arr2[i] <= 1000
 *   Each arr2[i] is distinct.
 *   Each arr2[i] is in arr1.
 */
public class T1122_RelativeSortArray {

    /**
     * 1. 计数排序
     *    1ms - 81%
     */
    private static int[] relativeSort(int[] arr1, int[] arr2) {
        // 1. 遍历arr1：记录arr1 中每个元素的出现次数
        int[] count = new int[1001];
        for (int element1 : arr1) {
            count[element1]++;
        }

        System.out.println("----arr1: " + Arrays.toString(arr1));
        System.out.println("----arr1 count: " + Arrays.toString(count));
        
        // 2. 遍历arr2：按 arr2排序
        int index = 0;
        for (int element2 : arr2) {
            while(count[element2]-- > 0) {
                arr1[index++] = element2;
            }
        }

        System.out.println("----by arr2: " + Arrays.toString(arr1));
        
        // 3. 遍历count: 处理 arr2 中未出现的元素
        for (int i = 0; i < count.length; i++) {
            if (count[i] > 0) {
                while (count[i]-- > 0) {
                    arr1[index++] = i;
                }
            }
        }
        
        return arr1;
    }

    public static void main(String[] args) {
//        print(relativeSort(new int[]{2,3,1,3,2,4,6,7,9,2,19}, new int[]{2,1,4,3,9,6}));
        print(relativeSort(new int[]{1,1,2,3,4,5,6,7}, new int[]{2,7}));
    }
    
    private static void print(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }

}
