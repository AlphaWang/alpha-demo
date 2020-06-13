package com.alphawang.algorithm.dp;

import java.util.Arrays;
import java.util.List;

public class T0120_Triangle {

    /**
     *  1. 递归：DFS 
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
    

    public static void main(String[] args) {
        T0120_Triangle sut = new T0120_Triangle();
        
        int res = sut.minimumTotal(Arrays.asList(
          Arrays.asList(    2),
          Arrays.asList(  3, 4),
          Arrays.asList( 6, 5, 7),
          Arrays.asList(4, 1, 8, 3)
        ));
        System.out.println(res); //11
    }

}
