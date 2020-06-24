package com.alphawang.algorithm.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * https://leetcode.com/problems/permutations-ii/
 * Medium
 * 
 * Given a collection of numbers that might contain duplicates, 
 * return all possible unique permutations.
 */
public class T0047_Permutations2 {

    /**
     * 1. 回溯，基于46，依靠Set去重
     *    30ms - 18%
     */
    public List<List<Integer>> permuteUnique(int[] nums) {
        Set<List<Integer>> res = new HashSet<>();
        List<Integer> output = IntStream.of(nums).boxed().collect(Collectors.toList());
        dfs(nums.length, 0, res, output);
        return res.stream().collect(Collectors.toList());
    }

    private void dfs(int length, int level, Set<List<Integer>> res, List<Integer> output) {
        if (level == length) {
            res.add(new ArrayList<>(output));
            return;
        }
        
        for (int i = level; i < length; i++) {
            Collections.swap(output, i, level);
            dfs(length, level + 1, res, output);
            Collections.swap(output, i, level);
        }
    }

    /**
     * 2. 回溯，基于46
     *    回溯，递归树：从空列表开始，依次放入
     *    1ms - 99%
     */
    public List<List<Integer>> permuteUnique2(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Deque<Integer> path = new LinkedList<>();
        boolean[] used = new boolean[nums.length];

        Arrays.sort(nums); // 先排序，方便去除重复值
        dfs2(nums, nums.length, 0, used, path, res);
        
        return res;
    }

    private void dfs2(int[] nums, int length, int depth, boolean[] used, Deque<Integer> path, List<List<Integer>> res) {
        // terminator
        if (depth == length) {
            System.out.println("added " + path);
            res.add(new ArrayList<>(path));
            return;
        }
        
        for (int i = 0; i < length; i++) {
            if (used[i]) {
                System.out.println(String.format("%s - nums[%s], %s ignore-A %s", depth, i, path, nums[i]));
                continue;
            }
            // TODO 剪枝，去掉重复结果
            // https://leetcode-cn.com/problems/permutations-ii/solution/hui-su-suan-fa-python-dai-ma-java-dai-ma-by-liwe-2/
            if (i > 0 && nums[i] == nums[i-1] // 与前一元素重复
//              && used[i - 1] == true) {     // 并且前一元素已使用? --> 不合理
              && used[i - 1] == false) {      // 并且nums[i - 1] 在深度优先遍历的过程中刚刚被撤销选择 
                System.out.println(String.format("%s - nums[%s], %s ignore-B %s", depth, i, path, nums[i]));
                continue;
            }
            
            path.addLast(nums[i]);
            used[i] = true;

            System.out.println(String.format("%s + nums[%s] %s, %s", depth, i, nums[i], path));
            dfs2(nums, length, depth + 1, used, path, res);
            
            used[i] = false;
            path.removeLast();
        }
    }


    public static void main(String[] args) {
        T0047_Permutations2 sut = new T0047_Permutations2();

        /*
        Input: [1,1,2]
        Output:
        [
          [1,1,2],
          [1,2,1],
          [2,1,1]
        ]
         */
        System.out.println(sut.permuteUnique2(new int[]{1,2,1}));
    }
    
}
