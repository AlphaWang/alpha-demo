package com.alphawang.algorithm.dp;

import java.util.Map;

/**
 * https://leetcode.com/problems/climbing-stairs/
 * Easy
 */
public class T0070_ClimbingStairs {

    /**
     * 1. 直接递归
     *    Time Limit Exceeded
     */
    public int climbStairs(int n) {
        
        return climbStairs(n - 1) + climbStairs(n - 2);
    }

    /**
     * 2. 递归 + 缓存
     *    0ms
     */
    public int climbStairs2(int n) {
//        return _climbStairs2(n, new HashMap<>());
        return _climbStairs2_1(n, new int[n + 1]);
    }
    
    // 或可用 int[] cache
    private int _climbStairs2(int n, Map<Integer, Integer> cache) {
        if (cache.containsKey(n)) {
            return cache.get(n);
        }
        
        if (n == 1) return 1;
        if (n == 2) return 2; 
        int res = _climbStairs2(n - 1, cache) + _climbStairs2(n - 2, cache);
        cache.put(n, res);
        
        return res;
    }

    private int _climbStairs2_1(int n, int[] cache) {
        if (cache[n] > 0) {
            return cache[n];
        }

        if (n == 1) return 1;
        if (n == 2) return 2;
        int res = _climbStairs2_1(n - 1, cache) + _climbStairs2_1(n - 2, cache);
        cache[n] = res;

        return res;
    }

    /**
     * 3. 迭代
     *    0ms
     */
    public int climbStairs3(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;
        
        int p1 = 1, p2 = 2, res = 0;
        for (int i = 3; i <= n; i++) {
            res = p1 + p2;
            p1 = p2;
            p2 = res;
        }
        
        return res;
    }

    /**
     * 4. DP
     *    状态： dp[n] 登到第n级台阶的方法数
     *    状态转移方程： dp[n] = dp[n-1] + dp[n-2]
     */
    public int climbStairs4(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;

        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
            
        return dp[n];
    }

    
    public static void main(String[] args) {
        T0070_ClimbingStairs sut = new T0070_ClimbingStairs();

        System.out.println(sut.climbStairs4(2)); // 2
        System.out.println(sut.climbStairs4(3)); // 3
    }
}
