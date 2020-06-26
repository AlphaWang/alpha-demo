package com.alphawang.algorithm.linkedlist;

/**
 * https://leetcode.com/problems/linked-list-cycle/
 * Easy
 * 
 * Given a linked list, determine if it has a cycle in it.
 *
 */
public class T0141_LinkedListCycle {
    /**
     * 0. Set存储走过的节点
     */
    //TBD
    
    /**
     * 1. 快慢指针
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

    /**
     * 1_1. 快慢指针，优化判断条件
     */
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

    /**
     * 1_1. 快慢指针，优化判断条件
     */
    public static boolean hasCycle3(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }

        ListNode slow = head;
        ListNode fast = head;

        while (slow != null && fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow != null && slow == fast) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        ListNode node = ListNodeCreator.create(3, 2, 0, 4);

        ListNode toNode = null;
        ListNode fromNode = null;
        ListNode curr = node;
        while (curr != null) {
            if (curr.getValue() == 2) {
                toNode = curr;
            }
            if (curr.getValue() == 4) {
                fromNode = curr;
            }

            curr = curr.getNext();
        }

        fromNode.setNext(toNode);

        System.out.println(hasCycle(node));
        System.out.println(hasCycle2(node));
        System.out.println(hasCycle3(node));
    }

}
