package com.alphawang.algorithm.linkedlist;

public class T019_RemoveNthNode {
    /**
     * https://leetcode.com/problems/remove-nth-node-from-end-of-list/
     *
     * Given a linked list, remove the n-th node from the end of list and return its head.
     */
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode start = new ListNode(null);
        start.setNext(head);
        ListNode slow = start;
        ListNode fast = start;
        
        // Move fast forward; fast - slow = n
        for (int i = 1; i <= n+1; i++) { // TODO 注意移动 n+1 次
            fast = fast.getNext();
        }
        System.out.println("-- move fast to: " + fast);
        
        // Move fast to end.
        while (fast != null) {
            fast = fast.getNext();
            slow = slow.getNext();
        }

        System.out.println("-- move fast to end: " + fast);
        System.out.println("-- move slow to end - n: " + slow);
        
        // Remove node
        slow.setNext(slow.getNext().getNext());
        
        return head;
    }

    public static void main(String[] args) {
        ListNode listNode = ListNodeCreator.create(0, 1, 2, 3, 4, 5, 6);
        
        listNode = removeNthFromEnd(listNode, 2);
        System.out.println(ListNode.format(listNode));

    }

}
