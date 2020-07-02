package com.alphawang.algorithm.search;


import java.util.Arrays;

/**
 * https://leetcode.com/problems/search-a-2d-matrix/
 * Medium
 */
public class T0074_Search2DMatrix {

    /**
     * 1. 先二分找到 子数组
     *    0ms - 100% 
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }
        
        // 找到 "比target小" 的起始点 matrix[x][0]
        int left = 0, right = matrix.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (matrix[mid][0] == target) {
                return true;
            } else if (matrix[mid][0] > target) {
                right = mid - 1;
            } else if (matrix[mid][0] < target) {
                if (matrix[mid + 1][0] > target) {
                    left = mid;
                    break;
                }
                left = mid + 1;
            }
        }
        
        if (matrix[left] == null || matrix[left].length == 0) {
            return false;
        }
        
        // 再查找子数组
        int subLeft = 0, subRight = matrix[left].length - 1;
        while (subLeft < subRight) {
            int mid = subLeft + (subRight - subLeft) / 2;
            if (matrix[left][mid] == target) {
                return true;
            } else if (matrix[left][mid] > target) {
                subRight = mid - 1;
            } else if (matrix[left][mid] < target){
                subLeft = mid + 1;
            }
        }

        return matrix[left][subLeft] == target;
    }

    public static void main(String[] args) {
        /*
         * Input:
         * matrix = [
         *   [1,   3,  5,  7],
         *   [10, 11, 16, 20],
         *   [23, 30, 34, 50]
         * ]
         * target = 3
         * Output: true
         */
        test(new int[][] {
          { 1,   3,  5,  7 },
          { 10, 11, 16, 20 },
          { 23, 30, 34, 50 }
        }, 3);

        /*
         * Input:
         * matrix = [
         *   [1,   3,  5,  7],
         *   [10, 11, 16, 20],
         *   [23, 30, 34, 50]
         * ]
         * target = 11
         * Output: true
         */
        test(new int[][] {
          { 1,   3,  5,  7 },
          { 10, 11, 16, 20 },
          { 23, 30, 34, 50 }
        }, 11);

        /*
         * Input:
         * matrix = [
         *   [1,   3,  5,  7],
         *   [10, 11, 16, 20],
         *   [23, 30, 34, 50]
         * ]
         * target = 13
         * Output: false 
         */
        test(new int[][] {
          { 1,   3,  5,  7 },
          { 10, 11, 16, 20 },
          { 23, 30, 34, 50 }
        }, 13);

        /*
         * 空数组：false
         */
        test(new int[][] { }, 13);
        test(new int[][] { {} }, 13);
        /*
         * 一个元素：true
         */
        test(new int[][] { {1} }, 1);
    }

    private static void test(int[][] matrix, int target) {
        T0074_Search2DMatrix sut = new T0074_Search2DMatrix();
        System.out.println(Arrays.deepToString(matrix));
        System.out.println(String.format("%s --> %s", target, sut.searchMatrix(matrix, target)));
    }

}
