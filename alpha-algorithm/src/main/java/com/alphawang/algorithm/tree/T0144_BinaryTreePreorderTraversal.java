package com.alphawang.algorithm.tree;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * https://leetcode.com/problems/binary-tree-preorder-traversal/
 */
public class T0144_BinaryTreePreorderTraversal {

    /**
     * 1. 递归
     *    0ms
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        preorder(root, res);
        
        return res;
    }
    
    private void preorder(TreeNode node, List<Integer> res) {
        res.add(node.val);
        if (node.left != null) {
            preorder(node.left, res);
        }
        if (node.right != null) {
            preorder(node.right, res);
        }
    }

    /**
     * 2. 栈
     *    0ms
     */
    public List<Integer> preorderTraversal2(TreeNode root) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        
        stack.push(root);
        while (!stack.isEmpty()) {
            System.out.println(stack);
            TreeNode curNode = stack.pop();
            res.add(curNode.val);
            if (curNode.right != null) {
                stack.push(curNode.right);
            }
            if (curNode.left != null) {
                stack.push(curNode.left);
            }
        }
            
        return res;
    }

    /**
     * 3. 栈：带状态
     */
    LinkedList<TreeNode> nodeStack = new LinkedList<>();
    LinkedList<Integer> statusStack = new LinkedList<>();
    private static final Integer NEW = 0, VISITED = 1;
    
    public List<Integer> preorderTraversal3(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        push(root, NEW);
        while(!nodeStack.isEmpty()) {
            TreeNode node = nodeStack.pop();
            Integer status = statusStack.pop();
            
            if (status == NEW) {
                // 入栈：右节点
                if (node.right != null) 
                    push(node.right, NEW);
                // 入栈：左节点
                if (node.left != null)
                    push(node.left, NEW);
                // 再次入栈：当前节点
                push(node, VISITED);
            } else {
                // 只有当前节点之前访问过，才处理
                res.add(node.val);
            }
        }
        return res;
    }
    
    private void push(TreeNode node, Integer status) {
        nodeStack.push(node);
        statusStack.push(status);
    }

    public static void main(String[] args) {
        T0144_BinaryTreePreorderTraversal sut = new T0144_BinaryTreePreorderTraversal();
        TreeNode treeNode = TreeNodeCreator.createTree(1, null, 2, null, null, 3);
        TreeNodePrinter.print(treeNode);
        System.out.println(sut.preorderTraversal3(treeNode)); // 1, 2 ,3

        treeNode = TreeNodeCreator.createTree(1, 2, 3, 4, 5);
        TreeNodePrinter.print(treeNode);
        System.out.println(sut.preorderTraversal3(treeNode)); // 1, 2, 4, 5 ,3
    }
}
