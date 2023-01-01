package com.alphawang.algorithm.linkedlist;

public class ListNode {
    public int val;
    public ListNode next;

    ListNode(int x) {
        this.val = x;
        this.next = null;
    }

    @Deprecated
    public int getValue() {
        return val;
    }

    @Deprecated
    public ListNode getNext() {
        return next;
    }

    @Deprecated
    public void setNext(ListNode next) {
        this.next = next;
    }

    public String toString() {
        return String.valueOf(val);
    }

    public static String format(ListNode head) {
        if (head == null) {
            return "(null)";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(head);

        while (head.getNext() != null) {
            sb.append(" -> ");
            sb.append(head.getNext());
            head = head.getNext();
        }

        if (head.getNext() == null) {
            sb.append(" -> (null)");
        }

        return sb.toString();
    }

    public String format() {
        ListNode node = this;
        StringBuilder sb = new StringBuilder();
        sb.append(node);

        while (node.getNext() != null) {
            sb.append(" -> ");
            sb.append(node.getNext());
            node = node.getNext();
        }

        if (node.getNext() == null) {
            sb.append(" -> (null)");
        }

        return sb.toString();
    }
}
