package com.alphawang.algorithm.dp.rob;

import java.util.Arrays;

/**
 * nums[i] 表示第i间房子中的现金数额，相邻房子里的钱不能同时取出；问最多取出多少钱？
 *
 * 限制：房子围成一个环形，首位两个房子也不能同时取出。
 */
public class T0213_HouseRobberII {

  public int rob(int[] nums) {
    int n = nums.length;
    if (n == 1) {
      return nums[0];
    }

    int sum1 = rob(nums, new Integer[n], n - 2, 0); // [0, n-2]
    System.out.println(String.format("--- rob [%s, %s] = %s", 0, n - 2, sum1));

    int sum2 = rob(nums, new Integer[n], n - 1, 1); // [1, n-1]
    System.out.println(String.format("--- rob [%s, %s] = %s", 1, n - 1, sum2));
    return Math.max(sum1, sum2);
  }

  private int rob(int[] nums, Integer[] memo, int end, int i) {
    if (i > end) {
      return 0;
    }
    if (memo[i] != null) {
      return memo[i];
    }

    return memo[i] = Math.max(rob(nums, memo, end, i+1),
        nums[i] + rob(nums, memo, end, i+2));
  }

  public static void main(String[] args) {
    T0213_HouseRobberII sut = new T0213_HouseRobberII();
    sut.test(new int[] {2,3,2}); //3
    sut.test(new int[] {1,2,3,1}); //4
    sut.test(new int[] {1,2,3}); //3
    sut.test(new int[] {1}); //1
  }

  private void test(int[] nums) {
    System.out.println(Arrays.toString(nums) + " --> " + rob(nums));
  }
}
