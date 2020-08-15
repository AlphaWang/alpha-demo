package com.alphawang.algorithm.linkedlist;

/**
 *
 * https://leetcode.com/problems/remove-duplicates-from-sorted-list/
 *
 * Given a sorted linked list, delete all duplicates such that each element appear only once.
 *
 * Input: 1->1->2->3->3
 * Output: 1->2->3
 *
 */
public class T083_RemoveDuplicatesFromSortedList {

    public static ListNode deleteDuplicates(ListNode head) {
        ListNode current = head;
        /**
         * bug!
         *
         * 1, 1, 1, 1 --> 1, 1
         * 1, 2, 2, 2, 3 --> 1, 2, 2, 3
         */
        //        while (current != null && current.next != null) {
        //            if (current.val == current.next.val) {
        //                current.next = current.next.next;
        //            } 
        //            current = current.next;
        //        }

        while (current != null && current.next != null) {
            if (current.next.val == current.val) {
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        }

        return head;
    }

    public static ListNode deleteDuplicates2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        head.next = deleteDuplicates2(head.next);

        return head.val == head.next.val ? head.next : head;
    }

    public static void main(String[] args) {
        test(1, 2, 2, 2, 3);
        test(1, 1, 1);
    }

    private static void test(Integer... values) {
        ListNode head = ListNodeCreator.create(values);
        System.out.println("BEFORE " + ListNode.format(head));

        head = deleteDuplicates(head);
        // head = deleteDuplicates2(head);

        System.out.println("AFTER  " + ListNode.format(head));
        System.out.println();
    }

}
