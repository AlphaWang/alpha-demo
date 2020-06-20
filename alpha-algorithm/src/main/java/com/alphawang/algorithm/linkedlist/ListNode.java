package com.alphawang.algorithm.linkedlist;

public class ListNode {
    final int val;
    ListNode next;

    ListNode(int x) {
        this.val = x;
        this.next = null;
    }

    public int getValue() {
        return val;
    }

    public ListNode getNext() {
        return next;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }

    public String toString() {
        return String.valueOf(val);
    }

    public static String format(ListNode head) {
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
