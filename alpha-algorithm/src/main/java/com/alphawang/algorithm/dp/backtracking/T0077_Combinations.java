package com.alphawang.algorithm.dp.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 组合
 * 类似 78-子集问题，只不过只需递归到 第K层 即可
 *
 *  https://leetcode.com/problems/combinations/
 *  Medium
 *  
 *  Given two integers n and k, 
 *  return all possible combinations of k numbers out of 1 ... n.
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
            System.out.println("added " + current);
            res.add(new ArrayList<>(current));
            return;
        }
        
        for (int i = level; i <= n; i++) {
            current.add(i);
            System.out.println(String.format("%s - %s", level, current));
            
            /*
              TODO 层数需要动态变化，是 i+1，而非 level+1
              否则会有重复元素：
              [[1, 2], [1, 3], [1, 4], [2, 2], [2, 3], [2, 4], [3, 2], [3, 3], [3, 4], [4, 2], [4, 3], [4, 4]] 
             */
//            dfs(n, k, level + 1, res, current); 
            
            /*
            [[1, 2], [1, 3], [1, 4], [2, 3], [2, 4], [3, 4]]
             */
            dfs(n, k, i + 1, res, current);
            current.remove(current.size() - 1);
        }
    }
    
    /**
     * 2. 回溯，基于数组，无需清除当前状态
     *    TODO Wrong...
     */
    public List<List<Integer>> combine2(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        backtrack(n, k, 1, res, new Integer[k]);
        return res;
    }
    
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

    /**
     * 3. 神仙解法 ？？
     *    TODO 没看懂
     */
    public List<List<Integer>> combine3(int n, int k) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if (k > n || k < 0) {
            return result;
        }
        if (k == 0) {
            result.add(new ArrayList<Integer>());
            System.out.println(String.format("1. %s - %s", k, result));
            return result;
        }
        result = combine3(n - 1, k - 1);
        for (List<Integer> list : result) {
            list.add(n);
        }
        System.out.println(String.format("2. %s - %s", k, result));
        
        result.addAll(combine3(n - 1, k));
        System.out.println(String.format("3. %s - %s", k, result));
        return result;
    }

    /**
     * 4. 迭代
     *    TODO 
     */
    public List<List<Integer>> combine4(int n, int k) {
        if (k == 0 || n == 0 || k > n) return Collections.emptyList();
        List<List<Integer>> combs = new ArrayList<>();
        for (int i = 1; i <= n; i++) combs.add(Arrays.asList(i));
        System.out.println("init: " + combs);
        
        for (int i = 2; i <= k; i++) {
            List<List<Integer>> newCombs = new ArrayList<>();
            for (int j = i; j <= n; j++) {
                for (List<Integer> comb : combs) {
                    if (comb.get(comb.size()-1) < j) {
                        List<Integer> newComb = new ArrayList<>(comb);
                        newComb.add(j);
                        newCombs.add(newComb);
                    }
                }
            }
            combs = newCombs;
        }
        return combs;
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
        System.out.println(sut.combine4(4, 2));
    }
}
