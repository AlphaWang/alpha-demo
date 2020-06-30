package com.alphawang.algorithm.ideas.greedy;

/**
 * https://leetcode.com/problems/lemonade-change/
 * Easy
 */
public class T0860_LemonadeChange {

    /**
     * 1. 记录收到的5元、10元的数目
     *    2ms - 70%
     */
    public boolean lemonadeChange(int[] bills) {
        int count5 = 0, count10 = 0;
        for (int bill : bills) {
            if (bill == 5) {
                count5++;
            }
            if (bill == 10) {
                if (count5 >= 1) {
                    count5--;
                    count10++;
                } else {
                    return false;
                }
            }
            if (bill == 20) {
                if (count10 >= 1 && count5 >= 1) {
                    count5--;
                    count10--;
                } else if (count5 >= 3) {
                    count5 -= 3;
                } else {
                    return false;
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        /*
         * Input: [5,5,5,10,20]
         * Output: true
         */
        test(new int[] {5,5,5,10,20});
        /*
         * Input: [5,5,10]
         * Output: true
         */
        test(new int[] {5,5,10});
        /*
         * Input: [10,10]
         * Output: false
         */
        test(new int[] {10,10});
        /*
         * Input: [5,5,10,10,20]
         * Output: false
         */
        test(new int[] {5,5,10,10,20});
    }

    private static void test(int[] bills) {
        T0860_LemonadeChange sut = new T0860_LemonadeChange();
        System.out.println(sut.lemonadeChange(bills));
    }

}
