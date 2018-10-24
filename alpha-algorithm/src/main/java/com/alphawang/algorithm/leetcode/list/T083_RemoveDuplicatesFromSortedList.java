package com.alphawang.algorithm.leetcode.list;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 
 * https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list
 * https://leetcode.com/problems/remove-duplicates-from-sorted-list/
 * 
 * 
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class T083_RemoveDuplicatesFromSortedList {

    public static ListNode deleteDuplicates(ListNode head) {
        ListNode current = head;
        /**
         * bug!
         * 
         * 1, 1, 1, 1 --> 1, 1
         * 1, 2, 2, 2, 3 --> 1, 2, 2, 3
         */
//        while (current != null && current.next != null) {
//            if (current.val == current.next.val) {
//                current.next = current.next.next;
//            } 
//            current = current.next;
//        }

        while (current != null && current.next != null) {
            if (current.next.val == current.val) {
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        }
        
        return head;
    }

    public static void main(String[] args) {
        List<ListNode> nodes = Stream.of(1, 2, 2, 2, 3)
            .map(i -> new ListNode(i))
            
            .collect(Collectors.toList());
        
        for (int i = 0; i < nodes.size() - 1; i++) {
            ListNode current = nodes.get(i);
            ListNode next = nodes.get(i +1);
            current.next = next;
        }
        
        ListNode head = nodes.get(0);
        
        System.out.println("BEFORE " + head);
        head = deleteDuplicates(head);
        System.out.println("AFTER " + head);
        
        
    }
    

    public static class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
      
      public String toString() {
          StringBuilder sb = new StringBuilder();
          sb.append(val);
          
          if (next != null) {
              sb.append(" -> ");
              sb.append(next.toString());
          }
          return sb.toString();
      }
  }
}
