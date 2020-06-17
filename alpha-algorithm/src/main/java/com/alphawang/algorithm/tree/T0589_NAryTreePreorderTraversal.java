package com.alphawang.algorithm.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * https://leetcode.com/problems/n-ary-tree-preorder-traversal/
 */
public class T0589_NAryTreePreorderTraversal {

    /**
     * 1. 递归
     */
    public List<Integer> preorder(Node root) {
        List<Integer> result = new ArrayList<>();
        recursion(root, result);
        
        return result;
    }
    
    private void recursion(Node node, List<Integer> result) {
        if (node == null) {
            return;
        }
        result.add(node.val);
        if (node.children != null) {
            for (Node child : node.children) {
                recursion(child, result);
            }
        }
    }

    /**
     * 2. Stack
     */
    public List<Integer> preorder2(Node root) {
        Stack<Node> stack = new Stack<>();
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        stack.push(root);
        
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            result.add(cur.val);
            if (cur.children != null) {
                for (int i = cur.children.size() - 1; i >= 0; i--) {
                    stack.push(cur.children.get(i));
                }
            }
        }
        
        return result;
    }

    public static void main(String[] args) {
        T0589_NAryTreePreorderTraversal sut = new T0589_NAryTreePreorderTraversal();

        /**
         *     1
         *  3  2  4
         * 5 6 
         *
         */
        // 1,3,5,6,2,4
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node3 = new Node(3, node5, node6);
        Node node2 = new Node(2);
        Node node4 = new Node(4);
        Node node1 = new Node(1, node3, node2, node4);
        
        System.out.println(sut.preorder2(node1));
    }
}
