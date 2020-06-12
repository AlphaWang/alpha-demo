package com.alphawang.algorithm.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
     * 2. 递归：优化避免频繁创建 List
     */
    public List<Integer> inorderTraversal2(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        traversal(root, res);
        
        return res;
    }
    
    private void traversal(TreeNode node, List<Integer> res) {
        if (node == null) {
            return;
        }
        
        traversal(node.left, res);
        res.add(node.val);
        traversal(node.right, res);
    }

    /**  
     * TODO
     * 3. 栈
     */
    public List<Integer> inorderTraversal3(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        List<Integer> res = new ArrayList<>();
        TreeNode curr = root;
        
        while (curr != null || !stack.isEmpty()) {
            // 将左节点入栈
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            
            curr = stack.pop();
            res.add(curr.val);
            
            curr = curr.right;
        }
        
        return res;
    }

    public static void main(String[] args) {
        T0094_BinaryTreeInorderTraversal sut = new T0094_BinaryTreeInorderTraversal();
        
        TreeNode treeNode = TreeNodeCreator.createTree(1, null, 2, null, null, 3);
        TreeNodePrinter.print(treeNode);
        System.out.println(sut.inorderTraversal3(treeNode)); // 1, 3, 2
    }
}
