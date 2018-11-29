package com.alphawang.algorithm.leetcode.list;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class T082_RemoveDuplicatesFromSortedList2 {
    /**
     * https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/
     *
     * Given a sorted linked list, 
     * delete all nodes that have duplicate numbers, 
     * leaving only distinct numbers from the original list.
     */

    public static Node<Integer> deleteDuplicates(Node<Integer> head) {
        if (head == null) {
            return head;
        }
        
        if (head.getNext() != null && head.getValue() == head.getNext().getValue()) {
            
            while (head.getNext() != null && head.getValue() == head.getNext().getValue()) {
                head = head.getNext();
            }
            
            return deleteDuplicates(head.getNext());
            
        } else {
            head.setNext(deleteDuplicates(head.getNext()));
        }

        return head;
    }

    public static void main(String[] args) {
        remove(1, 2, 2, 2, 3);
        remove(1, 1,1);

    }

    private static void remove(Integer... values) {
        Node<Integer> head = NodeCreator.create(values);
        System.out.println("BEFORE " + Node.format(head));
        head = deleteDuplicates(head);
        System.out.println("AFTER  " + Node.format(head));
    }
    

}
