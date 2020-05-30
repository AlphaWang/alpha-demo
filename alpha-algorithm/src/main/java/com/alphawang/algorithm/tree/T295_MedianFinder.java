package com.alphawang.algorithm.tree;

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

public class T295_MedianFinder {
    
    private PriorityQueue<Integer> bigHalf = new PriorityQueue();
    private PriorityQueue<Integer> smallHalf = new PriorityQueue(Comparator.reverseOrder());

    public void addNum(int num) {
        bigHalf.offer(num);
        if (bigHalf.size() > smallHalf.size() + 1) {
            int mid = bigHalf.poll();
            smallHalf.offer(mid);
        }

        System.out.println("bigHalf:   " + bigHalf);
        System.out.println("smallHalf: " + smallHalf);
    }

    public double findMedian() {
        if (bigHalf.size() == smallHalf.size()) {
            return (bigHalf.peek() + smallHalf.peek()) / 2.0;
        } else {
            return bigHalf.peek();
        }
    }

    public static void main(String[] args) {
        T295_MedianFinder sut = new T295_MedianFinder();
        sut.addNum(4);
        sut.addNum(5);
        System.out.println(sut.findMedian()); //4.5
        
        sut.addNum(6);
        System.out.println(sut.findMedian()); //5

        sut.addNum(3);
        System.out.println(sut.findMedian()); //4.5
    }
    
}
