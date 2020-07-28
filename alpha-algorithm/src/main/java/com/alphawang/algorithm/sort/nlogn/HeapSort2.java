package com.alphawang.algorithm.sort.nlogn;

import java.util.Arrays;

public class HeapSort2 {

    /**
     * 从上往下 堆化
     */
    static void heapify(int[] array, int length, int i) {
        int left = 2 * i + 1, right = 2 * i + 2;
        int largest = i;
        if (left < length && array[left] > array[largest]) {
            largest = left;
        }
        if (right < length && array[right] > array[largest]) {
            largest = right;
        }
        if (largest != i) {
            int temp = array[i]; array[i] = array[largest]; array[largest] = temp;
            heapify(array, length, largest);
        }
    }

    /**
     * 
     */
    public static void heapSort(int[] array) {
        System.out.println("Sorting " + Arrays.toString(array));
        
        if (array.length == 0) return;
        int length = array.length;
        
        // TODO 为何是从 length / 2 - 1 开始？？
        for (int i = length / 2 - 1; i >= 0; i--) {
            heapify(array, length, i);
        }

        System.out.println("1 --> " + Arrays.toString(array));
        for (int i = length - 1; i >= 0; i--) {
            int temp = array[0]; array[0] = array[i]; array[i] = temp;
            heapify(array, i, 0);
        }

        System.out.println("2 --> " + Arrays.toString(array));
    }

    public static void main(String[] args) {
        heapSort(new int[] { 3, 2, 6, 5, 4});
    }


}
