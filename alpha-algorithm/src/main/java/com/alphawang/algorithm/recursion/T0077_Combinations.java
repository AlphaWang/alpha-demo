package com.alphawang.algorithm.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *  https://leetcode.com/problems/combinations/
 *  Medium
 *  
 *  Given two integers n and k, return all possible combinations of k numbers out of 1 ... n.
 */
public class T0077_Combinations {

    /**
     * 1. 回溯，基于List
     *    19ms, 59%
     */
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        
        dfs(n, k, 1, res, new ArrayList<Integer>());
        
        return res;
    }

    private void dfs(int n, int k, int level, List<List<Integer>> res, List<Integer> current) {
        if (current.size() == k) {
            res.add(new ArrayList<>(current));
            return;
        }
        
        for (int i = level; i <= n; i++) {
            current.add(i);
            System.out.println("- " + current);
//            dfs(n, k, level + 1, res, current);  //TODO 层数需要动态变化，是 i+1，而非 level+1
            dfs(n, k, i + 1, res, current);
            current.remove(current.size() - 1);
        }
    }
    
    /**
     * 2. 回溯，基于数组，无需清除当前状态
     *    TODO Wrong...
     */
    public void backtrack(int n, int k, int level, List<List<Integer>> res, Integer[] curr) {
        // if the combination is done
        if (level > k) {
            res.add(new ArrayList<>(Arrays.asList(curr)));
            return;
        }
        

        for (int i = level; i < n + 1; ++i) {
            // add i into the current combination
            curr[level - 1] = i;
            System.out.println("- " + Arrays.toString(curr));
            // use next integers to complete the combination
            backtrack(n, k, i + 1, res, curr);
        }
    }

    public List<List<Integer>> combine2(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        backtrack(n, k, 1, res, new Integer[k]);
        return res;
    }


    public static void main(String[] args) {
        T0077_Combinations sut = new T0077_Combinations();
         /*
         Input: n = 4, k = 2
        Output:
        [
          [2,4],
          [3,4],
          [2,3],
          [1,2],
          [1,3],
          [1,4],
        ]
          */
        System.out.println(sut.combine2(4, 2));
    }
}
