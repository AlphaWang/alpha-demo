package com.alphawang.algorithm.sort.nlogn;

import static com.alphawang.algorithm.Utils.swap;

import com.alphawang.algorithm.Utils;
import com.alphawang.algorithm.tree.Heap;

public class HeapSort {

    /**
     * TODO
     * 1. 创建堆
     * 2. 排序
     */
    private static int[] heapSort(int[] array) {
        // 1. 创建堆
        Heap heap = Heap.buildHeap2(array); 
        heap.printTree();
        
        
        int[] heapArray = heap.getArray();
        int last = heapArray.length - 1;
        while (last > 1) {
            // 2. 将堆顶 放到数组末尾
            swap(heapArray, 1, last);
            // 再将末尾节点之外的部分堆化
            last--;
            Heap.heapifyFromTop(heapArray, last, 1);
        }
        
        heap.printTree();
        return heapArray;
    }

    public static void main(String[] args) {
        Utils.print(heapSort(new int[] { 3, 2, 6, 5, 4}));
    }


}
