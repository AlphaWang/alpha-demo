package com.alphawang.algorithm.tree;

import static com.alphawang.algorithm.TreeNodePrinter.print;

import com.alphawang.algorithm.TreeNode;
import com.alphawang.algorithm.TreeNodeCreator;

public class T104_MaxDepthOfBinaryTree {
    /**
     * 104. Maximum Depth of Binary Tree
     * https://leetcode.com/problems/maximum-depth-of-binary-tree/
     * 
     * Given a binary tree, find its maximum depth.
     *
     * The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
     * Note: A leaf is a node with no children.
     *
     * Example:
     * Given binary tree [3,9,20,null,null,15,7],
     *
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * return its depth = 3.
     * 
     */
    public static int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        return Math.max(leftDepth, rightDepth) + 1;
    }

    public static void main(String[] args) {
        TreeNode tree = TreeNodeCreator.createTree(3,9,20,null,null,15,7);
        print(tree);
        System.out.println(maxDepth(tree));
    }


}
