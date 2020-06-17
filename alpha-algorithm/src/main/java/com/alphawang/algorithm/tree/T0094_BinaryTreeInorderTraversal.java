package com.alphawang.algorithm.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * https://leetcode.com/problems/binary-tree-inorder-traversal/
 */
public class T0094_BinaryTreeInorderTraversal {

    /**
     * 1. 递归
     *    0ms
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
     *    0ms
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

            //处理左节点
            curr = stack.pop();
            //处理根节点
            res.add(curr.val);
            // 处理右节点
            curr = curr.right;
        }

        return res;
    }

    /**
     * 4. 栈: 更通用的解法
     *    1ms
     */
    Stack<TreeNode> nodeStack = new Stack<>();
    Stack<Integer> statusStack = new Stack<>();
    private static final int NEW = 0, VISITED = 1;
    
    public List<Integer> inorderTraversal4(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        
        push(root, NEW);
        
        while (!nodeStack.isEmpty()) {
            TreeNode node = nodeStack.pop();
            Integer status = statusStack.pop();
            System.out.println("popped node : " + node + " - " + status);
            if (status == NEW) {
                // 入栈：右节点
                if (node.right != null) {
                    push(node.right, NEW);
                }
                // 入栈：当前节点
                push(node, VISITED);
                // 入栈：左节点
                if (node.left != null) {
                    push(node.left, NEW);
                }
            } else {
                // 只有当节点之前访问过，才处理
                res.add(node.val);
            }
        }
        
        return res;
    }
    
    private void push(TreeNode node, Integer status) {
        System.out.println("push node : " + node + " - " + status);
        nodeStack.push(node);
        statusStack.push(status);
    }

    public static void main(String[] args) {
        T0094_BinaryTreeInorderTraversal sut = new T0094_BinaryTreeInorderTraversal();
        
        TreeNode treeNode = TreeNodeCreator.createTree(1, null, 2, null, null, 3);
        TreeNodePrinter.print(treeNode);
        System.out.println(sut.inorderTraversal4(treeNode)); // 1, 3, 2

        treeNode = TreeNodeCreator.createTree(1, 2, 3, 4, 5);
        TreeNodePrinter.print(treeNode);
        System.out.println(sut.inorderTraversal4(treeNode)); // 4, 2, 5, 1, 3
    }
}
