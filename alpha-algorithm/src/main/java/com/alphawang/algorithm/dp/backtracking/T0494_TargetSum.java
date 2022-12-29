package com.alphawang.algorithm.dp.backtracking;

import java.util.Arrays;

/**
 * 给定数组，对每个元素设置 正负号，使得总和 = target，有几种解法？
 */
public class T0494_TargetSum {

  public int findTargetSumWays(int[] nums, int target) {
    result = 0;
    backtrack(nums, 0, target);
    return result;
  }

  int result;
  private void backtrack(int[] nums, int i, int rest) {
    if (i == nums.length && rest == 0) {
      result++;
      return;
    }
    if (i >= nums.length) {
      return;
    }

    //选择一：+
    rest = rest - nums[i];
    backtrack(nums, i+1, rest);
    //恢复选择一
    rest = rest + nums[i];

    //选择二：-
    rest = rest + nums[i];
    backtrack(nums, i+1, rest);
    rest = rest - nums[i];
  }

  public static void main(String[] args) {
    T0494_TargetSum sut = new T0494_TargetSum();
    /*
    -1 + 1 + 1 + 1 + 1 = 3
    +1 - 1 + 1 + 1 + 1 = 3
    +1 + 1 - 1 + 1 + 1 = 3
    +1 + 1 + 1 - 1 + 1 = 3
    +1 + 1 + 1 + 1 - 1 = 3
     */
    sut.test(new int[]{1,1,1,1,1}, 3); //5
    sut.test(new int[]{1}, 1); //1
  }

  private void test(int[] nums, int target) {
    System.out.println(Arrays.toString(nums) + " to " + target + " --> " + findTargetSumWays(nums, target));
  }

}
