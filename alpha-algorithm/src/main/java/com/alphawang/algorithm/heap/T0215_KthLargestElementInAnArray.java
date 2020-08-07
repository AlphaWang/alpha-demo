package com.alphawang.algorithm.heap;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/kth-largest-element-in-an-array/
 * Medium
 */
public class T0215_KthLargestElementInAnArray {

    /**
     * 1. 排序
     *    1ms - 98%
     */
    public int findKthLargest(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }

    /**
     * 2. 堆
     *    3ms - 77%
     */
    public int findKthLargest2(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int num : nums) {
            if (pq.size() < k) {
                pq.offer(num);
            } else {
                if (pq.peek() < num) {
                    pq.poll();
                    pq.offer(num);
                }
            }
        }
        
        return pq.peek();
    }

    public static void main(String[] args) {
        test(new int[] {3,2,1,5,6,4}, 2); // 5
        test(new int[] {3,2,3,1,2,4,5,5,6}, 4); // 4
    }

    public static void test(int[] nums, int k) {
        System.out.println(String.format("%s - %s --> %s",
           Arrays.toString(nums), k, new T0215_KthLargestElementInAnArray().findKthLargest2(nums, k)));
    }

}
