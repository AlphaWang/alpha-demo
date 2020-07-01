package com.alphawang.algorithm.design.lru;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class LRUCache {

    public static void main(String[] args) {
        LRUCache cache = new LRUCache( 2 /* capacity */ );

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

    private final ConcurrentMap<Integer, Value> concurrentMap;
    // 双向链表
    private final Node head = new Node();
    private final Node tail = new Node();
    private final int capacity;
    private int size = 0;

    private static class Value {
        private int value;  // value 可放到Node中？
        private Node node;
    }

    private static class Node {
        private Node pre;
        private Node next;
        private int key;
    }

    public LRUCache(int capacity) {
        this.concurrentMap = new ConcurrentHashMap<>(capacity);
        this.capacity = capacity;
        this.head.next = this.tail;
        this.tail.pre = this.head;
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
        Value value = concurrentMap.get(key);
        if (value == null) {
            return -1;
        }
        replaceNode(value, key);

        System.out.println("-- get " + key + " : " + value.value);
        print();
        
        return value.value;
    }

    // 将 node 放到header
    private void replaceNode(Value value, int key) {
        Node newNode = new Node();
        newNode.key = key;
        synchronized (this) {
            Node oldNode = value.node;
            Node pre = oldNode.pre;
            Node next = oldNode.next;
            pre.next = next;
            next.pre = pre;

            newNode.next = head;
            newNode.pre = head.next;


            head.next.pre = newNode;
            head.next = newNode;

            value.node = newNode;
        }
    }

    /**
     * 思路：
     * 
     * void put(int key, int val) {
     *     Node x = new Node(key, val);
     *     if (key 已存在) {
     *         把旧的数据删除；
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
        Value value1 = new Value();
        value1.value = value;

        Value pre = concurrentMap.putIfAbsent(key, value1);
        synchronized (this) {
            if (pre == null) {
                // 如果是新元素，且超过容量，则删除 tail 元素
                if (size >= capacity) {
                    // 移除值
                    concurrentMap.remove(tail.pre.key);

                    // 移除node
                    Node temp = tail.pre.pre;
                    tail.pre = temp;
                    temp.next = tail;
                } else {
                    size++;
                }

                // 新元素放入header
                Node node = new Node();
                node.key = key;
                Node last = head.pre;

                node.pre = last;
                node.next = head;

                last.next = node;
                tail.pre = node;

                value1.node = node;
            } else {
                replaceNode(value1, key);
            }
        }

        System.out.println("++ put " + key + " : " +value);
        print();
    }


    private void print() {
        Node node = head;
        while (node != null) {
            System.out.print(node.key + " > ");
            node = node.next;
        }
        System.out.println();
    }
}