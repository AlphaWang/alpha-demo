package com.alphawang.algorithm.dp;

import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.com/problems/triangle/
 * Medium
 * 
 * 给定一个三角形，找出自顶向下的最小路径和。每一步只能移动到下一行中相邻的结点上。
 * 相邻的结点 在这里指的是 下标 与 上一层结点下标 相同或者等于 上一层结点下标 + 1 的两个结点。
 *
 */
public class T0120_Triangle {

    /**
     *  1. 递归：DFS 
     *  
     *  执行超时
     */
    public int minimumTotal(List<List<Integer>> triangle) {
        if (triangle == null || triangle.size() == 0)
            return 0;
        
        return dfs(triangle, 0, 0, "");
    }
    
    private int dfs(List<List<Integer>> triangle, int level, int index, String path) {
        // terminator
        int curr = triangle.get(level).get(index);
        if (level == triangle.size() - 1) {
            path += curr + " # ";
            System.out.println(path);
            return curr;
        }
        
        // process
        path += curr + " -> ";
        
        // drill down
        int leftRes = dfs(triangle, level + 1, index, path);
        int rightRes = dfs(triangle, level + 1, index + 1, path);
        
        // clear status
        // ...
        
        // 或者可将 sum 作为参数传给 dfs()
        return Math.min(leftRes, rightRes) + curr;
    }

    /**
     *  2. DP : 两层循环： for i m-1 --> 0, for j 
     *     37 ms
     *  
     *   >    状态定义：dp[i, j]，从底走到(i, j) 路径和的最小值 
     *   >    状态转移方程：dp[i, j] = min(dp[i+1, j], dp[i+1, j+1]) + triangle[i, j]
     *   
     *   >    起始状态：dp[m-1, j] = triangle[m-1, j]
     *   >    结果值：dp[0, 0]
     *   
     *   >  优化：状态存储无需二维，只需一位数组存储当前层的min
     */
    public int minimumTotal2(List<List<Integer>> triangle) {
        if (triangle == null || triangle.size() == 0)
            return 0;
        
        for (int level = triangle.size() - 2; level >= 0; level--) {
            List<Integer> currLevel = triangle.get(level);
            for (int index = currLevel.size() - 1; index >= 0; index--) {
                List<Integer> nextLevel = triangle.get(level + 1);
                // 当前节点 --> 下一层的 最小path
                int min = Math.min(nextLevel.get(index),
                                   nextLevel.get(index + 1));
                currLevel.set(index, min + currLevel.get(index));
            }
            System.out.println(String.format("level %s: %s", level, triangle.get(level)));
        }
        return triangle.get(0).get(0);
    }
    

    public static void main(String[] args) {
        T0120_Triangle sut = new T0120_Triangle();

        /*
         * 例如，给定三角形：
         *
         * [
         *      [2],
         *     [3,4],
         *    [6,5,7],
         *   [4,1,8,3]
         * ]
         * 自顶向下的最小路径和为 11（即，2 + 3 + 5 + 1 = 11）。
         */
        int res = sut.minimumTotal2(Arrays.asList(
          Arrays.asList(    2),
          Arrays.asList(  3, 4),
          Arrays.asList( 6, 5, 7),
          Arrays.asList(4, 1, 8, 3)
        ));
        System.out.println(res); //11
    }

}
