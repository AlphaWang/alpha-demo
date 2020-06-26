package com.alphawang.algorithm.linkedlist;

/**
 * https://leetcode.com/problems/insertion-sort-list/
 * Medium
 * 
 */
public class T0147_InsertionSortList {

    /**
     * 1. 依次处理每个节点，对每个节点从头遍历 找到待插入的位置
     *    Time Limit Exceeded
     */
    public ListNode insertionSortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        
        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        ListNode headPrev = head;
        head = head.next;
        while (head != null) {
            ListNode prev = dummy;
            ListNode curr = dummy.next;
            while (curr != null && curr.val < head.val) {
                if (curr == head) break;
                curr = curr.next; // 待插入的位置之后
                prev = prev.next; // 待插入的位置之前
            }   
            
            if (curr == head) {
                head = curr.next;
                headPrev = curr;
                continue;
            }
            // 断开原有链接
            headPrev.next = head.next;
            
            // insert
            prev.next = head;
            head.next = curr;
            
            // 下一个节点
            head = curr.next;
            headPrev = curr;

            System.out.println(ListNode.format(dummy.next));
        }
        return dummy.next;
    }

    /**
     * 2. 依次处理每个节点，对每个节点从头遍历 找到待插入的位置
     *    优化指针；&& 只有当prev > 当前节点的时候 才需要插入。
     *    3ms - 84%
     */
    public ListNode insertionSortList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode dummy = new ListNode(Integer.MIN_VALUE);
        dummy.next = head;

        ListNode prev = head;
        ListNode curr = head.next;
        while (curr != null) {
            // 当前继节点更大，则需要插入
            if (prev.val > curr.val) {
                ListNode toInsert = dummy;
                while (toInsert != null && toInsert.next != null && toInsert.next.val < curr.val) {
                    toInsert = toInsert.next; 
                }
                
                // 暂存原来的下一个
                ListNode next = curr.next;
                prev.next = next;
                // 插入
                curr.next = toInsert.next;
                toInsert.next = curr;
                // 切换到下一个 
                curr = next; 
            }  
            // 当前继节点较小，则不处理，直接跳到下一个
            else {
                curr = curr.next;
                prev = prev.next;
            }

            System.out.println(ListNode.format(dummy.next));
        }
        return dummy.next;
    }

    public ListNode insertionSortList3(ListNode head) {
        ListNode helper=new ListNode(0);
        ListNode pre=helper;
        ListNode current=head;
        while(current!=null) {
            pre=helper;
            while(pre.next!=null&&pre.next.val<current.val) {
                pre=pre.next;
            }
            ListNode next=current.next;
            current.next=pre.next;
            pre.next=current;
            current=next;
        }
        return helper.next;
    }

    public static void main(String[] args) {
        T0147_InsertionSortList sut = new T0147_InsertionSortList();
        /*
         * Input: 4->2->1->3
         * Output: 1->2->3->4
         */
        ListNode node = ListNodeCreator.create(4, 2, 1, 3);
        System.out.println(ListNode.format(sut.insertionSortList2(node)));

        /*
         * Input: -1->5->3->4->0
         * Output: -1->0->3->4->5
         */
        node = ListNodeCreator.create(-1, 5, 3, 4, 0);
        System.out.println(ListNode.format(sut.insertionSortList2(node)));

        System.out.println(ListNode.format(sut.insertionSortList2(null)));
    }

}
