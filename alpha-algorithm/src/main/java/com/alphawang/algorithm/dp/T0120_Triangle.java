package com.alphawang.algorithm.dp;

import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.com/problems/triangle/
 * Medium
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
            for (int index = triangle.get(level).size() - 1; index >= 0; index--) {
                int min = Math.min(triangle.get(level+1).get(index), 
                                   triangle.get(level+1).get(index + 1));
                triangle.get(level).set(index, min + triangle.get(level).get(index));
            }
            System.out.println(String.format("level %s: %s", level, triangle.get(level)));
        }
        return triangle.get(0).get(0);
    }
    

    public static void main(String[] args) {
        T0120_Triangle sut = new T0120_Triangle();
        
        int res = sut.minimumTotal2(Arrays.asList(
          Arrays.asList(    2),
          Arrays.asList(  3, 4),
          Arrays.asList( 6, 5, 7),
          Arrays.asList(4, 1, 8, 3)
        ));
        System.out.println(res); //11
    }

}
