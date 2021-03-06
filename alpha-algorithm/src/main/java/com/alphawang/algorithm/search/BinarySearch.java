package com.alphawang.algorithm.search;

public class BinarySearch {

    public static void main(String[] args) {
        System.out.println(binarySearch(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }, 10)); // 9
        System.out.println(binarySearch(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }, 3));  // 2
        System.out.println(binarySearch(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }, 11)); // -1

        System.out.println(binarySearch(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 8, 8 }, 8)); // 7
        System.out.println(binarySearch(new int[] { 1, 2, 3, 4, 5, 8, 8, 8, 11, 18 }, 8)); // 7

        System.out.println("----------test Recursive:");
        System.out.println(binarySearchRecursive(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }, 10)); // 9
        System.out.println(binarySearchRecursive(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }, 3));  // 2
        System.out.println(binarySearchRecursive(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }, 11)); // -1

        System.out.println(binarySearchRecursive(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 8, 8 }, 8)); // 7
        System.out.println(binarySearchRecursive(new int[] { 1, 2, 3, 4, 5, 8, 8, 8, 11, 18 }, 8)); // 7

        /**
         * 求中点的方法
         */
        System.out.println("----------test middle:");
        int middle = (22222 + Integer.MAX_VALUE) / 2;  // 溢出！！
        int middle2 = 22222 + ((Integer.MAX_VALUE - 22222) / 2);
        int middle3 = 22222 + ((Integer.MAX_VALUE - 22222) >> 1); // 位运算更高效

        System.out.println(middle);
        System.out.println(middle2);
        System.out.println(middle3);
    }

    public static int binarySearch(int[] data, int target) {
        int left = 0;
        int right = data.length - 1;
        return binarySearch(data, left, right, target);
    }

    public static int binarySearch(int[] data, int left, int right, int target) {
        while (left <= right) {
            // 可能溢出！！
            // int middle = (l + r) / 2;
            int middle = left + (right - left) / 2;
            if (data[middle] == target) {
                return middle;
            }

            // 当前值大于target，看左边
            if (data[middle] > target) {
                right = middle - 1;
            }

            // 当前值小于target, 看右边
            if (data[middle] < target) {
                left = middle + 1;
            }
        }

        return -1;
    }

    public static int binarySearchRecursive(int[] data, int target) {
        return binarySearchRecursive(data, 0, data.length - 1, target);
    }
    
    public static int binarySearchRecursive(int[] data, int low, int high, int target) {
        if (low > high) {
            return -1;
        }
        
        int mid = low + (high - low) / 2;
        if (data[mid] > target) {
            high = mid - 1;
            return binarySearchRecursive(data, low, high, target);
        } else if (data[mid] < target) {
            low = mid + 1;
            return binarySearchRecursive(data, low, high, target);
        } else {
            return mid;
        }
        
    }
}
