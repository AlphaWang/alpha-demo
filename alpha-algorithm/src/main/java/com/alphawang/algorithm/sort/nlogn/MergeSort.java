package com.alphawang.algorithm.sort.nlogn;

import java.util.Arrays;

public class MergeSort {

    /**
     * 将两个有序表合并成一个新的有序表。即：
     * - 把待排序序列分为若干个子序列，确保每个子序列是有序的。
     * - 然后再把有序子序列合并为整体有序序列。
     * 
     *  递归公式：
     *  merge_sort(start, end) = merge(merge_sort(start, mid), merge_sort(mid+1, end))
     *  
     *  终止条件：
     *  p >= r
     */
    private static int[] mergeSort(int[] array) {
        return mergeSort(array, 0, array.length - 1);
    }
    
    private static int[] mergeSort(int[] array, int start, int end) {
        /**
         * 终止条件
         */
        if (start >= end) {
            int[] ret = new int[] {array[start]};
            System.out.println(String.format("------ 2. sort [%s, %s] => %s", start, end, Arrays.toString(ret)));
            return ret;
        }
        
        int mid = start + (end - start) / 2;
        System.out.println(String.format("---- 1. split to [%s, %s], [%s, %s]", start, mid, mid + 1, end));

        /**
         * Merge
         */
        int[] left = mergeSort(array, start, mid);
        int[] right = mergeSort(array, mid + 1, end);
        return merge(left, right);
    }
    
    private static int[] merge(int[] arr1, int[] arr2) {
        int len1 = arr1.length;
        int len2 = arr2.length;
        //TODO 如何消除额外空间？
        int[] tmp = new int[len1 + len2];
        
        int i = 0, j = 0, k = 0;
        while (i < len1 && j < len2) {
            if (arr1[i] <= arr2[j]) {
                tmp[k++] = arr1[i++];
            } else {
                tmp[k++] = arr2[j++];
            }
        }
        
        while (i < len1) {
            tmp[k++] = arr1[i++];
        }
        while (j < len2) {
            tmp[k++] = arr2[j++];
        }

        System.out.println(String.format("-- 3. merge %s + %s ==> %s",
                                         Arrays.toString(arr1),
                                         Arrays.toString(arr2),
                                         Arrays.toString(tmp)
                                         ));
        
        return tmp;
    }
    
    public static void main(String[] args) {
        print(mergeSort(new int[] { 4, 5, 6, 1, 2, 3 }));
    }

    private static void print(int[] data) {
        System.out.println(Arrays.toString(data));
    }

}
