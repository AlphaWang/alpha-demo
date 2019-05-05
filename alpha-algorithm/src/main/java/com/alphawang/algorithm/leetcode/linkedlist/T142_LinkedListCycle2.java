package com.alphawang.algorithm.leetcode.linkedlist;

public class T142_LinkedListCycle2 {
    
    /**
     * https://leetcode.com/problems/linked-list-cycle-ii/
     * 
     * Given a linked list, return the node where the cycle begins. 
     * If there is no cycle, return null.
     *
     */

    public static ListNode getCyclePos(ListNode head) {
        if (head == null || head.getNext() == null) {
            return null;
        }
        
        ListNode slow = head;
        ListNode fast = head.getNext();
        
        ListNode result = null;

        while (slow != fast) {
            if (slow == null || fast == null || fast.getNext() == null) {
                return null;
            }
            
            result = slow;

            slow = slow.getNext();
            fast = fast.getNext().getNext();
        }
        
        return result;
    }

    public static void main(String[] args) {
        ListNode node = ListNodeCreator.create(3, 2, 0, 4);
        
        ListNode toNode = null;
        ListNode fromNode = null;
        ListNode curr = node;
        while (curr != null) {
            if (curr.getValue().equals(2)) {
                toNode = curr;
            }
            if (curr.getValue().equals(4)) {
                fromNode = curr;
            }

            curr = curr.getNext();
        }
        
        fromNode.setNext(toNode);
        
        System.out.println(getCyclePos(node));



        node = ListNodeCreator.create(1);
        System.out.println(getCyclePos(node));
    }
    
}
