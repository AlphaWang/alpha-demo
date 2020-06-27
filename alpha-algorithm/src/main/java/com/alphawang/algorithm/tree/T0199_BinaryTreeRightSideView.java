package com.alphawang.algorithm.tree;

import com.google.common.collect.Lists;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
public class T0199_BinaryTreeRightSideView {

    /**
     * 1. BFS 
     *    see T102_LevelOrderTraversal
     */
    public static List<Integer> rightSideViewByLevelTraverse(TreeNode tree) {
        List<Integer> result = Lists.newArrayList();
        if (tree == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(tree);

        while (!queue.isEmpty()) {
            int count = queue.size();
            Integer rightItem = null;

            for (int i = 0; i < count; i++) {
                TreeNode currentNode = queue.poll();
                rightItem = currentNode.val;

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
     * 2. DFS, 根 - 右 - 左，记录每层访问的第一个元素 
     */
    public static List<Integer> rightSideViewByRecursion(TreeNode tree) {
         List<Integer> result = Lists.newArrayList();
         dfs(tree, result, 0);
         
         return result;
    }
    
    private static void dfs(TreeNode node, List<Integer> result, int depth) {
        if (node == null) {
            return;
        }
        // 每层访问的第一个元素 即是最右节点
        if (depth == result.size()) {
            result.add(node.val);
        }
        System.out.println(String.format("%s + %s = %s", depth, node, result));
        dfs(node.right, result, depth + 1);
        dfs(node.left, result, depth + 1);
    }


    public static void main(String[] args) {
        
        System.out.println(rightSideViewByLevelTraverse(TreeNodeCreator.createTree(1,2,3,null,5,null,4)));
        System.out.println(rightSideViewByRecursion(TreeNodeCreator.createTree(1,2,3,null,5,null,4)));
    
    }

}
