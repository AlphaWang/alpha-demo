package com.alphawang.algorithm.number;

import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * 264
 * https://leetcode.com/problems/ugly-number-ii/
 * Medium
 * 
 * Write a program to find the n-th ugly number
 */
public class T0264_UglyNumber2 {

    /**
     * 1. 暴力解法：循环遍历
     *    超时
     */
    public int nthUglyNumber(int n) {
        if(n == 1) {
            return 1;
        }
        
        int count = 1;
        int result = 1;
        while (count < n) {
            if (isUgly(++result)) {
                count++;
            }
        }
        
        return result;
    }

    public static boolean isUgly(int num) {
        if (num <= 0) return false;
        if (num == 1) return true;
        while (num != 1) {
            if (num % 2 == 0) num /= 2;
            else if (num % 3 == 0) num /= 3;
            else if (num % 5 == 0) num /= 5;
            else return false;
        }

        return true;
    }

    /**
     * 2. 暴力 + 预定义
     *    超时
     */
    public int nthUglyNumber2(int n) {
        return UglyNumbers2.nums[n - 1];
    }
    
    static class UglyNumbers2 {
        public static int[] nums = new int[1690];
        static {
            int count = 0;
            int result = 0;
            while (count < 1690) {
                if (isUgly(++result)) {
                    nums[count] = result;
                    count++;
                }
            }
        }
    }

    /**
     * 3. 小顶堆
     *    3ms
     * 
     * 将计算得出的丑数放入Heap，再从小到大取出依次乘以 [2,3,5]
     */
    public int nthUglyNumber3(int n) {
        System.out.println(Arrays.toString(UglyNumbers3.nums));
        return UglyNumbers3.nums[n - 1];
    }

    static class UglyNumbers3 {
        public static int[] nums = new int[1690];
        private static final int[] primes = new int[] {2, 3, 5};
        static {
            Set<Long> parsed = new HashSet<>();
            PriorityQueue<Long> heap = new PriorityQueue<>();
            parsed.add(1L); 
            heap.add(1L);
            
            for (int i = 0; i < 1690; i++) {
                long cur = heap.poll();
                nums[i] = (int) cur;
                
                for (int prime : primes) {
                    long newUgly = cur * prime;
                    if (!parsed.contains(newUgly)) {
                        parsed.add(newUgly);
                        heap.add(newUgly);
                    }
                }
            }
        }
    }

    /**
     * 4: DP，三指针 表示与 2/3/5 相乘的数
     *    3ms
     */
    public int nthUglyNumber4(int n) {
        System.out.println(Arrays.toString(UglyNumbers4.nums));
        return UglyNumbers4.nums[n - 1];
    }

    static class UglyNumbers4 {

        public static int[] nums = new int[1690];
        
        static {
            nums[0] = 1;
            int p2 = 0, p3 = 0, p5 = 0; // nums index, start from 0
            
            for (int count = 1; count < 1690; count++) {
                int nextUgly = Math.min(nums[p2] * 2, Math.min(nums[p3] * 3, nums[p5] * 5));
                nums[count] = nextUgly;
                
                if (nextUgly == nums[p2] * 2) p2++;
                if (nextUgly == nums[p3] * 3) p3++;
                if (nextUgly == nums[p5] * 5) p5++;
            }
            
        }
    }
    

    public static void main(String[] args) {
        T0264_UglyNumber2 sut = new T0264_UglyNumber2();

        System.out.println(sut.nthUglyNumber4(10)); //12
        System.out.println(sut.nthUglyNumber4(1689)); //2109375000
    }
    
}
