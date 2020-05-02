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
        // 自下往上 堆化
        heapifyFromBottom(array, count);

        System.out.println("---- Added " + data);
        printTree();
    }

    /**
     *  删除堆顶元素
     *  
     *  方法一：删除第一个元素；并查找子节点，将第二大元素放到堆顶；依次类推。
     *  --> 可能导致数组空洞，不符合完全二叉树
     *  
     *  方法二：
     *  1. 将最后一个元素放到堆顶。--> 可保证不会有数组空洞
     *  2. 向下堆化。
     *  
     */
    public int removeMax() {
        if (count == 0) {
            System.out.println("---- Empty.");
            return -1;
        }
        // 暂存堆顶
        int max = array[1];
        // 将最后一个元素放到堆顶
        array[1] = array[count];
        array[count] = 0;
        count--;
        // 自上而下堆化
        heapifyFromTop(array, count, 1);

        System.out.println("---- Removed " + max);
        printTree();
        return max;
    }

    /**
     * 自下而上堆化：
     * 依次与父节点比较，交换
     */
    private void heapifyFromBottom(int[] array, int fromIndex) {
        int currentIndex = fromIndex;
        int parentIndex = currentIndex / 2;

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
    }
    
    private void heapifyFromTop(int[] array, int count, int fromIndex) {
        int currentIndex = fromIndex;
       
        while (currentIndex <= count) {
            // find next max
            int maxIndex = currentIndex;
            int leftChildIndex = currentIndex * 2;
            if (leftChildIndex <= count && array[currentIndex] < array[leftChildIndex]) {
                maxIndex = leftChildIndex;
            }
            int rightChildIndex = currentIndex * 2 + 1;
            if (rightChildIndex <= count && array[maxIndex] < array[rightChildIndex]) {
                maxIndex = rightChildIndex;
            }
            
            // 比子节点都大，则堆化结束
            if (currentIndex == maxIndex) {
                break;
            }
            // 否则，交换
            swap(array, currentIndex, maxIndex);
            currentIndex = maxIndex;
        }
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
        
        heap.removeMax();
        heap.removeMax();
        heap.removeMax();
        heap.removeMax();
        heap.removeMax();
        heap.removeMax();
        
        
    }
    

}
