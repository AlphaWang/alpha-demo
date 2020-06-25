package com.alphawang.algorithm.linkedlist;

/**
 * https://leetcode.com/problems/reverse-nodes-in-k-group/
 * Hard
 * 
 */
public class T0025_ReverseNodesInKGroup {

    /**
     * 1. 递归: 
     *    先遍历K个元素，同时swap;
     *    再将tail.next指向下一个递归
     * 
     *    0ms - 100%
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null) {
            return head;
        }

        ListNode next = head.next;
        ListNode curr = head;
        ListNode tail = head;
        
        ListNode tmp = head;
        // 如果子链表不足K，则无需reverse
        for (int i = 1; i < k; i++) {
            if (tmp.next == null) {
                return tail;
            }
            tmp = tmp.next;
        }
        // reverse 子链表
        for (int i = 1; i < k; i++) {
            ListNode newNext = next.next;
            next.next = curr;
            
            curr = next;
            next = newNext;
        }
        
        tail.next = reverseKGroup(next, k);
        
        return curr;
    }

    /**
     * 1_1. 递归: 优化swap子链表
     *    先遍历K个元素，同时swap;
     *    再将tail.next指向下一个递归
     *
     *    0ms - 100%
     */
    public ListNode reverseKGroup1_1(ListNode head, int k) {
        if (head == null) {
            return head;
        }
        
        ListNode end = head;

        for (int i = 1; i < k; i++) {
            end = end.next;
            if (end == null) {
                return head; // 不足K, 无需翻转
            }
        }
        ListNode next = end.next;
        
        ListNode reversed = reverse(head, end);
        head.next = reverseKGroup1_1(next, k);
        
        return reversed;
    }
    
    public ListNode reverse(ListNode start, ListNode end) {
        ListNode next = start.next;
        start.next = null;
        
        while (start != end) {
            ListNode newNext = next.next;
            next.next = start;
            
            start = next;
            next = newNext;
        }
        
        return end;
    }

    public static void main(String[] args) {
        T0025_ReverseNodesInKGroup sut = new T0025_ReverseNodesInKGroup();
        ListNode node = ListNodeCreator.create(1, 2, 3, 4, 5);
        ListNode end = node;
        for (int i = 0; i < 4; i++) {
            end = end.next;
        }
        System.out.println(ListNode.format(sut.reverse(node, end)));
        System.out.println("------");
        
        /*
                  Given this linked list: 1->2->3->4->5
            For k = 2, you should return: 2->1->4->3->5
            For k = 3, you should return: 3->2->1->4->5
         */
        node = ListNodeCreator.create(1, 2, 3, 4, 5);
        System.out.println(ListNode.format(sut.reverseKGroup1_1(node, 2)));

        node = ListNodeCreator.create(1, 2, 3, 4, 5);
        System.out.println(ListNode.format(sut.reverseKGroup1_1(node, 3)));
        
        
    }

}
