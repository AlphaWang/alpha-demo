package com.alphawang.algorithm.recursion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * https://leetcode.com/problems/subsets/
 * Medium
 */
public class T0078_Subsets {

    /**
     * 1. 对 nums 依次删除元素
     *    Time Limit Exceeded
     */
    public List<List<Integer>> subsets(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }
        
        Set<Set<Integer>> res = new HashSet<>();
        
        List<Integer> baseRes = IntStream.of(nums).mapToObj(Integer::new).collect(Collectors.toList());
        res.add(new HashSet<>(baseRes));
        
        List<List<Integer>> prevRes = new ArrayList<>();
        prevRes.add(baseRes);
        
        // remove i elements from nums
        for (int i = 1; i <= nums.length; i++) {
            // remove one 
            List<List<Integer>> curRes = new ArrayList<>();
            for (List<Integer> prevResItem : prevRes) {
                for (int j = prevResItem.size() - 1; j >=0; j--) {
                    List<Integer> tmp = new ArrayList<>(prevResItem);
                    tmp.remove(j);
                    curRes.add(tmp);
                }
            }
            res.addAll(curRes.stream().map(list -> new HashSet<>(list)).collect(Collectors.toSet()));
            prevRes = curRes;
        }
        return res.stream().map(list -> new ArrayList<>(list)).collect(Collectors.toList());
    }

    /**
     * 2. 从空数组开始，依次考虑数组每个元素
     *    0ms - 100%
     */
    public List<List<Integer>> subsets2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }

        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<>());
        for (int num : nums) {
            List<List<Integer>> resSnapshot = new ArrayList<>(res);  //res; ConcurrentModificationException
            for (List<Integer> existing : resSnapshot) {
                List<Integer> tmp = new ArrayList<>(existing);
                tmp.add(num);
                res.add(tmp);
            }
        }
        
        return res;
    }

    /**
     * 2. 从空数组开始，依次考虑数组每个元素；调整代码
     */
    public List<List<Integer>> subsets2_1(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }

        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<>());
        
        for (int num : nums) {
            List<List<Integer>> curRes = new ArrayList<>();
            for (List<Integer> existing : res) {
                List<Integer> tmp = new ArrayList<>(existing);
                tmp.add(num);
                curRes.add(tmp);
            }
            System.out.println(String.format("%s : %s", num, curRes));
            res.addAll(curRes);
        }

        return res;
    }

    /**
     * 3. DFS: 针对每个元素，选 or 不选
     *    1ms - 61%
     */
    public List<List<Integer>> subsets3(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }
        
        dfs2(res, nums, new ArrayList<>(), 0);
        
        return res;
    }
    
    // index: 层数，即当前考虑到第几个元素
    private void dfs(List<List<Integer>> res, int[] nums, List<Integer> list, int index) {
        // terminator
        if (index == nums.length) {
            res.add(new ArrayList<>(list));
            return;
        }

        System.out.println(String.format("%s - %s", index, list));
        
        // not pick element at index
        dfs(res, nums, list, index + 1);
        
        // pick element at index
        list.add(nums[index]);
        dfs(res, nums, list, index + 1);
        // reverse current state
        list.remove(list.size() - 1);
        
        System.out.println(String.format("remove last: %s - %s", index, list));
    }

    // 如果不想reverse state，则需要传递list的时候拷贝
    private void dfs2(List<List<Integer>> res, int[] nums, List<Integer> list, int index) {
        // terminator
        if (index == nums.length) {
            res.add(new ArrayList<>(list));
            return;
        }

        System.out.println(String.format("%s - %s", index, list));

        // not pick element at index
        dfs(res, nums, new ArrayList<>(list), index + 1);
        // pick element at index
        list.add(nums[index]);
        dfs(res, nums, new ArrayList<>(list), index + 1);
        // reverse current state
//        list.remove(list.size() - 1);

        System.out.println(String.format("remove last: %s - %s", index, list));
    }

    public static void main(String[] args) {
        T0078_Subsets sut = new T0078_Subsets();

        /*
        [
          [3],
          [1],
          [2],
          [1,2,3],
          [1,3],
          [2,3],
          [1,2],
          []
        ]
         */
        System.out.println(sut.subsets3(new int[]{1 ,2 ,3}));
    }
    
}
