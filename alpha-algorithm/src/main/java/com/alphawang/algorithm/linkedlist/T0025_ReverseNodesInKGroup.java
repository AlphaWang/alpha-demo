package com.alphawang.algorithm.linkedlist;

/**
 * https://leetcode.com/problems/reverse-nodes-in-k-group/
 * Hard
 * 
 * 给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
 * k 是一个正整数，它的值小于或等于链表的长度。
 * 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
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

    /**
     * 2. 循环
     */
    public ListNode reverseKGroup2(ListNode head, int k) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        
        ListNode prev = dummy;
        while (prev != null) {
            // 翻转 (prev, last] 之间的元素
            ListNode last = prev;
            for (int i = 0; i <= k; i++) {
                last = last.next;
                // 如果不足 k，则不翻转
                if (last == null) {
                    prev = null;
                    break;
                }
            }
            
            ListNode start = prev.next;
            ListNode curr = prev.next.next;
            
            while (curr != last) {
                ListNode newNext = curr.next;
                curr.next = start;
                
                start = curr;
                curr = newNext;
            }
           
            
            prev = last;
        }
        
        
        return dummy.next;
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
        System.out.println("------ tested reverse util");
        
        /*
                  Given this linked list: 1->2->3->4->5
            For k = 2, you should return: 2->1->4->3->5
            For k = 3, you should return: 3->2->1->4->5
         */
        test(ListNodeCreator.create(1, 2, 3, 4, 5), 2);
        test(ListNodeCreator.create(1, 2, 3, 4, 5), 3);
        
    }
    
    private static void test(ListNode head, int k) {
        T0025_ReverseNodesInKGroup sut = new T0025_ReverseNodesInKGroup();
        System.out.println(ListNode.format(head) + " --> ");
        System.out.println(ListNode.format(sut.reverseKGroup1_1(head, k)));
    }

}
