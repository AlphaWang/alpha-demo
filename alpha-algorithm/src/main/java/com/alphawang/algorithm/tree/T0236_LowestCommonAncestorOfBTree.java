package com.alphawang.algorithm.tree;

import com.alphawang.algorithm.TreeNode;
import com.alphawang.algorithm.TreeNodeCreator;
import com.alphawang.algorithm.TreeNodePrinter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *  https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
 *  Medium
 */
public class T0236_LowestCommonAncestorOfBTree {
    
    /**
     *  1. 递归找 p or q
     *     4ms - 100%
     */
    TreeNode res = null;
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        findPorQ(root, p, q);
        return res;
    }
    
    private boolean findPorQ(TreeNode curr, TreeNode p, TreeNode q) {
        if (curr == null) {
            return false;
        }
//        if (curr.val == p.val || curr.val == q.val) {
//            return true;
//        }
        
         boolean left = findPorQ(curr.left, p, q);
         boolean right = findPorQ(curr.right, p, q);

        if (left && right) {
            res = curr;
            System.out.println("---set res = " + curr);
        }
        
         if ((left || right) && (curr.val == q.val || curr.val == p.val)) {
             res = curr;
             System.out.println("+++set res = " + curr);
         }
        
         
         return left || right || (curr.val == p.val || curr.val == q.val);
    }

    /**
     *  2. DFS 记录路径，找交汇点
     *     7ms - 34.58%
     */
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        Map<Integer, TreeNode> parents = new HashMap<>();
        dfs2(root, parents, null);
        
        Set<Integer> pParents = new HashSet<>();
        while (p != null) {
            pParents.add(p.val);
            p = parents.get(p.val);
        }
        
        while (q != null) {
            if (pParents.contains(q.val)) {
                return q;
            }
            q = parents.get(q.val);
        }
        
        return null;
        
    }
    
    private void dfs2(TreeNode node, Map<Integer, TreeNode> parents, TreeNode parent) {
        if (node == null) {
            return;
        }
        
        parents.put(node.val, parent);
        dfs2(node.left, parents, node);
        dfs2(node.right, parents, node);
    }

    /**
     * 3. 递归，分别找左右 !!!!!
     *    4ms - 100%
     */
    public TreeNode lowestCommonAncestor3(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root.val == p.val || root.val == q.val) {
            return root;
        }
        
        TreeNode left = lowestCommonAncestor3(root.left, p, q);
        TreeNode right = lowestCommonAncestor3(root.right, p, q);
        if (left == null) return right;
        if (right == null) return left;
        
        return root;
    }
    

    public static void main(String[] args) {
        T0236_LowestCommonAncestorOfBTree sut = new T0236_LowestCommonAncestorOfBTree();
        /*
        [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
        Output: 3
         */
        TreeNode tree = TreeNodeCreator.createTree(3,5,1,6,2,0,8,null,null,7,4);
        TreeNodePrinter.print(tree);
        System.out.println(sut.lowestCommonAncestor3(tree, new TreeNode(5), new TreeNode(1)));  
          
        /*
        [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
        Output: 5
        */
        tree = TreeNodeCreator.createTree(3,5,1,6,2,0,8,null,null,7,4);
        TreeNodePrinter.print(tree);
        System.out.println(sut.lowestCommonAncestor3(tree, new TreeNode(5), new TreeNode(4)));
    }
}
