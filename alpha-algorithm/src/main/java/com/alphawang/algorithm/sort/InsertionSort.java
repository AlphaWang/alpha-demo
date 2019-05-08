package com.alphawang.algorithm.sort;

import java.util.Arrays;

public class InsertionSort {

    /**
     * N^2
     */
    public static int[] sort(int[] data) {

        // 遍历每个没有排序过的元素
        for (int i = 1; i < data.length; i++) {
            // “提取” 元素 X
            int value = data[i];
            int targetIndex = -1;
            
            // 找到待插入位置
            // 在已经排序的元素序列中从后向前扫描
            for (int j = i - 1; j >= 0; j--) {
                // 如果现在排序过的元素 > 提取的元素，将排序过的元素向右移一格
                if (data[j] > value) {
                    data[j+1] = data[j]; 
                    targetIndex = j;
                } else {
                    // 否则：插入提取的元素
                    break;
                }
            }
           
            if (targetIndex >= 0) {
                data[targetIndex] = value;
            }
        }
        
        return data;
    }
    
    public static void main(String[] args) {
        System.out.println("Insertion Sort...");
        print(sort(new int[]{4, 5, 6, 1, 2, 3}));
        print(sort(new int[]{1, 2, 3, 4, 5, 6}));
        print(sort(new int[]{6, 5, 4, 3, 2, 1}));
    }
    
    private static void print(int[] data) {
        System.out.println(Arrays.toString(data));
    }
}
