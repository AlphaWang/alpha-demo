package com.alphawang.algorithm.linkedlist;

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
public class T0234_PalindromeLinkedList {


    /**
     * 快慢指针，找到中点；同时将前一半倒序
     */
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        
        ListNode fast = head;
        ListNode slow = head;
        ListNode prev = null;

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

    /**
     * 递归解法
     * 先将node递归到最右边，再与最左边对比
     */
    public boolean isPalindrome2(ListNode head) {
        left = head;
        return traverse(head);
    }

    ListNode left;
    private boolean traverse(ListNode node) {
        if (node == null) {
            return true;
        }
        boolean res = traverse(node.next);
        res = res && left.val == node.val;

        left = left.next;
        return res;
    }


    public static void main(String[] args) {
        T0234_PalindromeLinkedList sut = new T0234_PalindromeLinkedList();
        sut.test(ListNodeCreator.create(1, 2, 3, 4, 5)); //false
        sut.test(ListNodeCreator.create(1, 2, 2, 1)); //true
        sut.test(ListNodeCreator.create(1, 2, 3, 2, 1)); //true
    }

    private void test(ListNode head) {
        System.out.println(head.format() + " -->>> " + isPalindrome2(head));
    }

}
