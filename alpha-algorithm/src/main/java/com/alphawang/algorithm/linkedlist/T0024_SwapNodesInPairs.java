package com.alphawang.algorithm.linkedlist;

/**
 *  https://leetcode.com/problems/swap-nodes-in-pairs/
 *  Medium
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
     */
    public ListNode swapPairs2(ListNode head) {
        
        return head;
    }

    public static void main(String[] args) {
        T0024_SwapNodesInPairs sut = new T0024_SwapNodesInPairs();
        
        /*
         Input  1->2->3->4, 
         Output 2->1->4->3.
         */
        ListNode node = ListNodeCreator.create(1, 2, 3, 4);
        System.out.println(ListNode.format(node));
        
        node = sut.swapPairs(node);
        System.out.println(ListNode.format(node));
    }

}
