package com.alphawang.algorithm.sort.n2;

import java.util.Arrays;

public class BubbleSort {

    /**
     * O(N^2)
     *
     * 自上而下对相邻的两个数依次进行比较和调整，让较大的数往下沉，较小的往上冒。
     * 即：比较相邻的元素，如果顺序不对，则 "互换"。
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

                    swapcount++;
                }
            }
        }

        System.out.println("swap count = " + swapcount);

        return data;
    }

    public static void main(String[] args) {
        System.out.println("Bubble Sort...");
        print(sort(new int[] { 4, 5, 6, 1, 2, 3 }));
        print(sort(new int[] { 1, 2, 3, 4, 5, 6 }));
        print(sort(new int[] { 6, 5, 4, 3, 2, 1 }));
    }

    private static void print(int[] data) {
        System.out.println(Arrays.toString(data));
    }
}
