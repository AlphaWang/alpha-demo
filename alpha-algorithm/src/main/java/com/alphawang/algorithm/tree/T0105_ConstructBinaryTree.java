package com.alphawang.algorithm.tree;

import com.alphawang.algorithm.TreeNode;
import com.alphawang.algorithm.TreeNodePrinter;

/**
 *  https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
 *  Medium
 */
public class T0105_ConstructBinaryTree {

    /**
     *  1. 递归 
     *     preStart: 根节点，遍历preorder
     *     inStart / inEnd: 中序遍历数组的有效位
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null) {
            return null;
        }
        
        return build(preorder, 0, inorder, 0, inorder.length - 1);
    }
    
    private TreeNode build(int[] preorder, int preStart, int[] inorder, int inStart, int inEnd) {
        if (preStart > preorder.length - 1 || inStart > inEnd) {
            return null;
        }
        
        TreeNode root = new TreeNode(preorder[preStart]);
        int inorderIndex = find(inorder, preorder[preStart]);
        
        int leftRootIndex = preStart + 1;
        int rightRootIndex = preStart + 1 + inorderIndex - inStart; //TODO 跳过左子树大小 (inOrderIndex - inStart)
        root.left = build(preorder, leftRootIndex, inorder, inStart, inorderIndex - 1);
        root.right = build(preorder, rightRootIndex, inorder, inorderIndex + 1, inEnd);

        return root;
    }
    
    private int find(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        T0105_ConstructBinaryTree sut = new T0105_ConstructBinaryTree();
        
        /*
        preorder = [3,9,20,15,7]
        inorder = [9,3,15,20,7]
                3
               / \
              9  20
                /  \
               15   7
         */
        TreeNode node = sut.buildTree(new int[]{3,9,20,15,7}, new int[]{9,3,15,20,7});
        TreeNodePrinter.print(node);
        
        /*
        [1,2]
        [1,2]
        
            1
             \
              2
         */
        node = sut.buildTree(new int[]{1,2}, new int[]{1,2});
        TreeNodePrinter.print(node);
    }
}
