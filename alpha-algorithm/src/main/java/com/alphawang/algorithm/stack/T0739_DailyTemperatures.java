package com.alphawang.algorithm.stack;

import static com.alphawang.algorithm.Utils.print;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * https://leetcode.com/problems/daily-temperatures
 *
 * Given an array of integers temperatures represents the daily temperatures,
 * return an array answer such that answer[i] is the number of days you have to wait after the ith day to get a warmer temperature.
 *
 * If there is no future day for which this is possible, keep answer[i] == 0 instead.
 */
public class T0739_DailyTemperatures {

  /**
   * 单调栈：栈元素表示比当前元素大的所有右侧元素索引
   */
  public int[] dailyTemperatures(int[] t) {
    int n = t.length;
    int[] res = new int[n];

    Deque<Integer> stack = new ArrayDeque<>();
    for (int i = n - 1; i >= 0; i--) {
      while(!stack.isEmpty() && t[i] >= t[stack.peek()]) {
        stack.pop();
      }
      if (stack.isEmpty()) {
        res[i] = 0;
      } else {
        res[i] = stack.peek() - i;
      }
      stack.push(i);
    }

    return res;
  }

  public static void main(String[] args) {
    T0739_DailyTemperatures sut = new T0739_DailyTemperatures();

    int[] t = { 73, 74, 75, 71, 69, 72, 76, 73 }; // [1,1,4,2,1,1,0,0]
    print(sut.dailyTemperatures(t));
  }

}
