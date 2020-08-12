package com.alphawang.algorithm.search;

/**
 * https://leetcode.com/problems/sqrtx/
 * Easy
 * 
 * 计算并返回 x 的平方根，其中 x 是非负整数。
 * 由于返回类型是整数，结果只保留整数的部分，小数部分将被舍去。
 * 
 * 升级：考虑小数
 */
public class T0069_SqrtX2 {

    public float mySqrt2(int x, float precision) {
        if (x == 0) return 0;
        if (x == 1) return 1;
        
        float left = 0, right = x;
        float mid = (left + right) / 2, last = 0;
        do {
            float res = mid * mid;
            if (res == x) {
                return mid;
            } else if (res < x) {
                left = mid;
            } else {
                right = mid;
            }

            last = mid;
            mid = (left + right) / 2;
        } while (Math.abs(mid - last) > precision);
        return mid;
    }

    public static void main(String[] args) {
        /*
         * Input: 4
         * Output: 2
         */
//        test(4, 1);

        /*
         * Input: 8
         * Output: 2
         */
        test(8, 1);

        test(2, 1);
        test(1, 1);

        /*
         * mid * mid == 负数
         * 2147395599
         * 46339
         */
        test(2147395599, 1);
    }
    
    private static void test(int x, float precision) {
        T0069_SqrtX2 sut = new T0069_SqrtX2();
        System.out.println(String.format("%s : %s --> %s", x, precision, sut.mySqrt2(x, precision)));
    }

}
