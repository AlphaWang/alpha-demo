package com.alphawang.algorithm.tree;

import com.alphawang.algorithm.TreeNode;
import com.alphawang.algorithm.TreeNodeCreator;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
public class T0102_BinaryTreeLevelOrderTraversal {

    /**
     * 1. BFS
     *    0ms - 100%
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        
        while (!queue.isEmpty()) {
            int count = queue.size();
            List<Integer> currentLevelItems = new ArrayList<>();
            
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
        T0102_BinaryTreeLevelOrderTraversal sut = new T0102_BinaryTreeLevelOrderTraversal();
        System.out.println(sut.levelOrder(TreeNodeCreator.createTree(3,9,20,null,null,15,7)));
        System.out.println(sut.levelOrder(null));
    }
    
    

}
