package com.alphawang.algorithm.dp.greedy;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/assign-cookies/
 * Easy
 * 
 * g: 胃口期望
 * s: 饼干大小
 */
public class T0455_AssignCookies {

    /**
     * 1. 贪心：从最小的胃口开始满足
     *    10ms - 25%
     */
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        
        int result = 0;
        int gIndex = 0, sIndex = 0;
        while (gIndex < g.length && sIndex < s.length) {
            if (g[gIndex] <= s[sIndex]) {
                result++;
                gIndex++;
                sIndex++;
            } else {
                sIndex++;
            }
        }
        
        return result;
    }

    public static void main(String[] args) {
        /*
         * Input: [1,2,3], [1,1]
         * Output: 1
         */
        test(new int[] {1,2,3}, new int[] {1,1});
        /*
         * Input: [1,2], [1,2,3]
         * Output: 2
         */
        test(new int[] {1,2}, new int[] {1,2,3});
        
    }
    
    private static void test(int[] g, int[] s) {
        T0455_AssignCookies sut = new T0455_AssignCookies();
        System.out.println(sut.findContentChildren(g, s));
    }
}
