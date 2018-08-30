package com.alphawang.algorithm.search;

public class BinarySearch {

    public static void main(String[] args) {
         int[] data = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
         int index = binarySearch(data, 10);
         System.out.println(index);
         
         int middle = (22222 + Integer.MAX_VALUE) / 2;  // 溢出！！
         int middle2 = 22222 + (Integer.MAX_VALUE - 222) / 2;
         System.out.println(middle); 
         System.out.println(middle2); 
    }
        
    
    private static int binarySearch(int[] data, int target) {
        int l = 0;
        int r = data.length - 1;
        
        while (l <= r) {
            // 可能溢出！！
            // int middle = (l + r) / 2;
            int middle = l + (r - l) / 2;
            if (data[middle] == target) {
                return middle;
            }
            
            if (data[middle] > target) {
                r = middle - 1;
            }
            
            if (data[middle] < target) {
                l = middle + 1;
            }
        }
        
        return -1;
    }
}
