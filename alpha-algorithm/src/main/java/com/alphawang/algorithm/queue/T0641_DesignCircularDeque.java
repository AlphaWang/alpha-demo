package com.alphawang.algorithm.queue;


/**
 * https://leetcode.com/problems/design-circular-deque/
 * Medium
 */
public class T0641_DesignCircularDeque {
    
    int capacity;
    int size;
    Node head;
    Node tail;
    
    static class Node {
        int val;
        Node prev;
        Node next;
        
        public Node(int val) {
            this.val = val;
        }
    }
    
    /** Initialize your data structure here. Set the size of the deque to be k. */
    public T0641_DesignCircularDeque(int k) {
       this.capacity = k;
       this.size = 0;
       head = new Node(-1);
       tail = new Node(-1);
//       head.prev = tail;
//       tail.prev = head;
    }

    /** Adds an item at the front of Deque. Return true if the operation is successful. */
    public boolean insertFront(int value) {
        if (size >= capacity) {
            return false;
        }
        
        Node node = new Node(value);
        if (size == 0) {
            tail.next = node;
            head.next = node;
        } else {
            Node first = head.next;
            head.next = node;

            first.prev = node;
            node.next = first;

            node.prev = tail.next;
            tail.next.next = node;
        }
        
        size++;
        return true;
    }

    /** Adds an item at the rear of Deque. Return true if the operation is successful. */
    public boolean insertLast(int value) {
        if (size >= capacity) {
            return false;
        }
        
        Node node = new Node(value);
        if (size == 0) {
            tail.next = node;
            head.next = node;
        } else {
            Node last = tail.next;
            tail.next = node;

            last.next = node;
            node.prev = last;

            node.next = head.next;
            head.next.prev = node; 
        }
        
        size++;
        return true;
    }

    /** Deletes an item from the front of Deque. Return true if the operation is successful. */
    public boolean deleteFront() {
        if (head.next == null) {
            return false;
        }

        if (size == 1) {
            tail.next = null;
            head.next = null;
            size--;
            return true;
        }
        
        Node newHead = head.next.next;
        head.next.next = null;
        
        head.next = newHead;
        newHead.prev = tail.next;
        tail.next.next = newHead;
        
        size--;
        return true;
    }

    /** Deletes an item from the rear of Deque. Return true if the operation is successful. */
    public boolean deleteLast() {
        if (tail.next == null) {
            return false;
        }
        
        if (size == 1) {
            tail.next = null;
            head.next = null;
            size--;
            return true;
        }
        
        Node newTail = tail.next.prev;
        tail.next.prev = null;
        
        tail.next = newTail;
        newTail.next = head.next;
        head.next.prev = newTail;
        
        size--;
        return true;
    }

    /** Get the front item from the deque. */
    public int getFront() {
        return head.next == null ? -1 : head.next.val;
    }

    /** Get the last item from the deque. */
    public int getRear() {
        return tail.next == null ? -1 : tail.next.val;
    }

    /** Checks whether the circular deque is empty or not. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Checks whether the circular deque is full or not. */
    public boolean isFull() {
        return size == capacity;
    }

    public static void main(String[] args) {
        test2();
    }
    
    private static void test1() {
        T0641_DesignCircularDeque circularDeque = new T0641_DesignCircularDeque(3); // set the size to be 3
        println(circularDeque.insertLast(1));			// return true
        println(circularDeque.insertLast(2));			// return true
        println(circularDeque.insertFront(3));			// return true
        println(circularDeque.insertFront(4));			// return false, the queue is full
        println(circularDeque.getRear());  			// return 2
        println(circularDeque.isFull());				// return true
        println(circularDeque.deleteLast());			// return true
        println(circularDeque.insertFront(4));			// return true
        println(circularDeque.getFront());			// return 4
    }

    private static void test2() {
//      ["MyCircularDeque","insertFront","getFront","isEmpty","deleteFront","insertLast","getRear","insertLast","insertFront","deleteLast","insertLast","isEmpty"]
//      [[8],[5],[],[],[],[3],[],[7],[7],[],[4],[]]
        T0641_DesignCircularDeque circularDeque = new T0641_DesignCircularDeque(8);
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

        private static void println(Object o) {
        System.out.println(o);
    }
}
