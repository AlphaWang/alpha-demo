package com.alphawang.algorithm.linkedlist;

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

    public static ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }

        System.out.println("-- removing " + ListNode.format(head));
        
        if (head.next != null && head.val == head.next.val) {
            while (head.next != null && head.val == head.next.val) {
                head = head.next;
                System.out.println(" -- move head: " + ListNode.format(head));
            }
            return deleteDuplicates(head.next);
        } else {
            System.out.println(" -- handle head: " + ListNode.format(head));
            head.next = deleteDuplicates(head.next);
        }

        System.out.println(" ++ return head: " + ListNode.format(head));
        return head;
    }

    public static void main(String[] args) {
        remove(1, 2, 2, 2, 3);
        remove(1, 1, 1);
    }

    private static void remove(Integer... values) {
        ListNode head = ListNodeCreator.create(values);
        System.out.println("BEFORE " + ListNode.format(head));

        head = deleteDuplicates(head);

        System.out.println("AFTER  " + ListNode.format(head));
        System.out.println();
    }

}
