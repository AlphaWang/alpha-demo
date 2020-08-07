package com.alphawang.algorithm.heap;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * https://leetcode-cn.com/problems/find-median-from-data-stream/
 * 
 * 中位数是有序列表中间的数。如果列表长度是偶数，中位数则是中间两个数的平均值。
 *
 * 例如，[2,3,4] 的中位数是 3. [2,3] 的中位数是 (2 + 3) / 2 = 2.5
 *
 * 设计一个支持以下两种操作的数据结构：
 *
 * void addNum(int num) - 从数据流中添加一个整数到数据结构中。
 * double findMedian() - 返回目前所有元素的中位数。
 * 
 * 示例：
 * addNum(1)
 * addNum(2)
 * findMedian() -> 1.5
 * addNum(3) 
 * findMedian() -> 2
 * 
 * 进阶:
 *
 * 如果数据流中所有整数都在 0 到 100 范围内，你将如何优化你的算法？
 * 如果数据流中 99% 的整数都在 0 到 100 范围内，你将如何优化你的算法？
 */

public class T0295_MedianFinder {

    /**
     * 两个堆
     * 
     * 43ms - 92%
     */
    private PriorityQueue<Integer> bigHalf;
    private PriorityQueue<Integer> smallHalf; //奇数时，这个队列比 bigalf 多一个

    public T0295_MedianFinder() {
        bigHalf = new PriorityQueue();
        smallHalf = new PriorityQueue(Comparator.reverseOrder());
    }
    /**
     * BUG
     * input : 4, 5, 6
     * smallHalf = 6, 4
     * bigHalf = 5
     */
    public void addNum_(int num) {                                                              
        smallHalf.offer(num);
        if (smallHalf.size() > bigHalf.size() + 1) {
            int mid = smallHalf.poll();
            bigHalf.offer(mid);
        }

        System.out.println("bigHalf:   " + bigHalf);
        System.out.println("smallHalf: " + smallHalf);
    }

    /**
     * 插入时，平衡两个堆
     */
    public void addNum2(int num) {
        Integer big = bigHalf.peek();
        if (big != null && big < num) {
            bigHalf.offer(num);
        } else {
            smallHalf.offer(num);
        }

        if (bigHalf.size() > smallHalf.size()) {
            smallHalf.offer(bigHalf.poll());
        } else if (smallHalf.size() > bigHalf.size() + 1) {
            bigHalf.offer(smallHalf.poll());
        }

        System.out.println("bigHalf:   " + bigHalf);
        System.out.println("smallHalf: " + smallHalf);
    }

    // 优化 add
    public void addNum(int num) {
        smallHalf.offer(num);
        bigHalf.offer(smallHalf.poll());
        
        if (smallHalf.size() < bigHalf.size()) {
            smallHalf.offer(bigHalf.poll());
        }

        System.out.println("bigHalf:   " + bigHalf);
        System.out.println("smallHalf: " + smallHalf);
    }

    /**
     * 读取时，对比两个queue的大小
     */
    public double findMedian() {
        if (bigHalf.size() == smallHalf.size()) {
            return (bigHalf.peek() + smallHalf.peek()) / 2.0;
        } else {
            return smallHalf.peek();
        }
    }

    public static void main(String[] args) {
        test1();
        test2();
        test3();
    }

    public static void test1() {
        T0295_MedianFinder sut = new T0295_MedianFinder();
        sut.addNum(4);
        sut.addNum(5);
        System.out.println(sut.findMedian()); //4.5

        sut.addNum(6);
        System.out.println(sut.findMedian()); //5

        sut.addNum(3);
        System.out.println(sut.findMedian()); //4.5
    }

    /*
     * ["MedianFinder","addNum","findMedian","addNum","findMedian","addNum","findMedian","addNum","findMedian","addNum","findMedian"]
     * [[],[-1],[],[-2],[],[-3],[],[-4],[],[-5],[]]
     */
    public static void test2() {
        T0295_MedianFinder sut = new T0295_MedianFinder();
        sut.addNum(-1);
        System.out.println(sut.findMedian()); //-1.0

        sut.addNum(-2);
        System.out.println(sut.findMedian()); //-1.5

        sut.addNum(-3);
        System.out.println(sut.findMedian()); //-2

        sut.addNum(-4);
        System.out.println(sut.findMedian()); //-2.5

        sut.addNum(-5);
        System.out.println(sut.findMedian()); //-3
    }

    /*
     * ["MedianFinder","addNum","addNum","findMedian","addNum","findMedian"]
     * [[],[1],[2],[],[3],[]]
     */
    public static void test3() {
        T0295_MedianFinder sut = new T0295_MedianFinder();
        sut.addNum(1);
        sut.addNum(2);
        System.out.println(sut.findMedian()); //1.5
        sut.addNum(3);
        System.out.println(sut.findMedian()); //2.0
    }
    
}
