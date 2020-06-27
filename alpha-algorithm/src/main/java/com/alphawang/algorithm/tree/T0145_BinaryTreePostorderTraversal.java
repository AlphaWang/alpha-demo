package com.alphawang.algorithm.tree;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * https://leetcode.com/problems/binary-tree-postorder-traversal/
 */
public class T0145_BinaryTreePostorderTraversal {

    /**
     * 1. 状态栈 + 节点栈
     *    0ms - 100%
     */
    Deque<TreeNode> nodeStack = new LinkedList<>();
    Deque<Integer> statusStack = new LinkedList<>();
    private static Integer NEW = 0, VISITED = 1;
    
    public List<Integer> postorderTraversal(TreeNode root) {
        TreeNodePrinter.print(root);
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        
        push(root, NEW);
        while (!nodeStack.isEmpty()) {
            TreeNode node = nodeStack.pop();
            Integer status = statusStack.pop();
            
            if (status == NEW) {
                // push root
                push(node, VISITED);
                
                // push right
                if (node.right != null) {
                    push(node.right, NEW); 
                }
                
                // push left
                if (node.left != null) {
                    push(node.left, NEW); 
                }
                
            } else {
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
        T0145_BinaryTreePostorderTraversal sut = new T0145_BinaryTreePostorderTraversal();

        /*
         * Input: [1,null,2,3]
         *    1
         *     \
         *      2
         *     /
         *    3
         *
         * Output: [3,2,1]
         */
        System.out.println(sut.postorderTraversal(TreeNodeCreator.createTree(1, null, 2, null, null, 3)));
    }
}
