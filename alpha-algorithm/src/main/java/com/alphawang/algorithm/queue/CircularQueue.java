package com.alphawang.algorithm.queue;

import java.util.Arrays;
import java.util.stream.IntStream;

public class CircularQueue {
    
    private String[] items;
    private int capacity = 0;
    
    private int head = 0;
    private int tail = 0;
    
    public CircularQueue(int capacity) {
        this.capacity = capacity;
        this.items = new String[capacity];
    }
    
    public boolean enqueue(String item) {
        if (isFull()) {
            System.out.println("+ enqueue: FULL");
            return false;
        }
        
        items[tail] = item;
        tail = (tail + 1) % capacity;

        System.out.println("+ enqueue: " + item);
        System.out.println(this.toString());
        return true;
    }
    
    public String dequeue() {
        if (isEmpty()) {
            return null;
        }
        
        String result = items[head];
        head = (head + 1) % capacity;

        System.out.println("- dequeue: " + result);
        System.out.println(this.toString());
        return result;
    }
    
    private boolean isFull() {
        return (tail + 1) % capacity == head;
    }
    
    private boolean isEmpty() {
        return tail == head;
    }

    @Override
    public String toString() {
        return String.format("cap = %s; head = %s; tail = %s; items = %s", capacity, head, tail, Arrays.toString(items));
    }

    public static void main(String[] args) {
        CircularQueue queue = new CircularQueue(5);

        IntStream.rangeClosed(0, 6).forEach( i -> queue.enqueue("item-"+i));
        IntStream.rangeClosed(0, 6).forEach( i -> queue.dequeue());
        
        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");
        queue.enqueue("d");
        queue.enqueue("e");
    }

}
