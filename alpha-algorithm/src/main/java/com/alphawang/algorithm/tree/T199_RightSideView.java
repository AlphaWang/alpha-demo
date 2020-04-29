package com.alphawang.algorithm.tree;

import com.google.common.collect.Lists;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class T199_RightSideView {
    /**
     * 199. Binary Tree Right Side View
     * https://leetcode.com/problems/binary-tree-right-side-view/
     * 
     * Given a binary tree, imagine yourself standing on the right side of it, 
     * return the values of the nodes you can see ordered from top to bottom.
     *
     * Example:
     *
     * Input: [1,2,3,null,5,null,4]
     * Output: [1, 3, 4]
     * Explanation:
     *
     *    1            <---
     *  /   \
     * 2     3         <---
     *  \     \
     *   5     4       <---
     */

    /**
     * 1. from T102_LevelOrderTraversal
     */
    public static List<Integer> rightSideViewByLevelTraverse(TreeNode<Integer> tree) {
        List<Integer> result = Lists.newArrayList();
        if (tree == null) {
            return result;
        }

        Queue<TreeNode<Integer>> queue = new LinkedList<>();
        queue.offer(tree);

        while (!queue.isEmpty()) {
            int count = queue.size();
            Integer rightItem = null;

            for (int i = 0; i < count; i++) {
                TreeNode<Integer> currentNode = queue.poll();
                rightItem = currentNode.value;

                // put next level to queue
                if (currentNode.left != null) {
                    queue.offer(currentNode.left);
                }
                if (currentNode.right != null) {
                    queue.offer(currentNode.right);
                }
            }

            result.add(rightItem);
        }
        return result;
    }

    /**
     * 2. //TODO
     */
    public static List<Integer> rightSideViewByRecursion(TreeNode<Integer> tree) {
         List<Integer> result = Lists.newArrayList();
         rightView(tree, result, 0);
         
         return result;
    }
    
    private static void rightView(TreeNode<Integer> node, List<Integer> result, int currDepth) {
        if (node == null) {
            return;
        }
        // 每层只放一个元素
        if (currDepth == result.size()) {
            result.add(node.value);
        }
        rightView(node.right, result, currDepth + 1);
        rightView(node.left, result, currDepth + 1);
    }


    public static void main(String[] args) {
        
        System.out.println(rightSideViewByLevelTraverse(TreeNodeCreator.createTree(1,2,3,null,5,null,4)));
        System.out.println(rightSideViewByRecursion(TreeNodeCreator.createTree(1,2,3,null,5,null,4)));
    
    }

}
