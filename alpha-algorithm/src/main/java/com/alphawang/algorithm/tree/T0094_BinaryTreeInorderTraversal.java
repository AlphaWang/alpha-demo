package com.alphawang.algorithm.tree;

import java.util.ArrayList;
import java.util.List;

public class T0094_BinaryTreeInorderTraversal {

    /**
     * 1. 递归
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        res.addAll(inorderTraversal(root.left));
        res.add(root.val);
        res.addAll(inorderTraversal(root.right));
        
       return res;
    }

    /**
     * 2. 栈
     */
//    public List<Integer> inorderTraversal2(TreeNode root) {
//        
//    }

    public static void main(String[] args) {
        T0094_BinaryTreeInorderTraversal sut = new T0094_BinaryTreeInorderTraversal();
        
        TreeNode treeNode = TreeNodeCreator.createTree(1, null, 2, null, null, 3);
        TreeNodePrinter.print(treeNode);
        System.out.println(sut.inorderTraversal(treeNode)); // 1, 3, 2
    }
}
