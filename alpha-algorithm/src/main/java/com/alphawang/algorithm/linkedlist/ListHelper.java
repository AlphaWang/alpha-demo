package com.alphawang.algorithm.linkedlist;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListHelper {

    public static String format(ListNode head) {
        if (head == null) {
            return "(null node)";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(head);

        while (head.next != null) {
            sb.append(" -> ");
            sb.append(head.next);
            head = head.next;
        }

        if (head.next == null) {
            sb.append(" -> (null node)");
        }

        return sb.toString();
    }

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
        
        public String toString() {
            return String.valueOf(val);
        }

    }

    public static ListNode create(Integer... values) {
        List<ListNode> nodes = Stream.of(values)
                                     .map(i -> new ListNode(i))
                                     .collect(Collectors.toList());

        for (int i = 0; i < nodes.size() - 1; i++) {
            ListNode current = nodes.get(i);
            ListNode next = nodes.get(i + 1);
            current.next = next;
        }

        ListNode head = nodes.get(0);
        return head;
    }
}
