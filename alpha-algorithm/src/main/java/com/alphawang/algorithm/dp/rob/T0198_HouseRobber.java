package com.alphawang.algorithm.dp.rob;

import java.util.Arrays;

/**
 * nums[i] 表示第i间房子中的现金数额，相邻房子里的钱不能同时取出；问最多取出多少钱？
 */
public class T0198_HouseRobber {

  /**
   * DP:
   * 状态：房子索引
   * 选择：取钱 or 不取钱
   */
  public int rob(int[] nums) {
    memo = new Integer[nums.length];
    return dp(nums, 0);
  }

  Integer[] memo;
  private int dp(int[] nums, int i) {
    if (i >= nums.length) {
      return 0;
    }
    if (memo[i] != null) {
      return memo[i];
    }

    int sum1 = dp(nums, i + 1);
    int sum2 = nums[i] + dp(nums, i + 2);
    return memo[i] = Math.max(sum1, sum2);
  }

  public static void main(String[] args) {
    T0198_HouseRobber sut = new T0198_HouseRobber();
    sut.test(new int[]{1,2,3,1}); //4 = 1 + 3
    sut.test(new int[]{2,7,9,3,1}); //12 = 2 + 9 + 1

  }

  private void test(int[] nums) {
    System.out.println(Arrays.toString(nums) + " --> " + rob(nums));
  }


}
