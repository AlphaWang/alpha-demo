package com.alphawang.algorithm.stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

/**
 * 给你两个 没有重复元素 的数组 nums1 和 nums2 ，下标从 0 开始计数，其中nums1 是 nums2 的子集。
 *
 * nums1 中数字 x 的 下一个更大元素 是指 x 在 nums2 中对应位置 右侧 的 第一个 比 x 大的元素。
 */
public class T0496_NextGreaterElement {

  /**
   * N2 两轮遍历
   */
  public int[] nextGreaterElement(int[] nums1, int[] nums2) {
    int[] res = new int[nums1.length];
    int tmp;
    for (int i = 0; i < nums1.length; i++) {
      tmp = -1;
      for (int j = nums2.length - 1; j >= 0; j--) {
        if (nums2[j] == nums1[i]) {
          break;
        }
        if (nums2[j] > nums1[i]) {
          tmp = nums2[j];
        }
      }
      res[i] = tmp;
    }

    return res;
  }

  /**
   * 单调栈：递减
   */
  public int[] nextGreaterElement2(int[] nums1, int[] nums2) {
    Map<Integer, Integer> map = new HashMap<>();
//    Deque<Integer> stack = new ArrayDeque<>();
    Stack<Integer> stack = new Stack<>();
    for (int i = 0; i < nums2.length; i++) {
      int num = nums2[i];
      while (!stack.isEmpty() && stack.peek() < num) {
        System.out.println(String.format("(%s) mapping %s -> %s", i, stack.peek(), num));
        map.put(stack.pop(), num);
      }
      stack.push(num);
      System.out.println(String.format("(%s) stack = %s", i, stack));
    }

    for (int i = 0; i < nums1.length; i++) {
      nums1[i] = map.getOrDefault(nums1[i], -1);
    }
    return nums1;
  }

  public static void main(String[] args) {
    T0496_NextGreaterElement sut = new T0496_NextGreaterElement();
    /*
    单调递减栈:
    1       -->
    5       --> 1 - 5
    5, 4    -->
    5, 4, 2 -->
    5, 4, 3 --> 2 - 3
     */
    sut.test(new int[] {4,1,2}, new int[] {1,5,4,2,3}); //[-1,5,3]
//    sut.test(new int[] {4,1,2}, new int[] {1,3,4,2}); //[-1,3,-1]
//    sut.test(new int[] {2,4}, new int[] {1,2,3,4}); //[3,-1]
  }

  private void test(int[] nums1, int[] nums2) {
    System.out.println(Arrays.toString(nums1) + " & " + Arrays.toString(nums2) +
        " --> " + Arrays.toString(nextGreaterElement2(nums1, nums2)));
  }

}
