package com.alphawang.algorithm.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * product[i] = number[0] * ... * number[i-1] * number[i + 1] * number[n]
 */
public class T_CalProduct {

    /**
     * 1. 除法
     * 
     *    注意要记录 0 的位置
     */
    public static void cal(int[] numbers, int[] products) {
        
    }

    /**
     * 2. 预计算，空间换时间
     */
    public static void cal2(int[] numbers, int[] products) {
        int len = numbers.length;
        
        // [0, i] 从当前元素 直至 末尾的乘积
        Map<Integer, Integer> toEnd = new HashMap<>();
        toEnd.put(len - 1, numbers[len - 1]);
        for (int i = len - 2; i >= 0; i--) {
            toEnd.put(i, numbers[i] * toEnd.get(i + 1));
        }

        // [i, n) 从开头 直至 当前元素的乘积
        Map<Integer, Integer> fromStart = new HashMap<>();
        fromStart.put(0, numbers[0]);
        for (int i = 1; i < len; i++) {
            fromStart.put(i, numbers[i] * fromStart.get(i - 1));
        }

        // 计算当前元素之外的所有元素乘积
        for (int i = 0; i < len; i++) {
            int fromStartProduct = i > 0 ? fromStart.get(i - 1) : 1;
            int toEndProduct = i < len - 1 ? toEnd.get(i + 1) : 1;
            products[i] = fromStartProduct * toEndProduct;
        }
    }


    /**
     * 3. 归并
     *    TODO 
     */
    private static HashMap<Key, Integer> cache = new HashMap<>();
    private static int mergeProduct(int[] numbers, int start, int end) {
        Key key= key(start, end);
        
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        
        int res;
        if (start > end) {
            res = 1;
        } else if (start + 1 == end) {
            res = numbers[start] * numbers[end];
        } else if (start == end) {
            res = numbers[start];
        } else {
            int mid = start + (end - start) / 2;
            res = mergeProduct(numbers, start, mid) * mergeProduct(numbers, mid + 1, end); 
        }

        cache.put(key, res);
        return res; 
    }
    
    
    private static Key key(int start, int end) {
        return new Key(start, end);
    }
    
    private static class Key {
        Integer start;
        Integer end;
        
        public Key(int start, int end) {
           this.start = start;
           this.end = end;
        }
        
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Key)) {
                return false;
            }
            Key key = (Key) o;
           return key.start == this.start && key.end == this.end; 
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(start, end);
        }
        
        @Override
        public String toString() {
            return start + "-" + end;
        }
    }
    

    public static void main(String[] args) {
//        test(new int[] { 1, 2, 3, 4, 5, 6, 7, 8}, new int[8]);
//        test(new int[] { 1, 2, 3, 4, 5, 6, 7}, new int[7]);
        test(new int[] { 1, 2, 3, 4}, new int[4]);
        test(new int[] { 0, 2, 3, 4}, new int[4]);
    }

    public static void test(int[] numbers, int[] products) {
        System.out.println(Arrays.toString(numbers));
        cal2(numbers, products);
        System.out.println(Arrays.toString(products));
        
    }
}
