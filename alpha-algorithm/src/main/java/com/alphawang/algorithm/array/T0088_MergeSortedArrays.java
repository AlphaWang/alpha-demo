package com.alphawang.algorithm.array;

import java.util.Arrays;

/**
 *  https://leetcode-cn.com/problems/merge-sorted-array/
 *  Easy
 */
public class T0088_MergeSortedArrays {

    /**
     * 1. 双指针从前往后遍历 nums1， nums2；挪动nums1
     *    1 ms
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        if (nums2 == null || nums2.length <= 0) return;
        int index1 = 0, index2 = 0, moved = 0;
        for (int i = 0; i < m + n; i++) {
            if (index2 >= n) continue;;
            if (nums1[index1] <= nums2[index2] && index1 < m + moved) {
                nums1[i] = nums1[index1++];
            } else {
                move(nums1, i);
                moved++;
                index1++;
                nums1[i] = nums2[index2++];
            }
        }
    }

    /**
     * 2. 双指针从后往前遍历 nums1， nums2；
     *    0 ms
     */
    public void merge2(int[] nums1, int m, int[] nums2, int n) {
        int index1 = m - 1, index2 = n -1;
        for (int i = m + n - 1; i >= 0; i--) {
            if (index1 >= 0 && index2 >= 0) {
                if (nums1[index1] <= nums2[index2]) {
                    nums1[i] = nums2[index2--];
                } else {
                    nums1[i] = nums1[index1--];
                }
            } else if (index2 >= 0) {
                System.arraycopy(nums2, 0, nums1, 0, index2 + 1); 
            }
        }
    }

    /**
     * 3. 双指针从后往前遍历：优化循环判断逻辑
     *    0 ms
     */
    public void merge3(int[] nums1, int m, int[] nums2, int n) {
        int index1 = m - 1, index2 = n -1;
        int index = m + n -1;
        while (index1 >= 0 && index2 >= 0) {
            nums1[index--] = nums1[index1] > nums2[index2] ? nums1[index1--] : nums2[index2--];
        }
        
        if (index2 >= 0) {
            System.arraycopy(nums2, 0, nums1, 0, index2 + 1);
        }
    }
    
    
    private void move(int[] nums, int startIndex) {
        for (int i = nums.length - 1; i > startIndex; i--) {
            nums[i] = nums[i-1];
        }
    }

    public static void main(String[] args) {
        T0088_MergeSortedArrays sut = new T0088_MergeSortedArrays();
        
        int[] nums1 = new int[] {1,2,3,0,0,0};
        int[] nums2 = new int[] {2,5,6};
        sut.merge(nums1, 3, nums2, 3) ;
        System.out.println(Arrays.toString(nums1));  // [1,2,2,3,5,6]

        nums1 = new int[] {1,2,3,7,8,9};
        nums2 = new int[] {2,5,6};
        sut.merge3(nums1, 3, nums2, 3) ;
        System.out.println(Arrays.toString(nums1));  // [1,2,2,3,5,6]

        nums1 = new int[] {3, 4, 5,7,8,9};
        nums2 = new int[] {2, 4,6};
        sut.merge3(nums1, 3, nums2, 3) ;
        System.out.println(Arrays.toString(nums1));  // [2,3,4,4,5,6]

        nums1 = new int[] {1};
        nums2 = new int[] {};
        sut.merge(nums1, 1, nums2, 0) ;
        System.out.println(Arrays.toString(nums1));  // [1]


        nums1 = new int[] {2, 0};
        nums2 = new int[] {1};
        sut.merge(nums1, 1, nums2, 1) ;
        System.out.println(Arrays.toString(nums1));  // [1,2]
        
    }

}
