package com.alphawang.algorithm.linkedlist;

public class T021_MergeSortedLists {

    /**
     * https://leetcode.com/problems/merge-two-sorted-lists/
     *
     * Merge two sorted linked lists and return it as a new list. 
     * The new list should be made by splicing together the nodes of the first two lists.
     *
     * Input: 1->2->4, 1->3->4
     * Output: 1->1->2->3->4->4
     *
     */
    public static ListNode mergeTwoLists(ListNode<Integer> l1, ListNode<Integer> l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        ListNode head;
        ListNode tail;

        if (l1.getValue() <= l2.getValue()) {
            head = l1;
            l1 = l1.getNext();
        } else {
            head = l2;
            l2 = l2.getNext();
        }
        tail = head;

        while (l1 != null && l2 != null) {
            if (l1.getValue() <= l2.getValue()) {
                tail.setNext(l1);
                tail = tail.getNext();
                l1 = l1.getNext();
            } else {
                tail.setNext(l2);
                tail = tail.getNext();
                l2 = l2.getNext();
            }
        }

        if (l1 != null) {
            tail.setNext(l1);
        }
        if (l2 != null) {
            tail.setNext(l2);
        }

        return head;
    }
    
    // 递归

    public static void main(String[] args) {
        testMerge(new Integer[] { 1, 2, 4 }, new Integer[] { 1, 3, 4 });
        testMerge(new Integer[] { 1, 2, 5 }, new Integer[] { 3, 4, 6 });
    }

    private static void testMerge(Integer[] array1, Integer[] array2) {
        ListNode l1 = ListNodeCreator.create(array1);
        ListNode l2 = ListNodeCreator.create(array2);

        ListNode result = mergeTwoLists(l1, l2);
        System.out.println(ListNode.format(result));
    }

}
