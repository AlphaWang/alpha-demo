package com.alphawang.algorithm.dp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * https://leetcode.com/problems/task-scheduler/
 * Medium
 * 
 * 给定一个用字符数组表示的 CPU 需要执行的任务列表。
 * 其中包含使用大写的 A - Z 字母表示的26 种不同种类的任务。
 * 任务可以以任意顺序执行，并且每个任务都可以在 1 个单位时间内执行完。
 * 
 * CPU 在任何一个单位时间内都可以执行一个任务，或者在待命状态。
 *
 * 然而，两个相同种类的任务之间必须有长度为 n 的冷却时间，
 * 因此至少有连续 n 个单位时间内 CPU 在执行不同的任务，或者在待命状态。
 *
 * 你需要计算完成所有任务所需要的最短时间。
 */
public class T0621_TaskScheduler {

    /**
     * 1. 计数，先把不同的任务排好
     *    --> 思路错误！反例：'A','A','A','A','A','A','B','C','D','E','F','G'
     */
    public int leastInterval(char[] tasks, int n) {
        Map<Character, Integer> count = new HashMap<>();
        for (char task : tasks) {
            count.put(task, count.getOrDefault(task, 0) + 1);
        }
        System.out.println(count);
        
        int num = 0;
        while (count.size() > 0) {
            if (count.size() > n) {
                num += count.size();
            } else {
                int remainCount = getRemainCount(count);
                if ( remainCount <= n) {
                    num += remainCount;
                } else {
                    num += n + 1;
                }
            }
           
            System.out.println("-- " + num);
            Iterator<Entry<Character, Integer>> iterator = count.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<Character, Integer> next = iterator.next();
                if (next.getValue() == 1) {
                    iterator.remove();
                } else {
                    next.setValue(next.getValue() - 1);
                }
            }
        }
        
        System.out.println(count);
        
        return num;
    }
    
    private int getRemainCount(Map<Character, Integer> count) {
        return count.values().stream().reduce(0, Integer::sum);
    }

    /**
     * 2. 排序，先排数目最多的任务  
     *    不通过
     */
    public int leastInterval2(char[] tasks, int n) {
        int[] count = new int[26];
        for (char c : tasks) {
            count[c - 'A']++;
        }
        // 从小到大排列
        Arrays.sort(count);
        int maxCount = count[25];
        int idleSlots = (maxCount - 1) * n;
        
        int res = maxCount + idleSlots;
        // 扣掉数目最多的任务，往idle里放
        for (int i = 24; i >= 0; i--) {
            if (count[i] == 0) {
                continue;
            }
            // 如果 == 数目最多
            if (count[i] == maxCount) {
                if (idleSlots >= maxCount - 1) {
                    idleSlots -= maxCount - 1;
                    res++;
                } else {
                    res += count[i];
                    idleSlots = 0;
                }
            } 
            // 如果 < 数目最多，扣减idleSlots
            else {
                int addTask = idleSlots - count[i];
                if (addTask >= 0) {
                    idleSlots = addTask;
                } else {
                    res -= addTask;
                }
            }
        }
        
        return res;
    }

    /**
     * 2_1. 排序，先排数目最多的任务：计算 idleSlot 个数  
     *    4ms - 67% 
     */
    public int leastInterval2_1(char[] tasks, int n) {
        int[] count = new int[26];
        for (char c : tasks) {
            count[c - 'A']++;
        }
        // 从小到大排列
        Arrays.sort(count);
        int maxCount = count[25];
        int maxIdlePerBucket = maxCount - 1;
        int idleSlots = maxIdlePerBucket * n;

        for (int i = 24; i >= 0; i--) {
            if (count[i] == 0) {
                continue;
            }
            idleSlots -= Math.min(count[i], maxIdlePerBucket);
        }

        /**
         * 如果 idleSlots 有剩余，则 idleSlots + tasks.length
         * 如果 idleSlots 没有剩余，则表示无需插入idle即可执行完成
         */
        return idleSlots > 0 ? idleSlots + tasks.length : tasks.length;
    }
    
    public static void main(String[] args) {
        /*
         * 输入：tasks = ["A","A","A","B","B","B"], n = 2
         * 输出：8
         * 解释：A -> B -> (待命) -> A -> B -> (待命) -> A -> B.
         */
        test(new char[] {'A','A','A','B','B','B'}, 2);
        
        // 6
        test(new char[] {'A','A','A','B','B','B'}, 0);
        
        // 7
        test(new char[] {'A','B','C','D','A','B','V'}, 3);
        
        // 8
        test(new char[] {'A','A','A','B','B','B', 'C'}, 2);
        
        // 16
        test(new char[] {'A','A','A','A','A','A','B','C','D','E','F','G'}, 2);
    }
    
    private static void test(char[] tasks, int n) {
        T0621_TaskScheduler sut = new T0621_TaskScheduler();
        System.out.println(Arrays.toString(tasks));
        System.out.println(n + " --> " + sut.leastInterval2_1(tasks, n));
    }

}
