package com.alphawang.algorithm.heap;

import com.alphawang.algorithm.tree.TreeNode;
import com.alphawang.algorithm.tree.TreeNodeCreator;
import com.alphawang.algorithm.tree.TreeNodePrinter;
import java.util.Arrays;

public class HeapFlexible {
    
    private int[] array;
    private int capacity; // 最大可存储个数
    private int count; // 已存储数据个数
    
    private static final int D = 2; // 子节点个数
    
    public HeapFlexible(int capacity) {
        this.array = new int[capacity];
        this.capacity = capacity;
        this.count = 0;
    }

    public HeapFlexible(int[] array) {
        this.array = array;
        this.capacity = array.length - 1;
        this.count = array.length - 1;
    }
    
    public String toString() {
        return Arrays.toString(array);
    }
    
    public void printTree() {
        TreeNode tree = TreeNodeCreator.createTree(array);
        TreeNodePrinter.print(tree);
    }
    
    public int[] getArray() {
        return array;
    }
    
    public boolean isEmpty() {
        return count <= 0;
    }
    
    public boolean isFull() {
        return count >= capacity;
    }
    
    private int parent(int i) {
        return (i - 1) / D;
    }
    
    private int kthChild(int i, int k) {
        return i * D + k;
    } 

    /**
     * 插入
     * 1. 放入数组末尾。
     * 2. 依次与父节点比较、交换。从下往上堆化
     */
    public void insert(int data) {
        if (data <= 0) {
            return;
        }
        if (isFull()) {
            System.out.println("---- exceed capacity " + capacity);
            return;
        }
        
        // 放入最后位置，从0计数
        array[count++] = data;
        // 自下往上 堆化
        heapifyFromBottom(count - 1);

        System.out.println("---- Added " + data);
        printTree();
    }

    /**
     * 删除指定元素
     * 
     * 将末尾元素放入空缺处，并向下堆化
     */
    public int remove(int x) {
        if (isEmpty()) {
            System.out.println("Heap is empty, cannot delete " + x);
            return -1;
        }
        
        int data = array[x];
        array[x] = array[--count];
        array[count] = 0;
        heapifyFromTop(x);

        System.out.println("---- Removed array[" + x + "] " + data);
        printTree();
        
        return data;
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
        return remove(0);
    }

    /**
     * 创建堆
     * 
     * 方法一：从前往后，逐个调用 insert()：从下往上堆化
     * 
     * 方法二：从后往前处理，从上往下堆化
     * 
     */
    public static HeapFlexible buildHeap1(int[] array) {
        System.out.println("build heap for " + Arrays.toString(array));
        HeapFlexible heap = new HeapFlexible(array.length);
        for (int i = 0; i < array.length; i++) {
            heap.insert(array[i]);
        }
        
        return heap;
    }
    
    /**
     * 自下而上堆化：
     * 依次与父节点比较，交换 --> 优化：不用每次都交换
     */
    public void heapifyFromBottom(int fromIndex) {
        int fromValue = array[fromIndex];
        int currentIndex = fromIndex;
        
        while (currentIndex > 0) {
            int parentIndex = parent(currentIndex);
            if (fromValue > array[parentIndex]) {
                array[currentIndex] = array[parentIndex];
                currentIndex = parentIndex;
            } else {
                break;
            }
        }
        array[currentIndex] = fromValue;
    }
    
    public void heapifyFromTop(int fromIndex) {
        int fromValue = array[fromIndex];
        
        int currentIndex = fromIndex;
        while (kthChild(currentIndex, 1) < count) {
            int maxChildIndex = maxChild(currentIndex);
            if (fromValue < array[maxChildIndex]) {
                array[currentIndex] = array[maxChildIndex];
                currentIndex = maxChildIndex;
            } else {
                break;
            }
        }
        array[currentIndex] = fromValue;
    }
    
    private int maxChild(int i) {
        if (kthChild(i, 1) >= count) {
            return -1;
        }
        
        int maxChildIndex = kthChild(i, 1);
        for (int k = 2; k <= D; k++) {
            int kthChild = kthChild(i, k); 
            if (kthChild >= count) {
                return maxChildIndex;
            }
            if (array[maxChildIndex] < array[kthChild]) {
                maxChildIndex = kthChild;
            }
        }
        return maxChildIndex;
    }
    
    public static void main(String[] args) {
        HeapFlexible heap = new HeapFlexible(5);
        
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
