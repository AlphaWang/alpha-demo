package com.alphawang.algorithm.dp.knapsack;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/partition-equal-subset-sum/
 *
 * 给定数组，判断是否能划分为两个子数组、确保元素和相等
 */
public class T0416_PartitionEqualSubsetSum {

  /**
   * 转化为：背包容量为sum/2，能否恰好将背包装满？
   */
  public boolean canPartition(int[] nums) {
    int sum = 0;
    for (int n : nums) {
      sum += n;
    }
    if (sum % 2 != 0) {
      return false;
    }
    memo = new Boolean[nums.length][sum/2 + 1];
    return dp(nums, nums.length - 1, sum/2);
  }

  /**
   * i = 前i个物品
   * j = 背包容量为j
   * dp[i, j] = 前i个物品可以装满容量为j的背包
   */
  Boolean[][] memo;
  private boolean dp(int[] nums, int i, int capacity) {
    if (capacity == 0) {
      return true;
    }
    if (capacity < 0 || i == 0) {
      return false;
    }

    if (memo[i][capacity] != null) {
      return memo[i][capacity];
    }
    if (nums[i] > capacity) {
      return memo[i][capacity] = dp(nums, i - 1, capacity); //过大，不放入第i个
    }

    return memo[i][capacity] =
        dp(nums, i - 1, capacity) //不放入第i个
        || dp(nums, i - 1, capacity - nums[i]); //放入第i个

  }

  public static void main(String[] args) {
    T0416_PartitionEqualSubsetSum sut = new T0416_PartitionEqualSubsetSum();
    sut.test(new int[]{1, 5, 11, 5}); //true, [1, 5, 5] & [11]
    sut.test(new int[]{1, 2, 3, 5}); //false

  }

  private void test(int[] nums) {
    System.out.println(Arrays.toString(nums) + " --> " + canPartition(nums));
  }
}
