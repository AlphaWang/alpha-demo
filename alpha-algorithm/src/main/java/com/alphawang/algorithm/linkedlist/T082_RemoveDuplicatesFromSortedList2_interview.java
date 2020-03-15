package com.alphawang.algorithm.linkedlist;

/**
 * TODO: has bugs.
 */
public class T082_RemoveDuplicatesFromSortedList2_interview {

    /**
     * https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/
     *
     * Given a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the
     * original list.
     *
     * Input: 1->2->3->3->4->4->5 Output: 1->2->5
     */

    static class ListNode<T> {

        private int index;
        private final T val;
        private ListNode next;

        ListNode(T x, int index) {
            this.index = index;
            this.val = x;
            this.next = null;
        }

        public T getValue() {
            return val;
        }

        public ListNode<T> getNext() {
            return next;
        }

        public void setNext(ListNode<T> next) {
            this.next = next;
        }

        public String toString() {
            return val != null ? index + ":" + val.toString() : "(null)";
        }
    }

    public static ListNode<Integer> removeDuplicates(ListNode<Integer> head) {
        ListNode<Integer> newHead = new ListNode<>(0, -1);
        ListNode<Integer> dump = newHead;
        while (head != null) {
            int currentVal = head.getValue();
            if (head.getNext() == null) {
                newHead.setNext(head);
                break;
            }
            int nextVal = head.getNext().getValue();
            if (currentVal != nextVal) {
                ListNode<Integer> n = head.getNext().getNext();
                if (n != null && nextVal != n.getValue()) {
                    newHead.setNext(new ListNode<>(currentVal, 0));
                    newHead = newHead.getNext();
                } else if (n == null) {
                    newHead.setNext(head);
                    newHead = newHead.getNext();
                }
            } else {
                head = head.getNext();
            }
            if (head.getNext() != null) {
                head = head.getNext();
            }
        }
        return dump.getNext();
    }

    public static void main(String[] args) {
        //  2 2 3 4
        //  2 3 3 4 4 4 5 6
        //  2 3 4 4 4
        //  2 2 2 4 4 4
        int[] arr1 = new int[]{2, 3, 3, 4, 4, 4, 5, 6};  //4 5 6
        int[] arr2 = new int[]{2, 3, 4, 4, 4};
        int[] arr3 = new int[]{2, 2, 2, 4, 4, 4};
        int[] arr4 = new int[]{2, 2, 3, 4};
        
        ListNode<Integer> result = removeDuplicates(createNode(arr1));
        print(result);
    }

    private static void print(ListNode<Integer> result) {
        while (result != null) {
            System.out.println(result.getValue());
            result = result.getNext();
        }
    }

    private static ListNode<Integer> createNode(int[] arr) {
        ListNode<Integer> node = new ListNode<>(0, -1);
        ListNode<Integer> demp = node;
        int index = 0;
        for (int a : arr) {
            node.setNext(new ListNode<>(a, index++));
            node = node.getNext();
        }
        return demp.getNext();
    }
}


