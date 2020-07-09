package com.alphawang.algorithm.array;

import java.util.Stack;

/**
 * https://leetcode.com/problems/trapping-rain-water/
 * Hard
 */
public class T0042_TrappingRainWater {

    /**
     *  1. 暴力：对每个元素，找到其左右最大边界
     *     69 ms
     */
    public int trap(int[] height) {
        if (height == null || height.length == 0) return 0;
        int result = 0;
        for (int i = 1; i < height.length; i++) {
            int maxLeft = 0, maxRight = 0;
            for (int left = 0; left < i; left++) {
                maxLeft = Math.max(maxLeft, height[left]);
            }
           
            for (int right = i + 1; right < height.length; right++) {
                maxRight = Math.max(maxRight, height[right]);
            }
            
            int area = Math.min(maxLeft, maxRight) - height[i];
            result += area > 0 ? area : 0;
            
        }
        return result;
    }

    /**
     * !!!
     *  2. DP，找到每个元素的leftMax, rightMax
     *     1 ms
     */
    public int trap2(int[] height) {
        if (height == null || height.length == 0) return 0;
        int result = 0;
        int size = height.length;
        
        int[] leftMax = new int[size];
        int[] rightMax = new int[size];
        leftMax[0] = height[0];
        rightMax[size - 1] = height[size - 1];
        
        for (int i = 1; i < size; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }
        for (int i = size - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }
        for (int i = 1; i < size; i++) {
            result += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        
        return result;
    }

    /**
     * !!!
     *  3. 优化方法2：双指针，两边夹逼
     *     1 ms
     */
    public int trap3(int[] height) {
        if (height == null || height.length == 0) return 0;
        int result = 0;
        
        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0;
        while (left < right) {
            // 右侧大：左夹逼，记录leftMax
            if (height[left] < height[right]) {
                if (height[left] < leftMax) {
                    result += leftMax - height[left];
                } else {
                    leftMax = height[left];
                }
                ++left;
            } 
            // 左侧大：右夹逼
            else {
                if (height[right] < rightMax) {
                    result += rightMax - height[right];
                } else {
                    rightMax = height[right];
                }
                --right;
            }
        }
        
        return result;
    }

    /**
     * TODO WIP
     *  4. 栈
     */
    public int trap4(int[] height) {
        if (height == null || height.length == 0) return 0;
        int result = 0;
        Stack<Integer> stack = new Stack<>();
        
        for (int i = 0; i < height.length; i++) {
            while (!stack.empty() && height[i] > height[stack.peek()]) {
                int top = stack.pop();
                if (stack.empty()) {
                    break;
                }
                int distance = i - stack.peek() - 1; //TODO
                int h = Math.min(height[i], height[stack.peek()]) - height[top];
                result += distance * h;
            }
            stack.push(i+1);
        }
        
        return result;
    }

    public static void main(String[] args) {
        T0042_TrappingRainWater sut = new T0042_TrappingRainWater();
        
        System.out.println(sut.trap(new int[] {0,1,0,2,1,0,1,3,2,1,2,1})); //6
        System.out.println(sut.trap2(new int[] {0,1,0,2,1,0,1,3,2,1,2,1})); //6
        System.out.println(sut.trap3(new int[] {0,1,0,2,1,0,1,3,2,1,2,1})); //6
        System.out.println(sut.trap4(new int[] {0,1,0,2,1,0,1,3,2,1,2,1})); //6
    }

}
