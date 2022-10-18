package com.alphawang.algorithm.stack;

import static com.alphawang.algorithm.Utils.print;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * https://leetcode.com/problems/daily-temperatures
 *
 * 给定一个整数数组 temperatures，表示每天的温度，
 * 返回一个数组 answer，其中 answer[i] 是指对于第 i 天，下一个更高温度出现在几天后。
 * 如果气温在这之后都不会升高，请在该位置用 0 来代替。
 *
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
      System.out.println(String.format("---- iter=%s", i));

      // 如果栈内元素比当前元素小，则清空
      // -- 大个子来了，之前的小个子无需存储
      while(!stack.isEmpty() && t[i] >= t[stack.peek()]) {
        stack.pop();
      }

      // 记录当前 i 位置的结果：栈顶到i的距离
      if (stack.isEmpty()) {
        res[i] = 0;
      } else {
        res[i] = stack.peek() - i;
      }
      stack.push(i);
      System.out.println(String.format("   stack = %s", stack));
      System.out.println(String.format("   res   = %s", Arrays.toString(res)));
    }

    return res;
  }

  public static void main(String[] args) {
    T0739_DailyTemperatures sut = new T0739_DailyTemperatures();

    int[] t = { 73, 74, 75, 71, 69, 72, 76, 73 }; // [1,1,4,2,1,1,0,0]
    print(sut.dailyTemperatures(t));
  }

}
