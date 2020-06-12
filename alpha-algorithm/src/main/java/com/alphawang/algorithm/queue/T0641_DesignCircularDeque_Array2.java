package com.alphawang.algorithm.queue;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/design-circular-deque/
 * Medium
 */
public class T0641_DesignCircularDeque_Array2 {

    /**
     * 头指针 + size
     */
    int[] data;
    int capacity;
    int head;
    int size;

    /** Initialize your data structure here. Set the size of the deque to be k. */
    public T0641_DesignCircularDeque_Array2(int k) {
        this.capacity = k;
        this.data = new int[capacity];
        this.head = 0;
        this.size = 0;
    }

    /** Adds an item at the front of Deque. Return true if the operation is successful. */
    public boolean insertFront(int value) {
        if (isFull()) {
            return false;
        }
        
        head = (head - 1 + capacity) % capacity;
        data[head] = value;
        size++;
        return true;
    }

    /** Adds an item at the rear of Deque. Return true if the operation is successful. */
    public boolean insertLast(int value) {
        if (isFull()) {
            return false;
        }

        data[(head + size) % capacity] = value;
        size++;
        return true;
    }

    /** Deletes an item from the front of Deque. Return true if the operation is successful. */
    public boolean deleteFront() {
        if (isEmpty()) {
            return false;
        }
        head = (head + 1) % capacity;
        size--;
        return true;
    }

    /** Deletes an item from the rear of Deque. Return true if the operation is successful. */
    public boolean deleteLast() {
        if (isEmpty()) {
            return false;
        }
        
        size--;
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
        return data[(head + size - 1) % capacity]; // !!! 
    }

    /** Checks whether the circular deque is empty or not. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Checks whether the circular deque is full or not. */
    public boolean isFull() {
        return size == capacity;
    }

    @Override
    public String toString() {
        return String.format("%s, %s - %s", Arrays.toString(data), head, size);
    }

    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        T0641_DesignCircularDeque_Array2 circularDeque = new T0641_DesignCircularDeque_Array2(3); // set the size to be 3
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
        T0641_DesignCircularDeque_Array2 circularDeque = new T0641_DesignCircularDeque_Array2(8);
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
        T0641_DesignCircularDeque_Array2 circularDeque = new T0641_DesignCircularDeque_Array2(5);
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
