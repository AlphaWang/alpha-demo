package com.alphawang.algorithm.search;

public class BinarySearch {

    public static void main(String[] args) {
        System.out.println(binarySearch(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 10));
        System.out.println(binarySearch(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 3));
        System.out.println(binarySearch(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 11));

        /**
         * 求中点的方法
         */
         int middle = (22222 + Integer.MAX_VALUE) / 2;  // 溢出！！
         int middle2 = 22222 + ((Integer.MAX_VALUE - 22222) / 2);
         int middle3 = 22222 + ((Integer.MAX_VALUE - 22222) >> 1); // 位运算更高效
         
         System.out.println(middle); 
         System.out.println(middle2); 
         System.out.println(middle3); 
    }
        
    
    private static int binarySearch(int[] data, int target) {
        int left = 0;
        int right = data.length - 1;
        
        while (left <= right) {
            // 可能溢出！！
            // int middle = (l + r) / 2;
            int middle = left + (right - left) / 2;
            if (data[middle] == target) {
                return middle;
            }
            
            if (target < data[middle]) {
                right = middle - 1;
            }
            
            if (target > data[middle]) {
                left = middle + 1;
            }
        }
        
        return -1;
    }
}
