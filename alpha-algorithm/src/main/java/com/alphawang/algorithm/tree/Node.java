package com.alphawang.algorithm.tree;

import java.util.Arrays;
import java.util.List;

public class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }

    public Node(int _val, Node... _children) {
        val = _val;
        children = Arrays.asList(_children);
    }

    @Override
    public String toString() {
        return String.valueOf(this.val);
    }
    
    public static Node create(Integer... args) {
        if (args == null || args.length == 0) return null;
        
        
        return null;
    }
}
