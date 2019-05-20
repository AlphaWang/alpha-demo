package com.alphawang.algorithm.search;

public class BinarySearchFirst {

    /**
     * 查找第一个值等于给定值的元素
     */
    public static void main(String[] args) {
        System.out.println(binarySearch(new int[] {1, 2, 3, 4, 5, 8, 8, 8, 11, 18}, 8)); // 5
    }
        
    
    private static int binarySearch(int[] data, int target) {
        int left = 0;
        int right = data.length - 1;
        
        while (left <= right) {
            int middle = left + (right - left) / 2;

            // 当前值大于target，看左边
            if (data[middle] > target) {
                right = middle - 1;
            }

            // 当前值小于target，看右边
            else if (data[middle] < target) {
                left = middle + 1;
            }
            
            // 当前值等于target，
            else {
                // 如果 middle已经到最左，或者再往左就不相等了，则找到结果！
                if (middle == 0 || data[middle - 1] != target) {
                    return middle;
                } 
                // 否则再往左找
                else {
                    right = middle - 1;
                }
            }
            
        }
        
        return -1;
    }
}
