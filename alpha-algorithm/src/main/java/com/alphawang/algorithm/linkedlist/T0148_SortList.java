package com.alphawang.algorithm.linkedlist;

/**
 * https://leetcode.com/problems/sort-list/
 * Medium
 * 
 * Sort a linked list in O(n log n) time using constant space complexity.
 */
public class T0148_SortList {

    /**
     * 0. 同147，插入排序
     *    300ms - 8%
     */
    //TBD

    /**
     * 1. 归并排序
     *    8ms - 20%
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode slow = head;
        ListNode fast = head.next;
        while (slow != null && fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        System.out.println(String.format("---list = %s, mid = %s", ListNode.format(head), slow.next));
        ListNode mid = slow.next;
        slow.next = null;
        
        ListNode first = sortList(head);
        ListNode second = sortList(mid);
        
        return merge2(first, second);
    }

    // 合并两个有序链表
    // 21: https://leetcode.com/problems/merge-two-sorted-lists/
    private ListNode merge(ListNode first, ListNode second) {
        if (first == null) return second;
        if (second == null) return first;
        if (first.val < second.val) {
            first.next = merge(first.next, second);
            return first;
        } else {
            second.next = merge(first, second.next);
            return second;
        }
    }

    /**
     * 2. 归并排序，merge 非递归
     *    3ms - 97%
     */
    private ListNode merge2(ListNode first, ListNode second) {
        if (first == null) return second;
        if (second == null) return first;
        ListNode head = new ListNode(-1);
        ListNode tail = head; 
        while (first != null && second != null) {
            if (first.val < second.val) {
                tail.next = first;
                first = first.next;
            } else {
                tail.next = second;
                second = second.next;
            }
            tail = tail.next;
        }
        
        if (first != null) tail.next = first;
        if (second != null) tail.next = second;
        
        return head.next;
    }


    public static void main(String[] args) {
        T0148_SortList sut = new T0148_SortList();
        /*
         * Input: 4->2->1->3
         * Output: 1->2->3->4
         */
        ListNode node = ListNodeCreator.create(4, 2, 1, 3);
        System.out.println(ListNode.format(sut.sortList(node)));

        /*
         * Input: -1->5->3->4->0
         * Output: -1->0->3->4->5
         */
        node = ListNodeCreator.create(-1, 5, 3, 4, 0);
        System.out.println(ListNode.format(sut.sortList(node)));

        /*
         * Input: null
         * Output: null
         */
        System.out.println(ListNode.format(sut.sortList(null)));
    }
    
    
}
