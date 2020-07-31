package com.alphawang.algorithm.design.lru;

import com.google.common.collect.Maps;
import java.util.Map;

/**
 * https://leetcode.com/problems/lru-cache/
 * Medium
 * 
 * see 缓存替换策略：https://en.wikipedia.org/wiki/Cache_replacement_policies
 * 
 * 自己实现双向链表
 * 23ms - 38%
 */
public class T0146_LRUCache {

    private static class Node {
        int key;
        int value;
        
        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
        
        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }
    
    private Map<Integer, Node> cache;
    private Node head;
    private Node tail;
    private int capacity;
    
    public T0146_LRUCache(int capacity) {
        this.capacity = capacity;
        cache = Maps.newConcurrentMap();
        head = new Node(-2, -2); // head,tail也可不用创建Node，只当做指针来用。
        tail = new Node(-3, -3);
        head.next = tail;
        tail.prev = head;
    }

    /**
     * 思路：
     *      int get(int key) {
     *        if (key 不存在) {
     *          return -1;
     *        } else {
     *          将数据 (key, val) 提到开头；
     *          return val;
     *        }
     *      }
     */
    public int get(int key) {
        Node node = cache.get(key);
        if (node == null) {
            System.out.println(String.format("\n-- get(%s) = %s", key, null));
            return -1;
        }
        
        refreshNode(node);

        System.out.println(String.format("\n-- get(%s) = %s", key, node.value));
        print();
        
        return node.value;
    }
    
    /**
     * 思路：
     *
     * void put(int key, int val) {
     *     Node x = new Node(key, val);
     *     if (key 已存在) {
     *         把旧的数据删除； //TODO 要更新为新值！
     *         将新节点 x 插入到开头；
     *     } else {
     *         if (cache 已满) {
     *             删除链表的最后一个数据腾位置；
     *             删除 map 中映射到该数据的键；
     *         }
     *         将新节点 x 插入到开头；
     *         map 中新建 key 对新节点 x 的映射；
     *     }
     * }
     */
    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            node.value = value;
            refreshNode(node);
        } else {
            Node node = new Node(key, value);
            cache.put(key, node);
            if (cache.size() > capacity) {
                removeOldest();
            }
            refreshNode(node);
        }
        
        System.out.println(String.format("\n-- put(%s) = %s", key, value));
        print();
    }

    private void refreshNode(Node node) {
        if (node == null) {
            return;
        }
        // 删除原链 
        if (head.next != node) {
            removeNode(node);

            // 在头部建立新链
            Node first = head.next;
            head.next = node;
            node.prev = head;
            node.next = first;
            first.prev = node;
        }
        
    }
    
    private void removeNode(Node node) {
        if (node.next != null && node.prev != null) {
            Node prev = node.prev;
            Node next = node.next;
            prev.next = next;
            next.prev = prev;
        }
    }
    
    private void removeOldest() {
        cache.remove(tail.prev.key);
        removeNode(tail.prev);
    }

    private void print() {
        Node node = head;
        while (node != null) {
            System.out.print(String.format("[%s:%s]", node.key, node.value) + " > ");
//            System.out.print(String.format("[%s]", node) + " > ");
            node = node.next;
        }
        System.out.println("\nmap : " + cache);
    }

    public static void main(String[] args) {
//        test1();
        test2();
    }
    
    private static void test1() {
        T0146_LRUCache cache = new T0146_LRUCache(2 /* capacity */ );

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
        T0146_LRUCache cache = new T0146_LRUCache(2);
        cache.put(2, 1);
        cache.put(2, 2);
        
        cache.get(2); // 2
        cache.put(1, 1);
        cache.put(4, 1);
        
        cache.get(2); // -1
    }
}
