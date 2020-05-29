package com.alphawang.algorithm.number;

public class Interview {

    static class ListNode {
        public int val;
        public ListNode next;
        
        public ListNode(int x) {
            val = x;
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(0);
        ListNode rtNode = result;

        int temp = 0;
        while (true) {
            if (l1 == null && l2 == null) {
                break;
            }

            int j = 0;
            if (l1 == null) {
                j = (l2.val + temp) % 10;
                temp = (l2.val + temp) / 10;
                l2 = l2.next;
            }
            else if (l2 == null) {
                j = (l1.val + temp) % 10;
                temp = (l1.val + temp) / 10;
                l1 = l1.next;
            }
            else {
                j = (l1.val + l2.val + temp) % 10;
                temp = (l1.val + l2.val + temp) / 10;
                l2 = l2.next;
                l1 = l1.next;
            }

            result.next = new ListNode(j);
            result = result.next;
        }

        if (temp != 0) {
            result.next = new ListNode(temp);
        }

        return rtNode.next;
    }


    public static void main(String[] args) {
        Interview interview = new Interview();
        
        ListNode l1 = new ListNode(2);
        ListNode input1 = l1;
        l1.next = new ListNode(4);
        l1 = l1.next;
        l1.next = new ListNode(3);
        
        ListNode l2 = new ListNode(5);
        ListNode input2 = l2;
        l2.next = new ListNode(6);
        l2 = l2.next;
        l2.next = new ListNode(4);

        ListNode result = interview.addTwoNumbers(input1, input2);
        System.out.println(result.val);

        while (result.next != null) {
            System.out.println(result.next.val);
            result = result.next;
        }

    }

}