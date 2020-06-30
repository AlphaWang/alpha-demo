package com.alphawang.algorithm.search;

/**
 * https://leetcode.com/problems/sqrtx/
 * Easy
 */
public class T0069_SqrtX {

    /**
     * 1. 二分法：mid * mid 越界
     */
    public int mySqrt(int x) {
        if (x == 0) return 0;
        int left = 0, right = x;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            double square = mid * mid;
            if (square == x) {
                return mid;
            } else if (square > x) {
                right = mid - 1;
            } else if (square < x) {
                left = mid + 1;
            }
        }
        
        return left - 1;
    }

    /**
     * 1. 二分法：优化越界
     *    2ms - 30%
     */
    public int mySqrt1_1(int x) {
        if (x == 0 || x == 1) return x;
        
        // right = x / 2，缩小搜索范围
        long left = 0, right = x / 2;
        while (left <= right) {
            long mid = left + (right - left) / 2;
            long square = mid * mid;
            if (square == x) {
                return (int) mid;
            } else if (square > x) {
                right = mid - 1;
            } else if (square < x) {
                left = mid + 1;
            }
        }
        /**
         * TODO
         */
        return (int) right;
//        return (int) (left - 1);
    }

    /**
     * 2. 二分法：除法计算
     *    2ms - 30%
     */
    public int mySqrt2(int x) {
        if (x == 0) return 0;
        if (x == 1) return 1;
        int left = 0, right = x;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            int tmp = x / mid;
            if (mid == tmp) {
                return mid;
            } else if (tmp < mid) {
                right = mid - 1;
            } else if (tmp > mid) {
                left = mid + 1;
            }
        }

        return left - 1;
    }

    /**
     * TODO
     * 3. 牛顿迭代法
     *    1ms - 100%
     */
    public int mySqrt3(int x) {
        long r = x;
        long t = x;
        while (r * r > t) {
            r = (r + t / r) / 2;
        }
        
        return (int) r;
    }

    public static void main(String[] args) {
        /*
         * Input: 4
         * Output: 2
         */
        test(4);

        /*
         * Input: 8
         * Output: 2
         */
        test(8);

        test(2);
        test(1);

        /*
         * mid * mid == 负数
         * 2147395599
         * 46339
         */
        test(2147395599);
    }
    
    private static void test(int x) {
        T0069_SqrtX sut = new T0069_SqrtX();
        System.out.println(sut.mySqrt3(x));
    }

}
