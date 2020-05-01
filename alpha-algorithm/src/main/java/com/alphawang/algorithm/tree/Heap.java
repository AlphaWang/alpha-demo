package com.alphawang.algorithm.tree;

import java.util.Arrays;

public class Heap {
    
    private int[] array;
    private int capacity; // 最大可存储个数
    private int count; // 已存储数据个数
    
    public Heap(int capacity) {
        this.array = new int[capacity + 1];
        this.capacity = capacity;
        this.count = 0;
    }
    
    public String toString() {
        return Arrays.toString(array);
    }
    
    public void printTree() {
        int[] data = Arrays.copyOfRange(array, 1, array.length);
        TreeNode<Integer> tree = TreeNodeCreator.createTree(data);
        TreeNodePrinter.print(tree);
    }

    /**
     * 插入
     * 1. 放入数组末尾。
     * 2. 依次与父节点比较、交换。从下往上堆化
     */
    public void insert(int data) {
        if (count >= capacity) {
            System.out.println("---- exceed capacity " + capacity);
            return;
        }
        
        // 放入最后位置，从1计数
        array[++count] = data;
        // 与父节点比较，自下往上 堆化
        int currentIndex = count;
        int parentIndex = count / 2;
        
        while (parentIndex > 0 ) {
            int parent = array[parentIndex];
            int current = array[currentIndex];
            if (parent < current) {
                swap(array, parentIndex, currentIndex);
                
                currentIndex = parentIndex;
                parentIndex = currentIndex / 2;
            } else {
                break;
            }
        }

        System.out.println("Added " + data);
        printTree();
    }
    
    private void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
//        System.out.println(String.format("----- swap: %s - %s", i, j));
    }
    

    public static void main(String[] args) {
        Heap heap = new Heap(5);
        heap.insert(1);
        heap.insert(2);
        heap.insert(3);
        heap.insert(4);
        heap.insert(5);
        heap.insert(6);

        System.out.println(heap);
        
    }
    

}
