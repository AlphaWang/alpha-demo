package com.alphawang.algorithm.dp.backtracking;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 排列
 *
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
     *    1ms - 92%
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

    /**
     * 2_2. 回溯，递归树：从空列表开始，依次放入 --> 优化，无需专门记录 used[]
     *    1ms - 92%
     *
     *   >    - depth: 递归到第几层，即拼接到结果数组中的第几个元素 
     *   >    - path: 已经选择了哪些数 (Stack)--> 同时用于判断使用了哪些数
     */
    public List<List<Integer>> permute2_2(int[] nums) {
        List<List<Integer>> res =  new ArrayList<>();
        Deque<Integer> path = new LinkedList<>();
        dfs2_2(nums, 0, path, res);
        
        return res;
    }

    private void dfs2_2(int[] nums, int depth, Deque<Integer> path, List<List<Integer>> res) {
        // 满足条件，则记录
        if (depth == nums.length) {
            res.add(new ArrayList<>(path));
            return;
        }
        
        // 遍历可选列表
        for (int num : nums) {
            if (path.contains(num)) {
                continue;
            }
            
            // 做选择
            path.addLast(num);
            // drill down
            dfs2_2(nums, depth + 1, path, res);
            // 撤销选择
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
        System.out.println(sut.permute2_2(new int[]{1,2,3}));
    }

}
