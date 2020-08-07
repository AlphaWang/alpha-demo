package com.alphawang.algorithm.array;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/median-of-two-sorted-arrays/
 * Hard
 */
public class T0004_MedianOfTwoSortedArrays {

    /**
     * 1. 堆
     *    O(N)
     *    
     *    9ms - 12%
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int length = m + n;
        
        PriorityQueue<Integer> bigPq = new PriorityQueue<>(Comparator.reverseOrder());
        PriorityQueue<Integer> smallPq = new PriorityQueue<>();
        
        int index = 0, index1 = 0, index2 = 0;
        while (++index <= length) {
            int curr;
            if (index1 < m && (index2 >= n || nums1[index1] < nums2[index2])) { // 这里的判断很巧妙！
                curr = nums1[index1++];
            } else {
                curr = nums2[index2++];
            }
            
            Integer small = smallPq.peek();
            if (small != null && small < curr) {
                smallPq.offer(curr);
            } else {
                bigPq.offer(curr);
            }

            System.out.println(index);
            System.out.println(String.format("small = %s \nbig=%s", smallPq, bigPq));
            if (bigPq.size() > smallPq.size()) {
                smallPq.offer(bigPq.poll());
            } else if (smallPq.size() > bigPq.size() + 1) {
                bigPq.offer(smallPq.poll());
            }
            System.out.println(String.format("small = %s \nbig=%s", smallPq, bigPq));
            
            
        }
       
        return (length & 1) == 1 ? smallPq.peek() : (smallPq.peek() + bigPq.peek()) / 2.0;
    }

    /**
     * 2. 遍历 len / 2 次，记录前一次的值、当前值；
     *    如果总长为奇数，res = curr
     *    如果总长为偶数，res = (prev + curr) / 2
     *    
     *    O(N)
     *    2ms - 99.9%
     */
    public double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int length = m + n;

        int mid = length >>> 1;
        int index = 0, index1 = 0, index2 = 0;
        int prev = 0, curr = 0;
        while (index <= mid) {
            prev = curr;
            if (index1 >= m) {
                curr = nums2[index2++];
            }
            else if (index2 >= n) {
                curr = nums1[index1++];
            }
            
            else if (nums1[index1] < nums2[index2]) {
                curr = nums1[index1++];
            } else {
                curr = nums2[index2++];
            }
            System.out.println(String.format("[%s] prev = %s, curr = %s", index, prev, curr));
            index++;
        }
        
        return (length & 1) == 0 ? (prev + curr) / 2D : curr;
    }

    /**
     * 2-2. 遍历 len / 2 次，记录前一次的值、当前值； --> 优化取最小值逻辑
     *    如果总长为奇数，res = curr
     *    如果总长为偶数，res = (prev + curr) / 2
     *
     *    O(N)
     *    2ms - 99.9%
     */
    public double findMedianSortedArrays2_2(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int length = m + n;

        int mid = length >>> 1;
        int index = 0, index1 = 0, index2 = 0;
        int prev = 0, curr = 0;
        while (index <= mid) {
            prev = curr;
            if (index1 < m && (index2 >= n || nums1[index1] < nums2[index2])) { // 这里的判断很巧妙！
                curr = nums1[index1++];
            } else {
                curr = nums2[index2++];
            }
                
            System.out.println(String.format("[%s] prev = %s, curr = %s", index, prev, curr));
            index++;
        }

        return (length & 1) == 0 ? (prev + curr) / 2D : curr;
    }

    public static void main(String[] args) {
        // 2.0
        test(new int[] {1, 3}, new int[] {2});
        // 2.5
        test(new int[] {1, 2}, new int[] {3, 4});
        // 2.5
        test(new int[] {1, 3}, new int[] {2, 4});
    }

    public static void test(int[] nums1, int[] nums2) {
        System.out.println(String.format("%s - %s --> %s",
                                         Arrays.toString(nums1), Arrays.toString(nums2), 
                                         new T0004_MedianOfTwoSortedArrays().findMedianSortedArrays(nums1, nums2)));
    }
}
