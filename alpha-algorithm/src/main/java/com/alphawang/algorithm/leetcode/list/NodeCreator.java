package com.alphawang.algorithm.leetcode.list;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NodeCreator {
    
    public static <T> Node<T> create(T... values) {
        List<Node> nodes = Stream.of(values)
            .map(i -> new Node(i))
            .collect(Collectors.toList());

        for (int i = 0; i < nodes.size() - 1; i++) {
            Node<T> current = nodes.get(i);
            Node<T> next = nodes.get(i +1);
            current.setNext(next);
        }

        Node<T> head = nodes.get(0);
        return head;
    }
}
