package com.alphawang.algorithm.tree;

import com.google.common.collect.Lists;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class T0102_BinaryTreeLevelOrderTraversal {
    /**
     * https://leetcode.com/problems/binary-tree-level-order-traversal/
     * 
     * Given a binary tree, return the level order traversal of its nodes' values. 
     * (ie, from left to right, level by level).
     *
     * For example:
     * Given binary tree [3,9,20,null,null,15,7],
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * return its level order traversal as:
     * [
     *   [3],
     *   [9,20],
     *   [15,7]
     * ]
     * 
     */
    
    public static List<List<Integer>> levelOrderTraverse(TreeNode tree) {
        List<List<Integer>> result = Lists.newArrayList();

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(tree);
        
        while (!queue.isEmpty()) {
            int count = queue.size();
            List<Integer> currentLevelItems = Lists.newArrayList();
            
            for (int i = 0; i < count; i++) {
                TreeNode currentNode = queue.poll();
                currentLevelItems.add(currentNode.val);
                
                // put next level to queue
                if (currentNode.left != null) {
                    queue.offer(currentNode.left);
                }
                if (currentNode.right != null) {
                    queue.offer(currentNode.right);
                }
            }
            
            result.add(currentLevelItems);
        }
        return result;
    }


    public static void main(String[] args) {
        System.out.println(levelOrderTraverse(TreeNodeCreator.createTree(3,9,20,null,null,15,7)));
    }
    
    

}
