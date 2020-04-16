package com.alphawang.algorithm.sort.n;

import java.util.Arrays;

public class CountingSort {

    private static int[] countingSort(int[] a, int n) {
        if (n <= 1) return a;
        /**
         * 1. 查找数组中数据的范围
         * 
         * sample: a = [2, 5, 3, 0, 2, 3, 0, 3]
         * max --> 5
         */
        // 
        int max = a[0];
        for (int i = 1; i < n; ++i) {
            if (max < a[i]) {
                max = a[i];
            }
        }

        /**
         * 2. 申请一个计数数组c，下标大小[0,max]； 计算每个元素的个数，放入c中
         * 计数！
         * 
         * Sample: c = [2, 0, 2, 3, 0, 1]
         */
        
        int[] c = new int[max + 1]; 
        for (int i = 0; i <= max; ++i) {
            c[i] = 0;
        }
        for (int i = 0; i < n; ++i) {
            c[a[i]]++;
        }

        /**
         * 3. 依次累加
         * 
         * Sample: c = [2, 2, 4, 7, 7, 8]
         */
        for (int i = 1; i <= max; ++i) {
            c[i] = c[i-1] + c[i];
        }

        // 临时数组r，存储排序之后的结果
        int[] r = new int[n];
        /**
         * 4. !!!!!! 计数排序的关键步骤，有点难理解
         */
        System.out.println("++++ Array: " + Arrays.toString(a));
        System.out.println("++++ Count: " + Arrays.toString(c));
        
        for (int i = n - 1; i >= 0; --i) {
            System.out.println("------- Loop " + i);
            
            // 待排序数值
            int num = a[i];
            System.out.println("-- Sort Num: " + num);
            
            // 该数的 "累计计数"，即是其排序位置
            int index = c[num] - 1;
            r[index] = a[i];
            System.out.println("-- Count: " + c[num]);
            
            // 该数已排序，累计计数需要 -1
            c[num]--;
            System.out.println("-- Reduced Count: " + Arrays.toString(c));

            System.out.println("-- Result: " + Arrays.toString(r));
        }
        
        return r;
    }


    public static void main(String[] args) {
        int[] array = new int[] { 2, 5, 3, 1, 2, 3, 1, 3 };
        print(countingSort(array, array.length));
    }

    private static void print(int[] data) {
        System.out.println(Arrays.toString(data));
    }

}
