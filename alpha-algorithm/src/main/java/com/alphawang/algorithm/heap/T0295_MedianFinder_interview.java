package com.alphawang.algorithm.heap;

import java.util.PriorityQueue;

public class T0295_MedianFinder_interview {

    PriorityQueue<Integer> a;
    PriorityQueue<Integer> b;

    public T0295_MedianFinder_interview() {
        a = new PriorityQueue<Integer>();
        b = new PriorityQueue<Integer>((x,y) -> (y -x));
    }

    public void addNum(int num) {
        if (a.size() != b.size()) {
            a.add(num);
            b.add(a.poll());
        } else {
            b.add(num);
            a.add(b.poll());
        }
    }

    public double findMedian() {
        if (a.size() != b.size()) {
            return a.peek();
        } else {
            return (a.peek() + b.peek())/2.0;
        }
    }

    public static void main(String[] args) {
        System.out.println("?");
        test1();
        test2();
        test3();
    }

    public static void test1() {
        T0295_MedianFinder_interview sut = new T0295_MedianFinder_interview();
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
        T0295_MedianFinder_interview sut = new T0295_MedianFinder_interview();
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
        T0295_MedianFinder_interview sut = new T0295_MedianFinder_interview();
        sut.addNum(1);
        sut.addNum(2);
        System.out.println(sut.findMedian()); //1.5
        sut.addNum(3);
        System.out.println(sut.findMedian()); //2.0
    }

}
