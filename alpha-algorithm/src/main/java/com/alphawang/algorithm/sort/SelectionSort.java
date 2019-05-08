package com.alphawang.algorithm.sort;

import java.util.Arrays;

public class SelectionSort {

    /**
     * N^2
     */
    public static int[] sort(int[] data) {
        int n = data.length;

        // 重复（元素个数-1）次
        for (int i = 0; i < n; i++) {
            // 把第一个没有排序过的元素设置为最小值
            int min = i;
            // 遍历每个没有排序过的元素
            for (int j = i+1; j < n; j++) { 
               // 如果元素 < 现在的最小值，将此元素设置成为新的最小值
               if (data[j] < data[min]) {
                   min = j;
               }
            }
            
            // 将最小值和第一个没有排序过的位置交换
            if (data[min] < data[i]) {
               int temp = data[min];
               data[min] = data[i];
               data[i] = temp;
            }
            
        }
        
        return data;
    }
    
    public static void main(String[] args) {
        print(sort(new int[]{4, 5, 6, 1, 2, 3}));
        print(sort(new int[]{1, 2, 3, 4, 5, 6}));
        print(sort(new int[]{6, 5, 4, 3, 2, 1}));
    }
    
    private static void print(int[] data) {
        System.out.println(Arrays.toString(data));
    }
}
