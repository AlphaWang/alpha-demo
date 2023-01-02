package com.alphawang.algorithm.linkedlist;

public class T0206_ReverseLinkedListII_92 {

  /**
   * 方法一：
   * 先移动到 left 节点，再 reverseN
   */
  public ListNode reverseBetween(ListNode head, int left, int right) {
    ListNode originHead = head;
    ListNode firstLast = null;
    for (int i = 1; i < left; i++) {
      firstLast = head;
      head = head.next;
    }

    System.out.println("firstLast = " + ListNode.format(firstLast));
    System.out.println("head = " + head.format());
    System.out.println("originHead = " + originHead.format());

    ListNode reversedN = reverseN(head, right - left + 1);
    if (firstLast != null) {
      firstLast.next = reversedN;
      return originHead;
    } else {
      return reversedN;
    }
  }

  /**
   * TODO
   * 方法二：递归
   */
  public ListNode reverseBetween2(ListNode head, int left, int right) {
    if (left == 1) {
      return reverseN(head, right);
    }

    head.next = reverseBetween2(head.next, left - 1, right - 1);
    return head;
  }

  /**
   * 翻转前N个元素
   * 1,2,3,4,5,6,7 & 2 --> 2,1,3,4,5,6,7
   */
  ListNode successor = null;
  private ListNode reverseN(ListNode head, int n) {
    if (n == 1) {
      successor = head.next; //记录第 N+1 个节点
      return head;
    }

    //T206 常规反转模板
    ListNode last = reverseN(head.next, n - 1);

    head.next.next = head;
    head.next = successor; //与206不同，head.next不是null
    return last;
  }

  public static void main(String[] args) {
    T0206_ReverseLinkedListII_92 sut = new T0206_ReverseLinkedListII_92();
    sut.test(ListNodeCreator.create(1, 2, 3, 4, 5, 6, 7), 2 ,5); // 1,5,4,3,2,6,7
    sut.test(ListNodeCreator.create(1, 2, 3, 4, 5), 2 ,4); // 1,4,3,2,5
    sut.test(ListNodeCreator.create(5), 1 ,1); // 5
    sut.test(ListNodeCreator.create(3, 5), 1 ,2); // 5,3
  }

  private void test(ListNode head, int left, int right) {
//    System.out.println(String.format("%s \nReverseN [0, %s] -->>>> %s",
//        head.format(), right, reverseN(head, right).format()));

    System.out.println(String.format("%s \nReverse Between [%s, %s] -->>>> %s \n---------",
        head.format(), left, right, reverseBetween2(head, left, right).format()));
  }
}
