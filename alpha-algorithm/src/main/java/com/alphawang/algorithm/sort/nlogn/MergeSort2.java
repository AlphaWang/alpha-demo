package com.alphawang.algorithm.sort.nlogn;

import java.util.Arrays;

public class MergeSort2 {

    /**
     * 将两个有序表合并成一个新的有序表。即：
     * - 把待排序序列分为两个子序列，确保每个子序列是有序的 （递归）。
     * - 然后再把有序子序列合并为整体有序序列。
     * 
     * > 可认为是快排的逆向操作。
     * 
     * 
     * 
     *  递归公式：
     *  merge_sort(start, end) = merge(merge_sort(start, mid), merge_sort(mid+1, end))
     *  
     *  终止条件：
     *  p >= r
     */
    private static int[] mergeSort(int[] array) {
        mergeSort(array, 0, array.length - 1);
        return array;
    }
    
    private static void mergeSort(int[] array, int start, int end) {
        if (start >= end) {
            return;
        }
        
        int mid = start + (end - start) / 2;

        System.out.println(String.format("mergeSort [%s, %s], mid = %s", start, end, mid));
        mergeSort(array, start, mid);
        mergeSort(array, mid + 1, end);
        
        merge(array, start, end, mid);
    }

    /**
     * 合并
     * [start, mid), [mid, end] 分别是有序数组
     */
    private static void merge(int[] array, int start, int end, int mid) {
        System.out.println(String.format("merge [%s, %s], mid = %s", start , end, mid));
        
        int[] tmp = new int[end - start + 1];
        int i = start, j = mid + 1, k = 0;
        
        while (i <= mid && j <= end) {
            tmp[k++] = array[i] < array[j] ? array[i++] : array[j++];
        }
        while (i <= mid) { // < 还是 <= ？？
            tmp[k++] = array[i++];
        }
        while (j <= end) {
            tmp[k++] = array[j++];
        }
        
        // 修改原数组
        for (int p = 0; p < tmp.length; p++) {
            array[start + p] = tmp[p];
        }
    }
    
    public static void main(String[] args) {
        print(mergeSort(new int[] { 4, 5, 6, 1, 2, 3 }));
    }

    private static void print(int[] data) {
        System.out.println(Arrays.toString(data));
    }

}
