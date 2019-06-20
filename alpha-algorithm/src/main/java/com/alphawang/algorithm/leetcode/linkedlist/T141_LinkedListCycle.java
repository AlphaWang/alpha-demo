package com.alphawang.algorithm.leetcode.linkedlist;

public class T141_LinkedListCycle {

    /**
     * https://leetcode.com/problems/linked-list-cycle/
     *
     * Given a linked list, determine if it has a cycle in it.
     *
     */
    public static boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (true) {
            if (slow == null || fast == null || fast.getNext() == null) {
                return false;
            }

            slow = slow.getNext();
            fast = fast.getNext().getNext();

            if (slow != null && slow == fast) {
                return true;
            }
        }
    }

    public static boolean hasCycle2(ListNode head) {
        if (head == null || head.getNext() == null) {
            return false;
        }

        ListNode slow = head;
        ListNode fast = head.getNext();

        while (slow != fast) {
            if (slow == null || fast == null || fast.getNext() == null) {
                return false;
            }

            slow = slow.getNext();
            fast = fast.getNext().getNext();
        }

        return true;
    }

    public static void main(String[] args) {
        ListNode node = ListNodeCreator.create(3, 2, 0, 4);

        ListNode toNode = null;
        ListNode fromNode = null;
        ListNode curr = node;
        while (curr != null) {
            if (curr.getValue().equals(2)) {
                toNode = curr;
            }
            if (curr.getValue().equals(4)) {
                fromNode = curr;
            }

            curr = curr.getNext();
        }

        fromNode.setNext(toNode);

        System.out.println(hasCycle(node));
        System.out.println(hasCycle2(node));
    }

}
