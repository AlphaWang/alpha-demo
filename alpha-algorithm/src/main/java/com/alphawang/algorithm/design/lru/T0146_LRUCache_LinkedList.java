package com.alphawang.algorithm.design.lru;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * https://leetcode.com/problems/lru-cache/
 * Medium
 * 
 * 基于 JDK LinkedHashMap
 */
public class T0146_LRUCache_LinkedList extends LinkedHashMap<Integer, Integer> {

    private int capacity; 
    
    public T0146_LRUCache_LinkedList(int capacity) {
        super(capacity, 0.8F, true);
        this.capacity = capacity;
    }

    public int get(int key) {
        int res = super.getOrDefault(key, -1);
        System.out.println(String.format("--get(%s) = %s", key, res));
        return res;
    }

    public void put(int key, int value) {
        System.out.println(String.format("++put(%s) = %s", key, value));
        super.put(key, value);
    }

    @Override
    protected boolean removeEldestEntry(Entry<Integer, Integer> eldest) {
        System.out.println("checking removeEldestEntry");
        return size() > capacity;
    }

    public static void main(String[] args) {
//        test1();
        test2();
    }
    
    private static void test1() {
        T0146_LRUCache_LinkedList cache = new T0146_LRUCache_LinkedList(2 /* capacity */ );

        cache.put(1, 1);
        cache.put(2, 2);
        cache.get(1);       // returns 1
        cache.put(3, 3);    // evicts key 2

        cache.get(2);       // returns -1 (not found)
        cache.put(4, 4);    // evicts key 1
        cache.get(1);       // returns -1 (not found)
        cache.get(3);       // returns 3
        cache.get(4);       // returns 4
    }

    /**
     * ["LRUCache","put","put","get","put","put","get"]
     * [[2],[2,1],[2,2],[2],[1,1],[4,1],[2]]
     * 
     * --> [null,null,null,2,null,null,-1]
     */
    private static void test2() {
        T0146_LRUCache_LinkedList cache = new T0146_LRUCache_LinkedList(2);
        cache.put(2, 1);
        cache.put(2, 2);
        
        cache.get(2); // 2
        cache.put(1, 1);
        cache.put(4, 1);
        
        cache.get(2); // -1
    }
}
