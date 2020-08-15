package com.alphawang.algorithm.linkedlist;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * https://leetcode-cn.com/problems/merge-k-sorted-lists/
 * Hard
 * 
 * 合并 k 个排序链表，返回合并后的排序链表
 */
public class T0023_MergeKSortedLists {

    /**
     * 1. 暴力：遍历每个链表，将元素放入数组，排序数组
     */
    // TBD

    /**
     * 2. K-1 次两链表合并
     *    127ms - 13%
     */
    public ListNode mergeKLists2(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        
        for (int i = lists.length - 1; i > 0; i--) {
            lists[i-1] = mergeTwoLists(lists[i], lists[i-1]); 
        }

        return lists[0];
    }
    
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(-1);
        ListNode tail = head;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                tail.next = l1;
                l1 = l1.next;
            } else {
                tail.next = l2;
                l2 = l2.next;
            }
            tail = tail.next;
        }
        
        if (l1 != null) tail.next = l1;
        if (l2 != null) tail.next = l2;
        
        return head.next;
    }

    /**
     * 3. 分治
     *    1ms - 100%
     */
    public ListNode mergeKLists3(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        return mergeK(lists, 0, lists.length -1);
    }
    
    public ListNode mergeK(ListNode[] lists, int start, int end) {
        if (end - start == 1) {
            return mergeTwoLists(lists[start], lists[end]);
        }
        if (start == end) {
            return lists[start];
        }
        
        int mid = start + (end - start) / 2;
        return mergeTwoLists(mergeK(lists, start, mid), mergeK(lists, mid + 1, end));
    }

    /**
     * 4. 优先队列，保存K个待处理元素
     *    6ms - 38%
     */
    public ListNode mergeKLists4(ListNode[] lists) {
        PriorityQueue<ListNode> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.val));
        // 插入 N 个头结点
        for (ListNode list : lists) {
            if (list != null)
                priorityQueue.offer(list);
        }
        
        ListNode head = new ListNode(-1);
        ListNode tail = head;
        while (!priorityQueue.isEmpty()) {
             ListNode top = priorityQueue.poll();
             tail.next = top;
             tail = tail.next;
             
             // 处理后续节点
             if (top.next != null) {
                 priorityQueue.offer(top.next);
             }
             
        }
        
        return head.next;
    }

    public static void main(String[] args) {
        T0023_MergeKSortedLists sut = new T0023_MergeKSortedLists();
        /*
         * 输入:
         * [
         *   1->4->5,
         *   1->3->4,
         *   2->6
         * ]
         * 输出: 1->1->2->3->4->4->5->6
         */
        ListNode list1 = ListNodeCreator.create(1, 4, 5);
        ListNode list2 = ListNodeCreator.create(1, 3, 4);
        ListNode list3 = ListNodeCreator.create(2, 6);
        System.out.println(sut.mergeKLists4(new ListNode[] {list1, list2, list3}).format());

        /*
         * [] 
         */
        System.out.println(sut.mergeKLists4(new ListNode[]{}));

        /*
         * [[]]
         */
        System.out.println(sut.mergeKLists4(new ListNode[]{null}));
    }

}
