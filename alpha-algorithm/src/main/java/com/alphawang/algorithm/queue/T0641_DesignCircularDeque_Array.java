package com.alphawang.algorithm.queue;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/design-circular-deque/
 * Medium
 */
public class T0641_DesignCircularDeque_Array {
    
    int[] data;
    int capacity;
    int head;
    int tail;

    /** Initialize your data structure here. Set the size of the deque to be k. */
    public T0641_DesignCircularDeque_Array(int k) {
        this.capacity = k + 1;
        this.data = new int[capacity];
        // 表示头部首个存放数据的位置
        // 插入时：先减，再赋值。
        this.head = 0;
        // 表示尾部下一个待插入的位置
        // 插入时：先赋值，再加。
        this.tail = 0;
    }

    /** Adds an item at the front of Deque. Return true if the operation is successful. */
    public boolean insertFront(int value) {
        if (isFull()) {
            return false;
        }
        
        head = (head - 1 + capacity) % capacity;
        data[head] = value;
        return true;
    }

    /** Adds an item at the rear of Deque. Return true if the operation is successful. */
    public boolean insertLast(int value) {
        if (isFull()) {
            return false;
        }

        data[tail] = value;
        tail = (tail + 1) % capacity;

        return true;
    }

    /** Deletes an item from the front of Deque. Return true if the operation is successful. */
    public boolean deleteFront() {
        if (isEmpty()) {
            return false;
        }
        head = (head + 1) % capacity;
        return true;
    }

    /** Deletes an item from the rear of Deque. Return true if the operation is successful. */
    public boolean deleteLast() {
        if (isEmpty()) {
            return false;
        }
        
        tail = (tail - 1 + capacity) % capacity;
        return true;
    }

    /** Get the front item from the deque. */
    public int getFront() {
        if (isEmpty()) return -1;
        return data[head];
    }

    /** Get the last item from the deque. */
    public int getRear() {
        if (isEmpty()) return -1;
        return data[(tail - 1 + capacity) % capacity]; // int rearIndex = tail - 1 < 0 ? capacity - 1 : tail - 1; 
    }

    /** Checks whether the circular deque is empty or not. */
    public boolean isEmpty() {
        return head == tail;
    }

    /** Checks whether the circular deque is full or not. */
    public boolean isFull() {
        return (tail + 1) % capacity == head;
    }

    @Override
    public String toString() {
        return String.format("%s, %s - %s", Arrays.toString(data), head, tail);
    }

    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        T0641_DesignCircularDeque_Array circularDeque = new T0641_DesignCircularDeque_Array(3); // set the size to be 3
        println(circularDeque.insertLast(1)); println(circularDeque);// return true
        println(circularDeque.insertLast(2)); println(circularDeque);// return true
        println(circularDeque.insertFront(3)); println(circularDeque);// return true
        println(circularDeque.insertFront(4)); println(circularDeque);// return false, the queue is full
        println(circularDeque.getRear());// return 2
        println(circularDeque.isFull());				// return true
        println(circularDeque.deleteLast()); println(circularDeque);// return true
        println(circularDeque.insertFront(4));println(circularDeque);	// return true
        println(circularDeque.getFront());			// return 4
    }

    private static void test2() {
//      ["MyCircularDeque","insertFront","getFront","isEmpty","deleteFront","insertLast","getRear","insertLast","insertFront","deleteLast","insertLast","isEmpty"]
//      [[8],[5],[],[],[],[3],[],[7],[7],[],[4],[]]
        T0641_DesignCircularDeque_Array circularDeque = new T0641_DesignCircularDeque_Array(8);
        println(circularDeque.insertFront(5));
        println(circularDeque.getFront());
        println(circularDeque.isEmpty());
        println(circularDeque.deleteFront());
        println(circularDeque.insertLast(3));
        println(circularDeque.getRear());
        println(circularDeque.insertLast(7));
        println(circularDeque.insertFront(7));
        println(circularDeque.deleteLast());
        println(circularDeque.insertLast(4));
        println(circularDeque.isEmpty());
    }
    
    private static void test3() {
//      ["MyCircularDeque","insertFront","insertLast","getFront","insertLast","getFront","insertFront","getRear","getFront","getFront","deleteLast","getRear"]
//      [[5],[7],[0],[],[3],[],[9],[],[],[],[],[]]
        T0641_DesignCircularDeque_Array circularDeque = new T0641_DesignCircularDeque_Array(5);
        println(circularDeque.insertFront(7));
        println(circularDeque.insertLast(0));
        println(circularDeque.getFront());
        println(circularDeque.insertLast(3));
        println(circularDeque.getFront());
        println(circularDeque.insertFront(9));
        println(circularDeque.getRear());
        println(circularDeque.getFront());
        println(circularDeque.getFront());
        println(circularDeque.deleteLast());
        println(circularDeque.getRear());
    }

    private static void println(Object o) {
        System.out.println(o);
    }
}
