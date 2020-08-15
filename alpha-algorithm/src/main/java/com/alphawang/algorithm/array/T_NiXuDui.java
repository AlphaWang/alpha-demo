package com.alphawang.algorithm.array;

import java.util.Arrays;

public class T_NiXuDui {
    
    public static int count(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        
        int len = nums.length;
        return pairs(nums, 0, len - 1);
    }

    private static int pairs(int[] nums, int left, int right) {
        if (left >= right) {
            return 0;
        }
        
        int mid = left + (right - left) / 2;
        int count = pairs(nums, left, mid) + pairs(nums, mid + 1, right);
       
        int currCount = 0;
        for (int i = left, j = mid + 1; i <= mid; i++) {
            while (j <= right && nums[i] > nums[j]) {
                j++;
                currCount++;
            }
            count += currCount;
            System.out.println(String.format("[%s, %s] %s, %s", left, right, currCount, currCount + count)); 
        }
        
        
        Arrays.sort(nums, left, right + 1);
        return count;
    }

    public static void main(String[] args) {
        test(new int[] {7,5,6,4});
    }

    public static void test(int[] nums) {
        System.out.println(String.format("%s -> %s", Arrays.toString(nums), count(nums)));
    }

}
