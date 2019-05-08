package com.alphawang.algorithm.sort;

import java.util.Arrays;

public class BubbleSort {
    
    /**
     * N^2
     */
    public static int[] sort(int[] data) {
        int swapcount = 0;
        int n = data.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (data[i] > data[j]) {
                    int tmp = data[j];
                    data[j] = data[i];
                    data[i] = tmp;

                    swapcount ++;
                }
            }
        }
        
        System.out.println(swapcount);
        
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
