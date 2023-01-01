package com.alphawang.algorithm.linkedlist;

public class T0234_PalindromeLinkedList {

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

    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        
        ListNode fast = head;
        ListNode slow = head;
        ListNode prev = null;

        /**
         * 快慢指针，找到中点；同时将前一半倒序
         */
        while (fast != null && fast.next != null) {
            //快慢指针找中点
            fast = fast.next.next;
            ListNode next = slow.next;

            //倒序
            slow.next = prev;
            prev = slow;
            slow = next;
        }
        
        // 如果是奇数个，跳过中间
        if (fast != null) {
            slow = slow.next;
        }

        // slow: 指向后一半链表
        // prev: 指向前一半链表
        System.out.println("slow : " + slow);
        System.out.println("fast : " + fast);
        System.out.println("prev : " + prev);
        System.out.println("slow : " + ListNode.format(slow));
        System.out.println("prev : " + ListNode.format(prev));
        
        while (slow != null && prev != null) {
            if (slow.val != prev.val) {
               return false; 
            }
            slow = slow.next;
            prev = prev.next;
        }
        
        return true;
    }


    public static void main(String[] args) {
        T0234_PalindromeLinkedList sut = new T0234_PalindromeLinkedList();
        sut.test(ListNodeCreator.create(1, 2, 3, 4, 5)); //false
        sut.test(ListNodeCreator.create(1, 2, 2, 1)); //true
        sut.test(ListNodeCreator.create(1, 2, 3, 2, 1)); //true
    }

    private void test(ListNode head) {
        System.out.println(head.format() + " -->>> " + isPalindrome(head));
    }

}
