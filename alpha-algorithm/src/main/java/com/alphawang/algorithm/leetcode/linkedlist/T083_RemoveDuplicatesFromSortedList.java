package com.alphawang.algorithm.leetcode.linkedlist;

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

    public static ListNode<Integer> deleteDuplicates(ListNode<Integer> head) {
        ListNode<Integer> current = head;
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

        while (current != null && current.getNext() != null) {
            if (current.getNext().getValue() == current.getValue()) {
                current.setNext(current.getNext().getNext());
            } else {
                current = current.getNext();
            }
        }

        return head;
    }

    public static ListNode<Integer> deleteDuplicates2(ListNode<Integer> head) {
        if (head == null || head.getNext() == null) {
            return head;
        }
        head.setNext(deleteDuplicates2(head.getNext()));

        return head.getValue() == head.getNext().getValue() ? head.getNext() : head;
    }

    public static void main(String[] args) {
        remove(1, 2, 2, 2, 3);
        remove(1, 1, 1);
    }

    private static void remove(Integer... values) {
        ListNode<Integer> head = ListNodeCreator.create(values);
        System.out.println("BEFORE " + ListNode.format(head));

        head = deleteDuplicates(head);
        // head = deleteDuplicates2(head);

        System.out.println("AFTER  " + ListNode.format(head));
        System.out.println();
    }

}
