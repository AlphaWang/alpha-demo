package com.alphawang.algorithm.leetcode.linkedlist;

public class T082_RemoveDuplicatesFromSortedList2 {
    /**
     * https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/
     *
     * Given a sorted linked list, 
     * delete all nodes that have duplicate numbers, 
     * leaving only distinct numbers from the original list.
     *
     * Input: 1->2->3->3->4->4->5
     * Output: 1->2->5
     *
     */

    public static ListNode<Integer> deleteDuplicates(ListNode<Integer> head) {
        if (head == null) {
            return head;
        }

        if (head.getNext() != null && head.getValue() == head.getNext().getValue()) {

            while (head.getNext() != null && head.getValue() == head.getNext().getValue()) {
                head = head.getNext();
            }

            return deleteDuplicates(head.getNext());

        } else {
            head.setNext(deleteDuplicates(head.getNext()));
        }

        return head;
    }

    public static void main(String[] args) {
        remove(1, 2, 2, 2, 3);
        remove(1, 1, 1);

    }

    private static void remove(Integer... values) {
        ListNode<Integer> head = ListNodeCreator.create(values);
        System.out.println("BEFORE " + ListNode.format(head));

        head = deleteDuplicates(head);

        System.out.println("AFTER  " + ListNode.format(head));
        System.out.println();
    }

}
