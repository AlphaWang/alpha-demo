package com.alphawang.algorithm.linkedlist;

public class ListNode<T> {
    private final T val;
    private ListNode next;

    ListNode(T x) {
        this.val = x;
        this.next = null;
    }

    public T getValue() {
        return val;
    }

    public ListNode<T> getNext() {
        return next;
    }

    public void setNext(ListNode<T> next) {
        this.next = next;
    }

    public String toString() {
        return val != null ? val.toString() : "(null)";
    }

    public static <T> String format(ListNode<T> head) {
        if (head == null) {
            return "(null node)";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(head);

        while (head.getNext() != null) {
            sb.append(" -> ");
            sb.append(head.getNext());
            head = head.getNext();
        }

        if (head.getNext() == null) {
            sb.append(" -> (null node)");
        }

        return sb.toString();
    }
}
