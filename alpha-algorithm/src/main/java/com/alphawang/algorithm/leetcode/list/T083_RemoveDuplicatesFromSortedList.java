package com.alphawang.algorithm.leetcode.list;

/**
 * 
 * https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list
 * https://leetcode.com/problems/remove-duplicates-from-sorted-list/
 * 
 * Given a sorted linked list, delete all duplicates such that each element appear only once.
 * 
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class T083_RemoveDuplicatesFromSortedList {

    public static Node<Integer> deleteDuplicates(Node<Integer> head) {
        Node<Integer> current = head;
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

    public static Node<Integer> deleteDuplicates2(Node<Integer> head) {
        if (head == null || head.getNext() == null) {
            return head;
        }
        head.setNext(deleteDuplicates2(head.getNext()));
        
        return head.getValue() == head.getNext().getValue() ? head.getNext() : head;
    }

    public static void main(String[] args) {
        remove(1, 2, 2, 2, 3);
        remove(1, 1,1);
    }
    
    private static void remove(Integer... values) {
        Node<Integer> head = NodeCreator.create(values);
        System.out.println("BEFORE " + Node.format(head));
        
        head = deleteDuplicates(head);
        // head = deleteDuplicates2(head);
        
        System.out.println("AFTER  " + Node.format(head));
        System.out.println();
    }
    
}
