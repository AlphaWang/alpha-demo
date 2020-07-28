package com.alphawang.algorithm.sort.nlogn;

import java.util.Arrays;

public class QuickSort {

    /**
     * 找个基准元素 pivot，比它小的放左边，比它大的放右边
     * 再递归排序左右两部分 （分治）
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
        
        _quickSort(array, 0, array.length - 1);
        return array;
    }

    private static void _quickSort(int[] array, int start, int end) {
        // 1. terminator
        if (start >= end) {
            return;
        }
        
        // 2. process current
        int pivotIndex = partition(array, start, end);
        
        System.out.println(String.format("---- pivot=%s, sorted=%s", pivotIndex, Arrays.toString(array)));
        
        // 3. 分治，drill down
        // 注意 pivotIndex 本身应该排除掉
        _quickSort(array, start, pivotIndex - 1);
        _quickSort(array, pivotIndex + 1, end);
    }

    /**
     * 分区
     *  - 返回 pivot 下标
     *  - 并且 比 pivot 小的放左边，比 pivot 大的放右边
     * 
     * 1. 原地分区：不占用额外空间，思路巧妙！
     * 2. 如果不考虑原地，则可创建两个临时数组，再拷贝回原数组
     */
    private static int partition(int[] array, int start, int end) {
        // 选取 end 作为 pivot
        int pivot = end;
        
        // 双指针顺序遍历，将 <pivot 的元素换到前面；i 元素为中间点
        // 注意不要遍历到 end
        int counter = start; //count == 小于 pivot 的个数 == 最后一个大于pivot的位置  
        for (int j = start; j <= end - 1; j++) {
            if (array[j] < array[pivot]) {
                // 遍历到的元素 如果 < pivot，则替换到数组前部，并累加count
                swap(array, counter, j);
                counter++;
            }
        }
        // 将pivot移到中间点
        swap(array, counter, pivot);
        
        return counter; // !!! 
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
