package com.alphawang.algorithm.sort.nlogn;

import java.util.Arrays;

public class QuickSort {

    /**
     * 找个基准元素，比它小的放左边，比它大的放右边
     * 再递归排序左右两部分
     * 
     * 递归公式：
     * quickSort(start, end) = quickSort(start, mid) + quickSort(mid+1, end)
     * 
     * 终止条件：
     * start >= end
     * 
     */
    private static int[] quickSort(int[] array) {
        System.out.println("---- origin: " + Arrays.toString(array));
        quickSort(array, 0, array.length - 1);
        return array;
    }

    private static void quickSort(int[] array, int start, int end) {
        if (start >= end) {
            return;
        }
        
        int pivotIndex = partition(array, start, end);
        System.out.println(String.format("---- pivot=%s, sorted=%s", pivotIndex, Arrays.toString(array)));
        
        // 注意 pivotIndex 本身应该排除掉
        quickSort(array, start, pivotIndex - 1);
        quickSort(array, pivotIndex + 1, end);
    }

    /**
     * 分区
     * 
     * 原地分区：不占用额外空间，思路巧妙！
     * 如果不考虑原地，则可创建两个临时数组 再拷贝回原数组
     */
    private static int partition(int[] array, int start, int end) {
        // 选取 end 作为 pivot
        int pivot = array[end];
        // 双指针顺序遍历，将<pivot的元素换到前面；i 元素为中间点
        // 注意不要遍历到 end
        int i = start;
        for (int j = start; j <= end - 1; j++) {
            if (array[j] < pivot) {
                swap(array, i, j);
                i++;
            }
        }
        // 将pivot移到中间点
        swap(array, i, end);
        
        return i;
    }
    
    private static void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    public static void main(String[] args) {
        print(quickSort(new int[] { 4, 6, 5, 2, 1, 3 }));
    }

    private static void print(int[] data) {
        System.out.println(Arrays.toString(data));
    }

}
