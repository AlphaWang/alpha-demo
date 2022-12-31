package com.alphawang.algorithm.stack;

import java.util.Arrays;

/**
 * 给定一个循环数组 nums，返回nums中每个元素的 下一个更大元素 。
 *
 */
public class T0503_NextGreaterElementII {

  public int[] nextGreaterElements(int[] nums) {

    return null;
  }

  public static void main(String[] args) {
    T0503_NextGreaterElementII sut = new T0503_NextGreaterElementII();
    sut.nextGreaterElements(new int[] {1,2,1}); // [2,-1,2]
    sut.nextGreaterElements(new int[] {1,2,3,4,3}); // [2,3,4,-1,4]
  }

  private void test(int[] nums) {
    System.out.println(Arrays.toString(nums) + " --> "
        + Arrays.toString(nextGreaterElements(nums)));
  }

}
