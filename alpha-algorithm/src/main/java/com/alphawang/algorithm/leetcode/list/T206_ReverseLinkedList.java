package com.alphawang.algorithm.leetcode.list;

public class T206_ReverseLinkedList {

    /**
     * https://leetcode.com/problems/reverse-linked-list/
     * 
     * Input: 1->2->3->4->5->NULL
     * Output: 5->4->3->2->1->NULL
     */
    
    public static Node<Integer> reverseByRecursion(Node<Integer> head) {
        if (head == null || head.getNext() == null) {
            return head;
        }
        
        Node<Integer> newHead = reverseByRecursion(head.getNext());
//        newHead.setNext(head);
        head.getNext().setNext(head);
        head.setNext(null);
        
        return newHead;
    }

    public static void main(String[] args) {
        reverse(1, 2, 3, 4, 5);
    }

    private static void reverse(Integer... values) {
        Node<Integer> head = NodeCreator.create(values);
        System.out.println("BEFORE " + Node.format(head));
        
        head = reverseByRecursion(head);
        // head = deleteDuplicates2(head);
        
        System.out.println("AFTER  " + Node.format(head));
        System.out.println();
    }
}
