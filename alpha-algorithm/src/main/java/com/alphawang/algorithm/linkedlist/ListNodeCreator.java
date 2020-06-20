package com.alphawang.algorithm.linkedlist;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListNodeCreator {

    public static ListNode create(Integer... values) {
        List<ListNode> nodes = Stream.of(values)
            .map(i -> new ListNode(i))
            .collect(Collectors.toList());

        for (int i = 0; i < nodes.size() - 1; i++) {
            ListNode current = nodes.get(i);
            ListNode next = nodes.get(i + 1);
            current.setNext(next);
        }

        ListNode head = nodes.get(0);
        return head;
    }
}
