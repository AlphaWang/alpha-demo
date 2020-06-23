package com.alphawang.algorithm.recursion;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
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
     * 1. 递归，回溯：从原始数组开始，交换元素位置 
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

    /**
     * 2. 回溯，递归树：从空列表开始，依次放入
     * 
     *   >    - depth: 递归到第几层，即拼接到结果数组中的第几个元素 
     *   >    - path: 已经选择了哪些数 (Stack)
     *   >    - used: boolean[]，已经选择的数
     */
    public List<List<Integer>> permute2(int[] nums) {
        List<List<Integer>> res = new LinkedList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }
        
        int len = nums.length;
        Deque<Integer> path = new ArrayDeque<>(); // Stack
        boolean[] used = new boolean[len];
        dfs2(nums, len, 0, path, used, res);
        
        return res;
    }

    private void dfs2(int[] nums, int len, int depth, Deque<Integer> path, boolean[] used, List<List<Integer>> res) {
        // terminator
        if (depth == len) {
            System.out.println("added " + path);
            res.add(new ArrayList<>(path));
            return;
        }
        
        for (int i = 0; i < len; i++) {
            if (used[i]) {
                System.out.println(String.format("%s - %s, ignore %s", depth, nums[i], nums[i]));
                continue;
            }
            
            // process current
            path.addLast(nums[i]);
            used[i] = true;

            // drill down
            System.out.println(String.format("%s + %s, %s", depth, nums[i], path));
            dfs2(nums, len, depth + 1, path, used, res);
            
            // reverse
            used[i] = false;
            path.removeLast();
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
        System.out.println(sut.permute2(new int[]{1,2,3}));
    }

}
