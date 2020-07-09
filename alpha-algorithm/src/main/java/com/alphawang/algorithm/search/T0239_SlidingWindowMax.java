package com.alphawang.algorithm.search;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * https://leetcode.com/problems/sliding-window-maximum/
 * Hard
 * 
 * 给定一个数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。
 * 你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
 *
 * 返回滑动窗口中的最大值。
 * 
 *   > 1: 暴力解法：循环找最大值，存入数组。
 *   > 2: 维护大顶堆。Q: 需要删除非堆顶元素  
 *   > 3: 维护一个双端队列。队列元素为下标，最左为当前最大值的下标。
 */
public class T0239_SlidingWindowMax {

    /**
     * 1. 暴力遍历。 O(N*K)
     *    Time Limit Exceeded
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] res = new int[nums.length - k + 1];
        for (int i = 0; i < res.length; i++) {
            int max = nums[i];
            for (int j = i + 1; j < i + k; j++) {
                max = Math.max(max, nums[j]);
            }
            res[i] = max;
        }
        return res;
    }

    /**
     * 2. 维护一个双端队列。队列元素为下标，最左为当前最大值的下标。
     *    20ms - 30%
     */
    public int[] maxSlidingWindow2(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k > nums.length) {
            return new int[0];
        }
        int[] res = new int[nums.length - k + 1];
        Deque<Integer> queue = new LinkedList<>();
        
        int maxIndex = 0;
        for (int i = 0; i < k; i++) {
            cleanQueue(nums, queue, i, k);
            queue.addLast(i);
            if (nums[i] > nums[maxIndex]) {
                maxIndex = i;
            }
        }
        res[0] = nums[maxIndex];
        
        for (int i = k; i < nums.length; i++) {
            cleanQueue(nums, queue, i, k);
            queue.addLast(i);
            System.out.println(String.format("[%s] %s == %s", i, queue, printValue(nums, queue)));

            res[i - k + 1] = nums[queue.getFirst()];
        }
        
        return res;
    }
    
    private void cleanQueue(int[] nums, Deque<Integer> queue, int i, int k) {
        if (!queue.isEmpty() && queue.getFirst() == i - k) {
            queue.removeFirst();
        }
        
        while (!queue.isEmpty() && nums[i] > nums[queue.getLast()]) {
            queue.removeLast();
        }
    }

    /**
     * 2. 维护一个双端队列。队列元素为下标，最左为当前最大值的下标。
     *    33ms - 17%
     */
    public int[] maxSlidingWindow2_1(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k > nums.length) {
            return new int[0];
        }
        int[] res = new int[nums.length - k + 1];
        Deque<Integer> queue = new LinkedList<>();

        for (int i = 0; i < nums.length; i++) {
            // 上一个最大值被移除
            if (!queue.isEmpty() && queue.getFirst() == i - k) {
                queue.removeFirst();
            }
            // 更新队列，剔除比新元素小的：removeFirst不可！！
            while (!queue.isEmpty() && nums[queue.getLast()] < nums[i]) {
                queue.removeLast();
            }
            
            queue.addLast(i);
            System.out.println(String.format("[%s] %s == %s", i, queue, printValue(nums, queue)));

            if (i >= k - 1) {
                res[i - k + 1] = nums[queue.getFirst()];
            }

        }

        return res;
    }

    /**
     * 3. 双端队列，存储最大值
     *    //TODO ?
     */
    public int[] maxSlidingWindow3(int[] nums, int k) {
        int left = 0; //左
        int p = 0; // res index
        int right = 0; //右
        int[] res = new int[nums.length - k + 1];

        Deque<Integer> queue = new LinkedList<>();

        while (right < nums.length) {
            while (queue.size() > 0 && queue.peekLast() < nums[right]) {
                queue.pollLast();
            }
            queue.offer(nums[right]);
            
            while (right - left + 1 > k) {
                if (nums[left] == queue.peek()) {
                    queue.poll();
                }
                left++;
            }
            if (right + 1 >= k) {
                res[p] = queue.peek();
                p++;
            }
            right++;
        }

        return res;
    }
    
    private static String printValue(int[] nums, Deque<Integer> queue) {
        int[] values = new int[queue.size()];
        int i = 0;
        for (int index : queue) {
            values[i++] = nums[index];
        }
        
        return Arrays.toString(values);
    } 

    public static void main(String[] args) {
        /*
         * 输入: nums = [1,3,-1,-3,5,3,6,7], 和 k = 3
         * 输出: [3,3,5,5,6,7] 
         */
        test(new int[] {1,3,-1,-3,5,3,6,7}, 3);
        /*
         * [1,3,1,2,0,5]  3
         * [3,3,2,5]
         */
        test(new int[] {1,3,1,2,0,5}, 3);
    }
    
    private static void test(int[] nums, int k) {
        T0239_SlidingWindowMax sut = new T0239_SlidingWindowMax();
        System.out.println(String.format("%s - %s --> \n%s",
             Arrays.toString(nums),
             k,
             Arrays.toString(sut.maxSlidingWindow2(nums, k))
             ));
    }

}
