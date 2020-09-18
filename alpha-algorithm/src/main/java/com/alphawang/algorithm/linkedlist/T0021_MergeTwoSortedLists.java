package com.alphawang.algorithm.linkedlist;

import static com.alphawang.algorithm.linkedlist.ListHelper.format;

import com.alphawang.algorithm.linkedlist.ListHelper.ListNode;
/**
 * https://leetcode.com/problems/merge-two-sorted-lists/
 * Easy
 */
public class T0021_MergeTwoSortedLists {

    /**
     * 1. Loop
     *    0 ms
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
         if (l1 == null) return l2;
         if (l2 == null) return l1;
         
         ListNode head = new ListNode();
         ListNode cur = head;
         while (l1 != null && l2 != null) {
             if (l1.val <= l2.val) {
                 cur.next = l1;
                 l1 = l1.next;
             } else if (l1.val > l2.val) {
                 cur.next = l2;
                 l2 = l2.next;
             } 
             cur = cur.next;
         }
         
         if (l1 != null) cur.next = l1;
         if (l2 != null) cur.next = l2;
         
         return head.next;
    }

    /**
     * 2. Recursion 
     *    0 ms
     */
    public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) return null;
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        
        if (l1.val <= l2.val) {
            l1.next = mergeTwoLists2(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists2(l1, l2.next);
            return l2;
        }
    }
    

    public static void main(String[] args) {
        T0021_MergeTwoSortedLists sut = new T0021_MergeTwoSortedLists();
        
        ListNode l1 = ListHelper.create(1, 2, 4);
        ListNode l2 = ListHelper.create(1, 3, 4);
        print(sut.mergeTwoLists(l1, l2));

        l1 = ListHelper.create(1, 2, 4);
        l2 = ListHelper.create(1, 3, 4);
        print(sut.mergeTwoLists2(l1, l2));
    }

    private static void print(ListNode mergeTwoLists) {
        System.out.println(format(mergeTwoLists));
    }
 

}
