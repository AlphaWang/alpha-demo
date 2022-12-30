package com.alphawang.algorithm.dp.knapsack;

import java.util.Arrays;

/**
 * 给你一个可装载重量为 W 的背包和 N 个物品，每个物品有重量和价值两个属性。
 * 其中第 i 个物品的重量为 wt[i]，价值为 val[i]。
 *
 * 现在让你用这个背包装物品，最多能装的价值是多少？
 */
public class T_BasicKnapsack {

  public int knapsack(int W, int N, int[] wt, int[] val) {
    return dp(0,W, N, wt, val);
  }

  /**
   * DP
   * i = 是否放入第i个物品；
   * W = 背包容量
   * dp[i]: 最大价值
   */
  public int dp(int i, int W, int N, int[] wt, int[] val) {
    if (i >= N) {
      return 0;
    }
    if (W < 0) {
      return 0;
    }
    if (wt[i] > W) {
      return dp(i + 1, W, N, wt, val); //不放入i
    }

    return Math.max(
        dp(i + 1, W, N, wt, val), //不放入i
        dp(i + 1, W - wt[i], N, wt, val) + val[i] //放入i
    );

  }

  public static void main(String[] args) {
    T_BasicKnapsack sut = new T_BasicKnapsack();
    sut.test(4, 3, new int[]{2,1,3}, new int[]{4, 2, 3}); //6, pick 4,2
  }

  private void test(int W, int N, int[] wt, int[] val) {
    System.out.println(String.format("wt=%s, val=%s, W=%s --> %s",
        Arrays.toString(wt), Arrays.toString(val), W, knapsack(W, N, wt, val)));
  }

}
