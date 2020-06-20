package com.alphawang.algorithm.linkedlist;

public class T234_PalindromeLinkedList {

    /**
     * https://leetcode.com/problems/palindrome-linked-list/
     * 
     * Given a singly linked list, determine if it is a palindrome.
     *
     * Example 1:
     * Input: 1->2
     * Output: false 
     * 
     * Example 2:
     * Input: 1->2->2->1
     * Output: true
     */
    
    public static boolean palindrome(ListNode head) {
        if (head == null || head.getNext() == null) {
            return true;
        }
        
        ListNode fast = head;
        ListNode slow = head;
        ListNode prev = null;

        /**
         * 快慢指针，找到中点，并且将前一半倒序
         */
        while (fast != null && fast.getNext() != null) {
            fast = fast.getNext().getNext();
            ListNode next = slow.getNext();
            
            slow.setNext(prev);
            prev = slow;
            slow = next;
        }
        
        // 如果是奇数个，跳过中间
        if (fast != null) {
            slow = slow.getNext();
        }

        // slow: 指向后一半链表
        // prev: 指向前一半链表
        System.out.println("slow : " + slow);
        System.out.println("fast : " + fast);
        System.out.println("prev : " + prev);
        System.out.println("slow : " + ListNode.format(slow));
        System.out.println("prev : " + ListNode.format(prev));
        
        while (slow != null && prev != null) {
            if (slow.getValue() != prev.getValue()) {
               return false; 
            }
            slow = slow.getNext();
            prev = prev.getNext();
        }
        
        return true;
    }


    public static void main(String[] args) {
        ListNode head = ListNodeCreator.create(1, 2, 3, 4, 5);
        System.out.println(">>>>" + palindrome(head));
        
        head = ListNodeCreator.create(1, 2, 2, 1);
        System.out.println(">>>>" + palindrome(head));

        head = ListNodeCreator.create(1, 2, 3, 2, 1);
        System.out.println(">>>>" + palindrome(head));
    }

}
