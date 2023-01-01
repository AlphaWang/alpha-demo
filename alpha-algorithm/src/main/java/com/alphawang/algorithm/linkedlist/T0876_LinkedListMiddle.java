package com.alphawang.algorithm.linkedlist;

public class T0876_LinkedListMiddle {

    /**
     * https://leetcode.com/problems/middle-of-the-linked-list/
     *
     * Given a non-empty, singly linked list with head node head, return a middle node of linked list.
     */

    public static ListNode findMiddleByArray(ListNode head) {
        ListNode[] array = new ListNode[100];

        int i = 0;
        while (head.getNext() != null) {
            array[i++] = head;
            head = head.getNext();
        }
        return array[i / 2];
    }

    public static ListNode findMiddleBySlowFast(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.getNext() != null) {
            slow = slow.getNext();
            fast = fast.getNext().getNext();
        }

        return slow;
    }

    public static void main(String[] args) {
        findMiddle(1, 2, 3);
        findMiddle(1, 2, 3, 4, 5, 6);
    }

    private static void findMiddle(Integer... array1) {
        ListNode l1 = ListNodeCreator.create(array1);
        System.out.println(findMiddleByArray(l1));
        System.out.println(findMiddleBySlowFast(l1));
        System.out.println("----");
    }
}
