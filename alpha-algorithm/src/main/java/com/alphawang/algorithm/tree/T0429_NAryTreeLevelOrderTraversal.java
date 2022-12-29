package com.alphawang.algorithm.tree;

import com.alphawang.algorithm.Node;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class T0429_NAryTreeLevelOrderTraversal {

    /**
     * 1. BFS: 基于队列
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
     * 1. BFS: List保存上一层数据  *****
     */
    public List<List<Integer>> levelOrder1_1(Node root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        List<Node> preLevelNodes = new ArrayList<>();
        preLevelNodes.add(root);
        while (!preLevelNodes.isEmpty()) {
//            res.add(preLevelNodes.stream().map(node -> node.val).collect(Collectors.toList()));
            List<Node> curLevelNodes = new ArrayList<>();
            List<Integer> preLevelValues = new ArrayList<>();
            for (Node preLevelNode : preLevelNodes) {
                preLevelValues.add(preLevelNode.val);
                if (preLevelNode.children != null) {
                    curLevelNodes.addAll(preLevelNode.children); 
                }
            }
            res.add(preLevelValues);
            preLevelNodes = curLevelNodes;
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

        System.out.println(sut.levelOrder1_1(node1));
    }
}
