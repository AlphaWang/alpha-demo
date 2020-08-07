package com.alphawang.algorithm.heap;

import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/kth-largest-element-in-a-stream/
 * Easy
 */
public class T0703_KthLargestElementInAStream {

    /**
     * 堆
     * 15ms - 91%
     */
    private int kth;
    private int k;
    
    private PriorityQueue<Integer> pq;
    
    public T0703_KthLargestElementInAStream(int k, int[] nums) {
        this.k = k;
        pq = new PriorityQueue<>();
        for (int num : nums) {
            add(num);
        }
    }

    public int add(int val) {
        System.out.println("add " + val);
        if (pq.size() < k) {
            pq.offer(val);
        } else {
            if (pq.peek() < val) {
                pq.poll();
                pq.offer(val);
            }
        }
        kth = pq.peek();

        System.out.println(pq);
        System.out.println("res : " + kth);
        return kth;
    }

    public static void main(String[] args) {
//        test1();
        test2();
    }

    public static void test1() {
        int[] arr = new int[] {4,5,8,2};
        T0703_KthLargestElementInAStream kthLargest = new T0703_KthLargestElementInAStream(3, arr);
        kthLargest.add(3);   // returns 4
        kthLargest.add(5);   // returns 5
        kthLargest.add(10);  // returns 5
        kthLargest.add(9);   // returns 8
        kthLargest.add(4);   // returns 8
    }

    /*
     * ["KthLargest","add","add","add","add","add"]
     * [[1,[]],[-3],[-2],[-4],[0],[4]]
     * 
     * [null,-3,-2,-2,0,4]
     */
    public static void test2() {
        int[] arr = new int[] {};
        T0703_KthLargestElementInAStream kthLargest = new T0703_KthLargestElementInAStream(1, arr);
        kthLargest.add(-3);   // returns -3
        kthLargest.add(-2);   // returns -2
        kthLargest.add(-4);  // returns -2
        kthLargest.add(0);   // returns 0
        kthLargest.add(4);   // returns 4
    }
}
