package com.alphawang.algorithm.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class T0429_NAryTreeLevelOrderTraversal {

    /**
     * 1. BFS
     *    2ms
     */
    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int preLevelSize = queue.size();
            List<Integer> preLevelValues = new ArrayList<>();
            for (int i = 0; i < preLevelSize; i++) {
                Node node = queue.poll();
                preLevelValues.add(node.val);
                if (node.children != null) {
                    for (Node child : node.children) {
                        queue.offer(child);
                    }
                }
            }
            res.add(preLevelValues);
        }
        
        return res;
    }

    /**
     * 2. DFS
     *    0ms
     */
    public List<List<Integer>> levelOrder2(Node root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        
        helper(root, res, 0);
        return res;
    }
    
    private void helper(Node node, List<List<Integer>> res, int level) {
        if (node == null) {
            return;
        }
        
        if (res.size() <= level) {
            res.add(new ArrayList<>());
        }

        System.out.println(String.format("node = %s, level = %s", node.val, level));
        res.get(level).add(node.val);
        if (node.children != null) {
            for (Node child : node.children) {
                helper(child, res, level + 1);
            }
        }
    }

    public static void main(String[] args) {
        T0429_NAryTreeLevelOrderTraversal sut = new T0429_NAryTreeLevelOrderTraversal();
        /**
         *     1
         *  3  2  4
         * 5 6 
         *   
         */
        // 1; 3,2,4; 5,6
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node3 = new Node(3, node5, node6);
        Node node2 = new Node(2);
        Node node4 = new Node(4);
        Node node1 = new Node(1, node3, node2, node4);

        System.out.println(sut.levelOrder2(node1));
    }
}
