package com.alphawang.algorithm.linkedlist;

/**
 * https://leetcode.com/problems/reverse-linked-list/
 *
 * Input: 1->2->3->4->5->NULL
 * Output: 5->4->3->2->1->NULL
 */
public class T0206_ReverseLinkedList {

    /**
     * 1. 递归
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

    /**
     * 2. 循环
     */
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

    /**
     * 2. 循环 + dummy header
     */
    public static ListNode reverseByLoop2(ListNode head) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        
        ListNode prev = dummy.next;
        ListNode curr = dummy.next.next;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            
        }
        
        
        return dummy.next;
    }

    public static void main(String[] args) {
        test(1, 2, 3, 4, 5);
    }

    private static void test(Integer... values) {
        ListNode head = ListNodeCreator.create(values);
        System.out.println("BEFORE " + ListNode.format(head));

        //        head = reverseByRecursion(head);
        head = reverseByLoop2(head);

        System.out.println("AFTER  " + ListNode.format(head));
        System.out.println();
    }
    
    
}
