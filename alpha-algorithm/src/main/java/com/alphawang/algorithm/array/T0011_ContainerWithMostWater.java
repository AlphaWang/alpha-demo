package com.alphawang.algorithm.array;

/**
 * https://leetcode.com/problems/container-with-most-water/
 * 
 * 给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。
 * 在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0)。
 * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 *
 * 说明：你不能倾斜容器，且 n 的值至少为 2。
 * 
 * Input: [1,8,6,2,5,4,8,3,7]
 * Output: 49
 */
public class T0011_ContainerWithMostWater {

    /**
     * 双指针夹逼
     * 
     * 2ms - 97%
     */
    public static int mostWater(int[] nums) {
        int count = 0;
        
        int left = 0;
        int right = nums.length - 1;
        
        while (left < right) {
            int area = (right - left) * Math.min(nums[left], nums[right]);
            count = Math.max(area, count);
            if (nums[left] < nums[right]) {
                left++;
            } else {
                right--;
            }
        }
        
        return count;
    }

    public static void main(String[] args) {
        System.out.println(mostWater(new int[] {1,8,6,2,5,4,8,3,7} ));
    }

}
