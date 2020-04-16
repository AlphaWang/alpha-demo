package com.alphawang.algorithm.sort.n2;

import java.util.Arrays;

public class InsertionSort {

    /**
     * N^2
     *
     * 假设前面(n-1) [n>=2] 个数已经是排好顺序的。
     * 把第n个数插到前面的有序数中，使得这n个数也是排好顺序的。
     *
     * - 找到待插入位置，查找过程中 "移动数据"
     * - 把n插入找到的位置。
     */
    public static int[] sort(int[] data) {

        // 遍历每个没有排序过的元素
        for (int i = 1; i < data.length; i++) {
            // 暂存要插入的数据
            int value = data[i];

            // 找到待插入位置
            // 在已经排序的元素序列中 "从后向前" 扫描
            int j = i;
            for (; j > 0; j--) {
                // 如果现在排序过的元素 > 待插入元素，将排序过的元素向右移一格
                if (data[j - 1] > value) {
                    data[j] = data[j - 1];
                } else {
                    // 否则：找到待插入点
                    break;
                }
            }

            // 此时 data[j] 已被挪空
            if (j != i) {
                data[j] = value;
            }

        }

        return data;
    }

    public static void main(String[] args) {
        System.out.println("Insertion Sort...");
        print(sort(new int[] { 4, 5, 6, 1, 2, 3 }));
        print(sort(new int[] { 1, 2, 3, 4, 5, 6 }));
        print(sort(new int[] { 6, 5, 4, 3, 2, 1 }));
    }

    private static void print(int[] data) {
        System.out.println(Arrays.toString(data));
    }
}
