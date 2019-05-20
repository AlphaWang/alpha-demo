package com.alphawang.algorithm.search;

public class BinarySearchBigger {

    /**
     * 查找第一个 值大于或等于给定值的元素
     */
    public static void main(String[] args) {
        System.out.println(binarySearch(new int[] {1, 2, 3, 4, 5, 8, 8, 8, 11, 18}, 10)); // 找到元素11， index=8
        System.out.println(binarySearch(new int[] {1, 2, 3, 4, 5, 8, 8, 8, 11, 18}, 7)); // 找到元素8， index=5
    }
        
    
    private static int binarySearch(int[] data, int target) {
        int left = 0;
        int right = data.length - 1;
        
        while (left <= right) {
            int middle = left + (right - left) / 2;

            // 当前值大于等于target，
            if (data[middle] >= target) {
                // 并且再往左就比target小，则找到！
                if (middle == 0 || data[middle - 1] < target) {
                    return middle;
                } 
                // 否则，看左边
                else {
                    right = middle - 1;
                }
            }
            
            // 当前值小于 target，看右边
            else {
                left = middle + 1;
            }
            
        }
        
        return -1;
    }
}
