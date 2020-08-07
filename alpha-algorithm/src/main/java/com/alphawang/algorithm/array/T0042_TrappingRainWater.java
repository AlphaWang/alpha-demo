package com.alphawang.algorithm.array;

import java.util.Arrays;
import java.util.Stack;

/**
 * https://leetcode.com/problems/trapping-rain-water/
 * Hard
 */
public class T0042_TrappingRainWater {

    /**
     *  1. 暴力：对每个元素，找到其左右最大边界
     *  
     *     69ms - 5%
     */
    public int trap(int[] height) {
        if (height == null || height.length == 0) return 0;
        int result = 0;
        for (int i = 1; i < height.length; i++) {
            int maxLeft = 0, maxRight = 0;
            // 找左侧最大值 [0, i-1]
            for (int left = 0; left < i; left++) {
                maxLeft = Math.max(maxLeft, height[left]);
            }
            // 找右侧最大值 [i+1, n]
            for (int right = i + 1; right < height.length; right++) {
                maxRight = Math.max(maxRight, height[right]);
            }
            
            int area = Math.min(maxLeft, maxRight) - height[i];
            result += area > 0 ? area : 0;
            
        }
        return result;
    }

    /**
     *  !!!
     *  2. DP，找到每个元素的leftMax, rightMax
     *     1ms - 95%
     */
    public int trap2(int[] height) {
        if (height == null || height.length == 0) return 0;
        int result = 0;
        int size = height.length;
        
        int[] leftMax = new int[size];
        int[] rightMax = new int[size];
        leftMax[0] = height[0];
        rightMax[size - 1] = height[size - 1];
        
        // 遍历1：找到各个元素的 leftMax: dp[i] = max{ dp[i - 1], height[i] }
        for (int i = 1; i < size; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }
        // 遍历2：找到各个元素的 rightMax: dp[i] = max{ dp[i + 1], height[i] }
        for (int i = size - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }
        // 遍历3： 计算
        for (int i = 1; i < size; i++) {
            result += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        
        return result;
    }

    /**
     *  ？？？
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
     *  4. 单调栈：递减
     *     3ms - 18%
     */
    public int trap4(int[] height) {
        if (height == null || height.length == 0) return 0;
        int result = 0;
        // 栈 中存的是位置
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        
        int rightIndex = 1;
        while (rightIndex < height.length) {
            System.out.println(String.format("[%s] %s", rightIndex, stack));
            
            // 如果当前高度 > 栈顶，则计算面积
            while (!stack.empty() && height[rightIndex] > height[stack.peek()]) {
                // 当前计算的位置
                int baseIndex = stack.pop(); 
                if (stack.empty()) {
                    System.out.println(String.format("[%s] ignore baseIndex = %s",
                                                     rightIndex, baseIndex));
                    break;
                }
                int leftIndex = stack.peek();
                // 横向宽度：(leftIndex, rightIndex)
                int distance = rightIndex - leftIndex - 1; 
                // 纵向高度：左右最低的高度 - 当前高度
                int h = Math.min(height[rightIndex], height[leftIndex]) - height[baseIndex];
                result += distance * h;

                System.out.println(String.format("[%s] baseIndex = %s, leftIndex = %s, distance = %s, h = %s",
                                                 rightIndex, baseIndex, leftIndex, distance, h));
            }
            stack.push(rightIndex++);
        }
        
        return result;
    }

    public static void main(String[] args) {
        // 6
        test(new int[] {0,1,0,2,1,0,1,3,2,1,2,1});
    }

    public static void test(int[] height) {
        System.out.println(Arrays.toString(height));
        System.out.println(String.format("%s -> \n%s", Arrays.toString(height), 
                                         new T0042_TrappingRainWater().trap4(height)));
    }

}
