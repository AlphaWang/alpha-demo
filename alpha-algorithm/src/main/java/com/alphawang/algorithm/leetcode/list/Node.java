package com.alphawang.algorithm.leetcode.list;

public class Node<T> {
    private final T val;
    private Node next;

    Node(T x) { 
        this.val = x; 
        this.next = null;
    }
    
    public T getValue() {
        return val;
    }
    
    public Node<T> getNext() {
        return next;
    }
    
    public void setNext(Node<T> next) {
        this.next = next;
    }

    

    public String toString() {
        return val.toString();
    }

    public static <T> String format(Node<T> head) {
        if (head == null) {
            return null;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append(head);
        
        while(head.getNext() != null) {
            sb.append(" -> ");
            sb.append(head.getNext());
            head = head.getNext();
        }
        
        return sb.toString();
    }
}
