package com.alphawang.algorithm.recursion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *  https://leetcode.com/problems/permutations/
 *  Medium 
 *  
 *  Given a collection of distinct integers, return all possible permutations.
 */
public class T0046_Permutations {

    /**
     * 1. 递归，回溯 
     *    2ms - 50%
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new LinkedList<>();
        List<Integer> output = IntStream.of(nums).boxed().collect(Collectors.toList());
        
        dfs(nums.length, res, output, 0);
        
        return res;
    }
    
    private void dfs(int n, List<List<Integer>> res, List<Integer> output, int level) {
        if (level == n) {
            res.add(new ArrayList<>(output));
            return;
        }
        
        for (int i = level; i < n; i++) {
            // 动态维护数组
            Collections.swap(output, level, i);
            System.out.println(String.format("  set %s+%s - %s", level, i, output));
            // 继续递归填下一个数
            dfs(n, res, output, level + 1); //TODO level + 1，而非 i+1
            // 撤销操作
            Collections.swap(output, level, i);
            System.out.println(String.format("reset %s+%s - %s", level, i, output));
        }
    }

    public static void main(String[] args) {
        T0046_Permutations sut = new T0046_Permutations();
        /*
        Input: [1,2,3]
        Output:
        [
          [1,2,3],
          [1,3,2],
          [2,1,3],
          [2,3,1],
          [3,1,2],
          [3,2,1]
        ]
         */
        System.out.println(sut.permute(new int[]{1,2,3}));
    }

}
