package com.alphawang.algorithm.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * https://leetcode.com/problems/merge-intervals/
 * Medium
 * 
 * 给出一个区间的集合，请合并所有重叠的区间。
 */
public class T0056_MergeIntervals {

    /**
     * 1: 按首位排序，依次比较prev vs. curr
     *    18ms - 7%
     */
    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }
        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));
        List<int[]> res = new ArrayList<>();
        res.add(intervals[0]);
        int index = 0;
        for (int i = 1; i < intervals.length; i++) {
            int[] curr = intervals[i];
            int[] prev = res.get(index);
            
            if (prev[1] >= curr[1]) {
                // 前一个区间包含当前区间，跳过
                continue;
            } else if (prev[1] >= curr[0]) {
                // 有重合
                prev[1] = curr[1];
            } else {
                // 无重合
                res.add(curr);
                index++;
            }
        }
        return res.toArray(new int[res.size()][]);
    }

    /**
     * 2: 优化比较逻辑，去掉 List.get
     *    6ms - 71%
     */
    public int[][] merge2(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }
        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));
        List<int[]> res = new ArrayList<>();
        int[] newInterval = intervals[0];
        res.add(newInterval);
        
        for (int[] interval : intervals) {
            if (interval[0] > newInterval[1]) {
                newInterval = interval;
                res.add(newInterval);
            } else {
                newInterval[1] = Math.max(newInterval[1], interval[1]);
            }
        }
        
        return res.toArray(new int[res.size()][]);
    }

    public static void main(String[] args) {
        /*
         * Input: [[1,3],[2,6],[8,10],[15,18]]
         * Output: [[1,6],[8,10],[15,18]]
         */
        test(new int[][]{{2,6},{1,3},{8,10},{15,18}});
        /*
         * Input: [[1,4],[4,5]]
         * Output: [[1,5]]
         */
        test(new int[][]{{1,4},{4,5}});
        // [1, 4]
        test(new int[][]{{1,4},{2,3}});
        // empty
        test(new int[][]{});
        
    }

    private static void test(int[][] intervals) {
        System.out.println(Arrays.deepToString(intervals));
        System.out.println("-->");
        System.out.println(Arrays.deepToString(new T0056_MergeIntervals().merge2(intervals)));
    }
}
