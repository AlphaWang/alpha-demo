package com.alphawang.algorithm.array;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *  https://leetcode-cn.com/problems/zui-xiao-de-kge-shu-lcof/
 *  Easy
 */
public class M040_MinKNums {

    /**
     * 1. 维护一个大顶堆
     *    21 ms
     */
    public int[] getLeastNumbers(int[] arr, int k) {
        if (arr == null || k <= 0) {
            return new int[0];
        }
        PriorityQueue<Integer> queue = new PriorityQueue(Comparator.reverseOrder());
        for (int i = 0; i < arr.length; i++) {
            if (queue.size() < k) {
                queue.offer(arr[i]);
            } else {
                if (arr[i] < queue.peek()) {
                    queue.poll();
                    queue.offer(arr[i]);
                }
            }
        }
        return queue.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * 2. 排序
     *    7 ms
     */
    public int[] getLeastNumbers2(int[] arr, int k) {
        if (arr == null || k <= 0) {
            return new int[0];
        }
        
        Arrays.sort(arr);
        int[] res = new int[k];
        System.arraycopy(arr, 0, res, 0, k);
        
        return res;
    }

    /**
     * 3. 计数排序
     *    2 ms
     */
    public int[] getLeastNumbers3(int[] arr, int k) {
        if (arr == null || k <= 0) {
            return new int[0];
        }
        
        int[] counter = new int[10000];
        for (int num : arr) {
            counter[num]++;
        }
        
        int[] res = new int[k];
        int pos = 0;
        for (int i = 0; i < counter.length; i++) {
            if (counter[i] == 0) continue;
            for (int j = 1; j <= counter[i]; j++) {
                res[pos++] = i;
                if (pos == k) {
                    return res;
                }
            }
        }
        
        return res;
    }

    /**
     * TODO  https://leetcode-cn.com/problems/zui-xiao-de-kge-shu-lcof/solution/tu-jie-top-k-wen-ti-de-liang-chong-jie-fa-you-lie-/
     * 4. 快排思想？
     */
    public int[] getLeastNumbers4(int[] arr, int k) {
        
        return null;
    }
    

    public static void main(String[] args) {
        M040_MinKNums sut = new M040_MinKNums();
        
        int[] res = sut.getLeastNumbers3(new int[]{3,2,1}, 2);
        System.out.println(Arrays.toString(res)); // 1, 2

        res = sut.getLeastNumbers3(new int[]{0,1,2,1}, 1);
        System.out.println(Arrays.toString(res)); // 0

        res = sut.getLeastNumbers3(new int[]{0,0,0,2,0,5}, 0);
        System.out.println(Arrays.toString(res)); // 0
        
    }

}
