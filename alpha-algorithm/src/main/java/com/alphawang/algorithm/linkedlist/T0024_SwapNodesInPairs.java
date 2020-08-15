package com.alphawang.algorithm.linkedlist;

/**
 *  https://leetcode.com/problems/swap-nodes-in-pairs/
 *  Medium
 *  
 *  给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。
 *  你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
 */
public class T0024_SwapNodesInPairs {

    /**
     * 1. 递归
     *    0ms - 100%
     */
    public ListNode swapPairs(ListNode head) {
        // terminator
        if (head == null || head.next == null) {
            return head;
        }
        ListNode curr = head.next;
        ListNode prev = head;
        
        // drill down
        prev.next = swapPairs(curr.next);
        // process current: swap
        curr.next = prev;
        
        return curr;
    }

    /**
     * 2. 遍历
     *    0ms - 100%
     */
    public ListNode swapPairs2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode newHead = head.next;

        ListNode curr = head.next;
        ListNode prev = head;
        while (curr != null && curr != prev) {
            ListNode nextHead = curr.next;
            
            prev.next = curr.next == null ? null 
              : curr.next.next == null ? curr.next : curr.next.next;
            curr.next = prev;
            
            curr = prev.next;
            prev = nextHead;
        }
        
        return newHead;
    }

    /**
     * 3. 遍历，优化判断条件
     *    TODO 三指针
     *    - prev
     *    - p1: 
     *    - p2: curr
     */
    public ListNode swapPairs3(ListNode head) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        
        ListNode prev = dummy;
        while (head != null && head.next != null) {
            ListNode p1 = head;
            ListNode p2 = head.next;
            
            prev.next = p2;
            p1.next = p2.next;
            p2.next = p1;
            
            prev = p1;
            head = p1.next;
        }
        
        return dummy.next;
    }

    public static void main(String[] args) {
        T0024_SwapNodesInPairs sut = new T0024_SwapNodesInPairs();
        
        /*
         Input  1->2->3->4, 
         Output 2->1->4->3.
         */
        ListNode node = ListNodeCreator.create(1, 2, 3, 4);
        System.out.println(ListNode.format(node));
        
        node = sut.swapPairs3(node);
        System.out.println(ListNode.format(node));
        
        /*
         Input  1->2->3->4->5, 
         Output 2->1->4->3->5.
         */
        node = ListNodeCreator.create(1, 2, 3, 4, 5);
        System.out.println(ListNode.format(node));

        node = sut.swapPairs3(node);
        System.out.println(ListNode.format(node));
    }

}
