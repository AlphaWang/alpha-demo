package com.alphawang.algorithm.linkedlist;

import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode.com/problems/linked-list-cycle-ii/
 * Medium
 *
 * Given a linked list, return the node where the cycle begins. 
 * If there is no cycle, return null.
 *
 */
public class T0142_LinkedListCycle2 {
    /**
     * 1. Set存储访问过的节点
     *    6ms - 7%
     */
    public static ListNode getCyclePos1(ListNode head) {
        Set<ListNode> visited = new HashSet<>();
        while(head != null) {
            if (visited.contains(head)) {
                return head;
            }
            visited.add(head);
            head = head.next;
        }
        return null;
    }
    
    /**
     * 2. 快慢指针
     *    在 141 的基础上，先找到slow / fast 相遇点，再从头遍历
     *    0ms - 100%
     */
    public static ListNode getCyclePos(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        ListNode slow = head;
        ListNode fast = head;
        ListNode meet = null;
        
        while (slow != null && fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow != null && slow == fast) {
                meet = fast;
                break;
            }
        }
        
        if (meet == null) {
            return null;
        }
        fast = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        
        return fast;
    }

    public static void main(String[] args) {
        ListNode node = ListNodeCreator.create(3, 2, 0, 4, 5, 6, 7, 8, 9);

        ListNode toNode = null;
        ListNode fromNode = null;
        ListNode curr = node;
        while (curr != null) {
            if (curr.getValue() == 7) {
                toNode = curr;
            }
            if (curr.getValue() == 9) {
                fromNode = curr;
            }

            curr = curr.getNext();
        }

        fromNode.setNext(toNode);

        System.out.println(getCyclePos1(node));

        node = ListNodeCreator.create(1);
        System.out.println(getCyclePos1(node));
    }

}
