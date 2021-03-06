package com.alphawang.algorithm.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

/**
 *  https://leetcode.com/problems/top-k-frequent-elements/
 *  Medium
 *  
 *  给定一个非空的整数数组，返回其中出现频率前 k 高的元素。
 */
public class T0347_TopKFrequentElements {

    /**
     * 1. Map: 14ms
     *    存储 num - count，再按count排序
     */
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> count = new HashMap<>();
        for (int num : nums) {
            int c = count.getOrDefault(num, 0);
            count.put(num, c + 1);
        }

        int[] res = count.entrySet().stream()
//            .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
            .sorted(Comparator.<Entry<Integer, Integer>, Integer>comparing(Entry::getValue).reversed())
            .limit(k)
            .map(Entry::getKey)
            .mapToInt(Integer::intValue)
            .toArray();

        System.out.println(Arrays.toString(res));
        
        return res;
    }

    /**
     * 2. Map + 小顶Heap: 13ms
     *    存储 num - count，再依次放入堆中
     */
    public int[] topKFrequent2(int[] nums, int k) {
        Map<Integer, Integer> count = new HashMap<>();
        for (int num : nums) {
            int c = count.getOrDefault(num, 0);
            count.put(num, c + 1);
        }

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparing(count::get));
        for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
            priorityQueue.add(entry.getKey());
            if (priorityQueue.size() > k) {
                priorityQueue.poll();
            }
        }
        int[] res = priorityQueue.stream().mapToInt(Integer::intValue).toArray();

        System.out.println(Arrays.toString(res));
        return res;
    }


    /**
     * 3. Map + 桶排序 (只适用于非负数的场景)
     *   存储 num - count，再以count为下标放入数组，倒序遍历数组取前k
     */
    public int[] topKFrequent3(int[] nums, int k) {
        Map<Integer, Integer> count = new HashMap<>();
        for (int num : nums) {
            count.put(num, count.getOrDefault(num, 0) + 1);
        }
        
        int[] countArr = new int[nums.length + 1];
        for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
            countArr[entry.getValue()] = entry.getKey();
        }
        
        int[] res = new int[k];
        int index = 0;
        for (int i = countArr.length - 1; i >= 0; i--) {
            if (countArr[i] <= 0) {
                continue;
            }
            res[index++] = countArr[i];
            if (index == k) {
                break;
            }
        }
        System.out.println(Arrays.toString(res));
        return res;
    }

    /**
     * TODO
     * 4. 快排思想
     */
    public int[] topKFrequent4(int[] nums, int k) {
        return null;
    }

    /**
     * 5. 对象排序
     *    8ms
     */
    public int[] topKFrequent5(int[] nums, int k) {
        if (nums == null || nums.length <= 1) {
            return nums;
        }
        Arrays.sort(nums);
        
        // 或者用 Heap
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int key = nums[i];
            int count = 1;
            while (i < nums.length - 1 && nums[i] == nums[i + 1]) {
                i++;
                count++;
            }
            
            items.add(new Item(key, count));
        }
        items.sort((o1, o2) -> o2.count - o1.count);
        
        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = items.get(i).key;
        }

        System.out.println(Arrays.toString(res));
        return res;
    }

    static class Item {
        int key;
        int count;
        
        public Item(int key, int count) {
            this.key = key;
            this.count = count;
        }
    }

    public static void main(String[] args) {
        T0347_TopKFrequentElements sut = new T0347_TopKFrequentElements();

        sut.topKFrequent5(new int[]{1,1,1,2,2,3}, 2);  // [1, 2]
        sut.topKFrequent5(new int[]{1}, 1);  // [1]
    }
    
    
}
