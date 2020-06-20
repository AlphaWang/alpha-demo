package com.alphawang.algorithm.linkedlist;

public class T206_ReverseLinkedList {

    /**
     * https://leetcode.com/problems/reverse-linked-list/
     *
     * Input: 1->2->3->4->5->NULL
     * Output: 5->4->3->2->1->NULL
     */

    public static ListNode reverseByRecursion(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode newHead = reverseByRecursion(head.next);
        //        newHead.setNext(head);
        head.next.next = head;
        head.next = null;

        return newHead;
    }

    public static ListNode reverseByLoop(ListNode head) {
        ListNode newHead = null;
        ListNode curHead = head;

        while (curHead != null) {
            ListNode nextHead = curHead.getNext();
            curHead.setNext(newHead);

            newHead = curHead;
            curHead = nextHead;
        }

        return newHead;
    }

    public static void main(String[] args) {
        reverse(1, 2, 3, 4, 5);
    }

    private static void reverse(Integer... values) {
        ListNode head = ListNodeCreator.create(values);
        System.out.println("BEFORE " + ListNode.format(head));

        //        head = reverseByRecursion(head);
        head = reverseByRecursion(head);

        System.out.println("AFTER  " + ListNode.format(head));
        System.out.println();
    }
}
