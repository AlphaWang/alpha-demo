package com.alphawang.algorithm.array;

import com.google.common.collect.Lists;
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
public class T0056_MergeIntervals_0830 {

    private List<Tuple<Integer, Integer>> merge(List<Tuple<Integer, Integer>> input) {
        List<Tuple<Integer, Integer>> result = Lists.newArrayList();
        Tuple<Integer, Integer> start = input.get(0);
        for(int i = 1; i< input.size(); i++) {
            Tuple<Integer, Integer> elem = input.get(i);
            if (start.f1 > elem.f0) {
                Tuple<Integer, Integer> res1 = new Tuple<>(start.f0, elem.f1);
                start = res1;
            } else {
                result.add(start);
                start = elem;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        /*
         * Input: [[1,3],[2,6],[8,10],[15,18]]
         * Output: [[1,6],[8,10],[15,18]]
         */
        test(new int[][]{{1,3},{2,6},{8,10},{15,18}});
        /*
         * Input: [[1,4],[4,5]]
         * Output: [[1,5]]
         */
//        test(new int[][]{{1,4},{4,5}});
//        // [1, 4]
//        test(new int[][]{{1,4},{2,3}});
//        // empty
//        test(new int[][]{});
    }

    private static void test(int[][] intervals) {
        List<Tuple<Integer, Integer>> input = new ArrayList<>();
        for (int i = 0; i < intervals.length; i++) {
            Tuple t = new Tuple(intervals[i][0], intervals[i][1]);
            input.add(t);
        }
        List<Tuple<Integer, Integer>> output = new T0056_MergeIntervals_0830().merge(input);
        System.out.println(Arrays.deepToString(intervals));
        System.out.println("-->");
        System.out.println(output);
        System.out.println();
    }

    private static class Tuple<L, R> {
        L f0;
        R f1;


        public Tuple(L f0, R f1) {
          this.f0 = f0;
          this.f1 = f1;
        }
    }
}
