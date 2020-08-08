package com.alphawang.algorithm.linkedlist;

import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode.com/problems/intersection-of-two-linked-lists/
 * 找到两个单链表相交的起始节点。
 */
public class T0160_IntersectionOfTwoLinkedLists {

    /**
     * 1. Set存储已访问节点
     *    O(N) 空间复杂度：不符合要求
     *    
     *    7ms - 39%
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        Set<ListNode> visited = new HashSet<>();
        while(headA != null) {
            visited.add(headA);
            headA = headA.next;
        }
        
        while (headB != null) {
            if (visited.contains(headB)) {
                return headB;
            }
            headB = headB.next;
        }
        
        return null;
    }

    /**
     * 2. 双指针
     *    lengthA + lengthB == lengthB + lengthA
     * 
     *    空间：O(1)
     *    1ms - 99%
     */
    public ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null; 
        ListNode indexA = headA;
        ListNode indexB = headB;
        
        boolean switchedA = false;
        boolean switchedB = false;

        /*
         * 指针只能交换一次吗？
         * -> 非也，此处逻辑可简化
         */
        while ((indexA != null || !switchedA) && (indexB != null || !switchedB)) {
             if (indexA == null) {
                 indexA = headB;
                 switchedA = true;
             }
             if (indexB == null) {
                 indexB = headA;
                 switchedB = true;
             }
             
             if (indexA == indexB) {
                 return indexA;
             }
             indexA = indexA.next;
             indexB = indexB.next;
        }
        
        return null;
    }

    /**
     * 2-2: 双指针，优化判断逻辑
     *     1ms - 99%
     */
    public ListNode getIntersectionNode2_2(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        ListNode indexA = headA;
        ListNode indexB = headB;

        /*
         * 逻辑简化！
         * 注意 null == null --> true
         */
        while (indexA != indexB) {
            System.out.println(String.format("1- A: %s, B: %s", indexA, indexB));
            indexA = (indexA == null) ? headB : indexA.next;
            indexB = (indexB == null) ? headA : indexB.next;
            System.out.println(String.format("2- A: %s, B: %s", indexA, indexB));
        }

        return indexA;
    }

    /**
     * 3: 按长度对齐终点
     *    1ms - 99%
     */
    public ListNode getIntersectionNode3(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        int lengthA = getLength(headA);
        int lengthB = getLength(headB);
        
        if (lengthA > lengthB) {
            int steps = lengthA - lengthB;
            while (steps-- > 0) {
                headA = headA.next;
            }
        }
        if (lengthB > lengthA) {
            int steps = lengthB - lengthA;
            while (steps-- > 0) {
                headB = headB.next;
            }
        }
        
        while (headA != null && headB != null) {
            if (headA == headB) {
                return headA;
            }
            headA = headA.next;
            headB = headB.next;
        }
        
        return null;
    }
    
    private int getLength(ListNode node) {
        int length = 0;
        while (node != null) {
            length++;
            node = node.next;
        }
        return length;
    }

    public static void main(String[] args) {
        ListNode headA = ListNodeCreator.create(4, 1, 8, 4, 5);
        ListNode headB = ListNodeCreator.create(5, 0, 1);
        
        ListNode intersection = find(headA, 8);
        ListNode prevB = find(headB, 1);
        prevB.next = intersection;
        
        // 8
        test(headA, headB);
        // null
        test(ListNodeCreator.create(1, 2, 3), ListNodeCreator.create(4, 5));
    }
    
    private static ListNode find(ListNode node, int target) {
        while (node != null) {
            if (node.val == target) {
                return node;
            }
            node = node.next;
        }
        return null;
    }

    public static void test(ListNode headA, ListNode headB) {
        System.out.println(ListNode.format(headA));
        System.out.println(ListNode.format(headB));
        System.out.println(new T0160_IntersectionOfTwoLinkedLists().getIntersectionNode2_2(headA, headB));
    }
    
    
    

}
