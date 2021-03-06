package com.alphawang.algorithm.dp;

/**
 * https://leetcode.com/problems/decode-ways/
 * Medium
 * 
 * 一条包含字母 A-Z 的消息通过以下方式进行了编码：
 *   'A' -> 1
 *   'B' -> 2
 *   ...
 *   'Z' -> 26
 * 
 * 给定一个只包含数字的非空字符串，请计算解码方法的总数。
 */
public class T0091_DecodeWays {

    /**
     * 1. DP
     *    状态：dp[i] 表示从 0~i 个元素的编码总数
     *    方程：
     *      - 如果当前元素为0：
     *          - 如果"能"和前一个数组成字母（prev == 1 or 2 ）：dp[i] = dp[i-2]
     *          - 如果"不能"和前一个数组成字母：                 dp[i] = 0 
     *      - 如果"能"和前一个数组成字母：  dp[i] = dp[i-1] + dp[i-2] // 单独构成字母  +  与前一个数组成字母
     *      - 如果"不能"和前一个数组成字母：dp[i] = dp[i-1]           // 单独构成字母
     *      
     *    6ms - 15%
     */
    public int numDecodings(String s) {
        if (s == null || s.charAt(0) == '0') return 0;
        
        int[] nums = s.chars().map(x -> x - '0').toArray();
        
        int length = nums.length;
        int[] dp = new int[length + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= length; i++) {
            int curr = nums[i - 1];
            int prev = nums[i - 2];
            int comp = prev * 10 + curr;
            if (curr == 0) {
                // 当前为0，且能和前一个组成字母
                if (prev == 1 || prev == 2) {
                    dp[i] = dp[i-2];
                } 
                // 当前为0，但不能和前一个组成字母 
                else {
                    dp[i] = 0;
                }
            } else {
                // 如果能和前一个组成字母
                if (comp >= 11 && comp <= 26) {
                    dp[i] = dp[i-1] + dp[i-2];
                } 
                // 如果不能和前一个组成字母
                else {
                    dp[i] = dp[i-1];
                }
            }
            
            // 性能提升几乎没有
            if (dp[i] == 0) {
                return 0;
            }
        }
        return dp[length];
    }

    /**
     * 优化 DP 方程
     *   >  1 如果当前元素 [1, 9]:   dp[i] = dp[i - 1]
     *   >  2 如果最近两位 [10, 26]: dp[i] += dp[i - 2]
     */
    public int numDecodings2(String s) {
        int[] nums = s.chars().map(x -> x - '0').toArray();
        int m = nums.length;
        int[] dp = new int[m + 1];

        dp[0] = 1;
        dp[1] = nums[0] == 0 ? 0 : 1;
        for (int i = 2; i <= m; i++) {
            int curr = nums[i - 1];
            if (curr >= 1 && curr <= 9) {
                dp[i] = dp[i - 1];
            }

            int prev = nums[i - 2];
            int comp = prev * 10 + curr;
            if (comp >= 10 && comp <= 26) {
                dp[i] += dp[i - 2];
            }
        }
        
        return dp[m];
    }
    
    public static void main(String[] args) {
        /*
         * 输入: "12"
         * 输出: 2
         * 解释: 它可以解码为 "AB"（1 2）或者 "L"（12）。
         */
        test("12");

        /*
         * 输入: "226"
         * 输出: 3
         * 解释: 它可以解码为 "BZ" (2 26), "VF" (22 6), 或者 "BBF" (2 2 6) 。
         */
        test("226");

        // 0
        test("0");
        // 1
        test("10"); 
        // 0 
        test("012");
        // 0 
        test("100");
        // 1
        test("110");
    }
    
    private static void test(String s) {
        T0091_DecodeWays sut = new T0091_DecodeWays();
        System.out.println(s + " --> " + sut.numDecodings2(s));
    }

}
