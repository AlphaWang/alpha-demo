package com.alphawang.algorithm.lru;

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
    private final Node head = new Node();
    private final Node tail = new Node();
    private final int capacity;
    private int size = 0;

    private static class Value {
        private int value;
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

    public int get(int key) {
        Value value = concurrentMap.get(key);
        if (value == null) {
            return -1;
        }
        replaceNode(value, key);
        return value.value;
    }

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

    public void put(int key, int value) {
        Value value1 = new Value();
        value1.value = value;

        Value pre = concurrentMap.putIfAbsent(key, value1);
        synchronized (this) {
            if (pre == null) {
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
    }
}