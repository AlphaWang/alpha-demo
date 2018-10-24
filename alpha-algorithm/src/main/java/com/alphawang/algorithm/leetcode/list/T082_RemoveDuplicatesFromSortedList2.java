package com.alphawang.algorithm.leetcode.list;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class T082_RemoveDuplicatesFromSortedList2 {
    /**
     * https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/
     *
     * Given a sorted linked list, 
     * delete all nodes that have duplicate numbers, 
     * leaving only distinct numbers from the original list.
     */

    public static ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }
        
        if (head.next != null && head.val == head.next.val) {
            while (head.next != null && head.val == head.next.val) {
                head = head.next;
            }
            return deleteDuplicates(head.next);
        } else {
            head.next = deleteDuplicates(head.next);
        }

        return head;
    }

    public static void main(String[] args) {
        List<ListNode> nodes = Stream.of(1, 2, 2, 2, 3)
            .map(i -> new ListNode(i))

            .collect(Collectors.toList());

        for (int i = 0; i < nodes.size() - 1; i++) {
            ListNode current = nodes.get(i);
            ListNode next = nodes.get(i +1);
            current.next = next;
        }
        ListNode head = nodes.get(0);

        System.out.println("BEFORE " + head);
        head = deleteDuplicates(head);
        System.out.println("AFTER " + head);


    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(val);

            if (next != null) {
                sb.append(" -> ");
                sb.append(next.toString());
            }
            return sb.toString();
        }
    }

}
