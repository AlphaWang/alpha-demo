package com.alphawang.algorithm.leetcode.linkedlist;

public class T206_ReverseLinkedList {

    /**
     * https://leetcode.com/problems/reverse-linked-list/
     * 
     * Input: 1->2->3->4->5->NULL
     * Output: 5->4->3->2->1->NULL
     */
    
    public static ListNode<Integer> reverseByRecursion(ListNode<Integer> head) {
        if (head == null || head.getNext() == null) {
            return head;
        }
        
        ListNode<Integer> newHead = reverseByRecursion(head.getNext());
//        newHead.setNext(head);
        head.getNext().setNext(head);
        head.setNext(null);
        
        return newHead;
    }
    
    public static ListNode<Integer> reverseByLoop(ListNode<Integer> head) {
        ListNode<Integer> newHead = null;
        ListNode<Integer> curHead = head;
        
        while (curHead != null) {
            ListNode<Integer> nextHead = curHead.getNext();
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
        ListNode<Integer> head = ListNodeCreator.create(values);
        System.out.println("BEFORE " + ListNode.format(head));
        
//        head = reverseByRecursion(head);
         head = reverseByLoop(head);
        
        System.out.println("AFTER  " + ListNode.format(head));
        System.out.println();
    }
}
