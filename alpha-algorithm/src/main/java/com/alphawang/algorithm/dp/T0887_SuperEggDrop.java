package com.alphawang.algorithm.dp;

/**
 *  https://leetcode.com/problems/super-egg-drop/
 *  Hard
 *  
 *  You are given K eggs, 
 *  and you have access to a building with N floors from 1 to N. 
 *  
 *  You know that there exists a floor F with 0 <= F <= N 
 *  such that any egg dropped at a floor higher than F will break, 
 *  and any egg dropped at or below floor F will not break.
 *  
 *  What is the minimum number of moves that you need to know with certainty what F is, 
 *  regardless of the initial value of F?
 */
public class T0887_SuperEggDrop {

    /**
     * 1. DP
     * 状态：1. K = 鸡蛋数, 2. N = 要测试的楼层数
     * 状态转移：在第i层扔，碎 or 没碎
     */
    int[][] memo;
    public int superEggDrop(int K, int N) {
        memo = new int[K + 1][N + 1];
        return dp(K, N);
    }
    
    private int dp(int K, int N) {
        // base case-1: 如果楼层=0，无需尝试
        if (N == 0) {
            return 0;
        }
        // base case-2: 如果只有一个鸡蛋，要遍历
        if (K == 1) {
            return N;
        }

        if (memo[K][N] > 0) {
            return memo[K][N];
        }

        int res = Integer.MAX_VALUE;
        for (int i = 1; i <= N; i++) {
            /**
             * 我们选择在第 i 层楼扔了鸡蛋之后，可能出现两种情况：鸡蛋碎了，鸡蛋没碎。
             *
             * 如果没碎，那么鸡蛋的个数 K 不变，搜索的楼层区间应该从 [1..N] 变为 [i+1..N] 共 N-i 层楼。
             * 如果碎了，那么鸡蛋的个数 K 应该减一，搜索的楼层区间应该从 [1..N] 变为 [1..i-1] 共 i-1 层楼；
             */
            int move = Math.max(dp(K, N - i),  //没碎: 往上找 N-i 层楼
                                dp(K - 1, i - 1)) // 碎了：往下找 i-1 层楼
                       + 1;
            res = Math.min(res, move);
        }

        memo[K][N] = res;
        
        return res;
    }

    /**
     * 1. DP + 二分 
     */
    public int superEggDrop2(int K, int N) {
        int[][] memo = new int[K + 1][N + 1];
        return dp2(K, N, memo);
    }

    private int dp2(int K, int N, int[][] memo) {
        if (K == 1) return N;
        if (N == 0) return 0;
        if (memo[K][N] > 0) return memo[K][N];
        
        int res = Integer.MAX_VALUE;
        int low = 1, high = N;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int broken = dp2(K - 1, mid - 1, memo);
            int notBroken = dp2(K, N - mid, memo);
            if (broken > notBroken) {
                high = mid - 1;
                res = Math.min(res, broken + 1);
            } else {
                low = mid + 1;
                res = Math.min(res, notBroken + 1);
            }
        }
        
        memo[K][N] = res;
        return res;
    }


    public static void main(String[] args) {
        T0887_SuperEggDrop sut = new T0887_SuperEggDrop();
        sut.test(1, 2); //2
        sut.test(2, 6); //3
        sut.test(3, 14); //4
    }

    private void test(int K, int N) {
        System.out.println(String.format("K=%s, N=%s --> %s", K, N, superEggDrop(K, N)));
    }

}
