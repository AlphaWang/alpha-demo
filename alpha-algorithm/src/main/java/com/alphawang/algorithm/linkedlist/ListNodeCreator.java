package com.alphawang.algorithm.linkedlist;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListNodeCreator {

    public static <T> ListNode<T> create(T... values) {
        List<ListNode> nodes = Stream.of(values)
            .map(i -> new ListNode(i))
            .collect(Collectors.toList());

        for (int i = 0; i < nodes.size() - 1; i++) {
            ListNode<T> current = nodes.get(i);
            ListNode<T> next = nodes.get(i + 1);
            current.setNext(next);
        }

        ListNode<T> head = nodes.get(0);
        return head;
    }
}
