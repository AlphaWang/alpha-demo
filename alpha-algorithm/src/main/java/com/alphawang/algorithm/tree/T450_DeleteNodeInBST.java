package com.alphawang.algorithm.tree;

import com.alphawang.algorithm.TreeNode;
import com.alphawang.algorithm.TreeNodeCreator;
import com.alphawang.algorithm.TreeNodePrinter;

public class T450_DeleteNodeInBST {
    /**
     * 450. Delete Node in a BST
     * https://leetcode.com/problems/delete-node-in-a-bst/
     * 
     * Given a root node reference of a BST and a key, 
     * delete the node with the given key in the BST. 
     * Return the root node reference (possibly updated) of the BST.
     *
     * Basically, the deletion can be divided into two stages:
     *
     * Search for a node to remove.
     * If the node is found, delete the node.
     * Note: Time complexity should be O(height of tree).
     *
     * Example:
     *
     * root = [5,3,6,2,4,null,7]
     * key = 3
     *
     *     5
     *    / \
     *   3   6
     *  / \   \
     * 2   4   7
     *
     * Given key to delete is 3. So we find the node with value 3 and delete it.
     *
     * One valid answer is [5,4,6,2,null,null,7], shown in the following BST.
     *
     *     5
     *    / \
     *   4   6
     *  /     \
     * 2       7
     *
     * Another valid answer is [5,2,6,null,4,null,7].
     *
     *     5
     *    / \
     *   2   6
     *    \   \
     *     4   7
     */
    public static TreeNode delete(TreeNode tree, Integer target) {
        TreeNode parent = null;
        TreeNode node = tree;

        // 0. 找到待删除节点、以及父节点
        while(node != null && !target.equals(node.val)) {
            parent = node;
            if (target.compareTo(node.val) < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }

        if (node == null) {
            System.out.println("---- Not Found " + target);
            return tree;
        }


        // 1. 当有两个子节点，找到右子树中的最小值，替换到当前节点；再删除最小值节点
        if (node.left != null & node.right != null) {
            TreeNode min = node.right;
            TreeNode minParent = node;
            while (min.left != null) {
                minParent = min;
                min = min.left;
            }

            node.val = min.val;

            //接下来 删除 min
            node = min;
            parent = minParent;
        }


        // 2. 当没有子节点，直接删除
        // 可以与 step-3 合并
        if (node.left == null && node.right == null) {
            if (parent == null) {
                return null;
            }
            if (node.equals(parent.right)) {
                parent.right = null;
            } else {
                parent.left = null;
            } 
            
            return tree;
        }

        // 3. 当有一个子节点，则把子节点 链接 到父节点
        TreeNode child = null;
        if (node.left == null) {
            child = node.right;
        }
        if (node.right == null) {
            child = node.left;
        }
        if (child != null) {
            if (parent == null) {  //root node
                return child;
            }
            if (node.equals(parent.right)) {
                parent.right = child;
            } else {
                parent.left = child;
            }
            return tree;
        }

        return tree;
    }
    
    
    public static void main(String[] args) {
        TreeNode tree = TreeNodeCreator.createTree(5,3,6,2,4,null,7);
        System.out.println("----------");
        TreeNodePrinter.print(tree);
        System.out.println("Delete 3:");
        TreeNodePrinter.print(delete(tree, 3));
        
        tree = TreeNodeCreator.createTree(5,3,6,2,4,null,7);
        System.out.println("----------");
        TreeNodePrinter.print(tree);
        System.out.println("Delete 0:");
        TreeNodePrinter.print(delete(tree, 0));

        tree = TreeNodeCreator.createTree(0);
        System.out.println("----------");
        TreeNodePrinter.print(tree);
        System.out.println("Delete 0:");
        TreeNodePrinter.print(delete(tree, 0));

        tree = TreeNodeCreator.createTree(1, null, 2);
        System.out.println("----------");
        TreeNodePrinter.print(tree);
        System.out.println("Delete 1:");
        TreeNodePrinter.print(delete(tree, 1));
    }

}
