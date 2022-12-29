package com.alphawang.algorithm.dp.coin;

import java.util.Arrays;

/**
 * 给定硬币币值数组，要凑出总额 amount 最多共有多少种方法？
 */
public class T0518_CoinChangeII {

  public int change(int amount, int[] coins) {
    memo = new Integer[coins.length][amount + 1];
    return dp(coins, coins.length - 1, amount);
  }

  /**
   * dp[i, j] = 只使用前 i 个硬币币值，凑出总额 j 的方法数目。
   */
  Integer[][] memo;
  private int dp(int[] coins, int i, int amount) {
    if (amount == 0) {
      return 1;
    }
    if (amount < 0 || i < 0) { //注意 i < 0 而非 i == 0
      return 0;
    }

    if (memo[i][amount] != null) {
      return memo[i][amount];
    }
    if (coins[i] > amount) {
      // return 0; --> NO
      return memo[i][amount] = dp(coins, i - 1, amount);
    }

    int sum1 = dp(coins, i - 1, amount); //不用第i个
    int sum2 = dp(coins, i, amount - coins[i]); //用第i个：注意无需 i-1，因为可以重复使用当前币值
    return memo[i][amount] = sum1 + sum2;
  }

  public static void main(String[] args) {
    T0518_CoinChangeII sut = new T0518_CoinChangeII();
    /*
    5=5
    5=2+2+1
    5=2+1+1+1
    5=1+1+1+1+1
     */
    sut.test(5, new int[]{1, 2, 5}); // 4
    sut.test(3, new int[]{2}); // 0
    sut.test(10, new int[]{10}); // 1
  }

  private void test(int amount, int[] coins) {
    System.out.println(Arrays.toString(coins) + " to " + amount + " --> " + change(amount, coins));
  }

}
