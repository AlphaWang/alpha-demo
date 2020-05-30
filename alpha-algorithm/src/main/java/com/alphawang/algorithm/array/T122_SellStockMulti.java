package com.alphawang.algorithm.array;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii/
 * 
 * 
 * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
 * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 *
 *  
 *
 * 示例 1:
 *
 * 输入: [7,1,5,3,6,4]
 * 输出: 7
 * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
 *      随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。
 * 
 * 示例 2:
 *
 * 输入: [1,2,3,4,5]
 * 输出: 4
 * 解释: 在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
 *      注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。
 *      因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
 * 示例 3:
 *
 * 输入: [7,6,4,3,1]
 * 输出: 0
 * 解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
 *  
 *
 * 提示：
 * 1 <= prices.length <= 3 * 10 ^ 4
 * 0 <= prices[i] <= 10 ^ 4
 */
public class T122_SellStockMulti {
    
    public static int maxProfit(int[] nums) {
        int lowPrice = -1;
        
        int profit = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i < nums.length - 1 && nums[i] < nums[i+1] && (i == 0 || nums[i] < nums[i - 1]) ) {
                lowPrice = nums[i];
                continue;
            }

            if (i > 0 && nums[i] > nums[i - 1] && (i == nums.length - 1 || nums[i] > nums[i+1])) {
                if (lowPrice > 0 && nums[i] > lowPrice) {
                    profit += nums[i] - lowPrice;
                    System.out.println(String.format("buy = %s, sell = %s", lowPrice, nums[i]));
                    lowPrice = -1;
                }
            }
            
        }
        
        return profit;
    }

    /**
     * 先找到波谷，再往后找到波峰
     */
    public static int maxProfit2(int[] nums) {
        int valley = nums[0];
        int peak = nums[0];

        int profit = 0;
//        int i = 0;
//        while (i < nums.length - 1) {
        for (int i = 0; i < nums.length; i++) {
            // 找波谷
            while (i < nums.length - 1 && nums[i] >= nums[i + 1]) {
                i++;
            }
            valley = nums[i];
            // 找波峰
            while (i < nums.length - 1 && nums[i] <= nums[i +1]) {
                i++; 
            }
            peak = nums[i];
            
            profit += peak - valley;
        }
        
        return profit;
    }

    /**
     * 从第二天开始，如果当前价格比之前价格高，则把差值加入利润中
     */
    public static int maxProfit3(int[] nums) {
       int profit = 0;
       for (int i = 1; i < nums.length; i++) {
           if (nums[i] > nums[i-1]) {
               profit += nums[i] - nums[i-1];
           }
       }
       
       return profit;
    }

    public static void main(String[] args) {
        printMaxProfit1(new int[] {7, 1, 5, 3, 6, 4}); //7
        printMaxProfit1(new int[] {1,2,3,4,5}); //4
        printMaxProfit1(new int[] {7,6,4,3,1}); //0

        printMaxProfit2(new int[] {7, 1, 5, 3, 6, 4}); //7
        printMaxProfit2(new int[] {1,2,3,4,5}); //4
        printMaxProfit2(new int[] {7,6,4,3,1}); //0

        printMaxProfit3(new int[] {7, 1, 5, 3, 6, 4}); //7
        printMaxProfit3(new int[] {1,2,3,4,5}); //4
        printMaxProfit3(new int[] {7,6,4,3,1}); //0
        
    }
    
    private static void printMaxProfit1(int[] nums) {
        System.out.println(String.format("%s -> %s", Arrays.toString(nums), maxProfit(nums)));
    }

    private static void printMaxProfit2(int[] nums) {
        System.out.println(String.format("%s -> %s", Arrays.toString(nums), maxProfit2(nums)));
    }

    private static void printMaxProfit3(int[] nums) {
        System.out.println(String.format("%s -> %s", Arrays.toString(nums), maxProfit3(nums)));
    }

}
