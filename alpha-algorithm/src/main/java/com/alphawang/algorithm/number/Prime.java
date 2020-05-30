package com.alphawang.algorithm.number;

import com.google.common.base.Stopwatch;
import java.util.Arrays;

public class Prime {

    /**
     * 返回区间 [2, n) 中有几个素数 
     */

    /**
     * 1. 遍历，计算是否能整除
     */
    static int countPrimes1(int n) {
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (isPrime(i)) {
                count ++;
            }
        }
        
        return count;
    }

    static boolean isPrime(int n) {
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 2. 先假设全是素数；再剔除掉素数的倍数
     */
    static int countPrimes2(int n) {
        // 1. 初始化flag
        boolean[] flags = new boolean[n];
        Arrays.fill(flags, true);
        
        // 2. 剔除素数的倍数
        // 优化：只需要计算 2 ~ 根号n
        for (int i = 2; i * i < n; i++) {
            if (flags[i]) { //如果是素数，则计算倍数
                // 优化：避免重复设置，从 i平方开始
                for (int j = i * i; j < n; j += i) {
                    flags[j] = false;
                }
            }
        }

//        System.out.println("---" + Arrays.toString(flags));
        
        // 3. 统计 flag
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (flags[i]) {
                count ++;
            }
        }

        return count;
    } 
    
    
    public static void main(String[] args) {
        for (int i = 1; i < 20; i++) {
            System.out.println(String.format("%s - %s", i, isPrime(i)));
        }

        Stopwatch stopwatch = Stopwatch.createStarted();
        System.out.println(String.format("count from %s : %s", 1000, countPrimes1(1000)));
        stopwatch.stop();
        System.out.println(stopwatch);

        stopwatch.reset();
        stopwatch.start();
        System.out.println(String.format("count from %s : %s", 1000, countPrimes2(1000)));
        stopwatch.stop();
        System.out.println(stopwatch);
        
    }

}
