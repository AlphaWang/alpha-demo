package com.alphawang.algorithm.tree;

import com.google.common.collect.Lists;
import java.util.List;

public class TreeNodeTraversal {

    public static void traverse(TreeNode node) {
        System.out.println("----------");
        System.out.println("pre :" + preOrder(node));
        System.out.println("in  :" + inOrder(node));
        System.out.println("post:" + postOrder(node));
    }
    
    public static List<Integer> preOrder(TreeNode tree) {
        List<Integer> items = Lists.newArrayList();
        
        items.add(tree.val);

        if (tree.left != null) {
            items.addAll(preOrder(tree.left));
        }
        if (tree.right != null) {
            items.addAll(preOrder(tree.right));
        }
        return items;
    }

    public static List<Integer> inOrder(TreeNode tree) {
        List<Integer> items = Lists.newArrayList();

        if (tree.left != null) {
            items.addAll(inOrder(tree.left));
        }
        
        items.add(tree.val);
        
        if (tree.right != null) {
            items.addAll(inOrder(tree.right));
        }
        return items;
    }

    public static List<Integer> postOrder(TreeNode tree) {
        List<Integer> items = Lists.newArrayList();

        if (tree.left != null) {
            items.addAll(postOrder(tree.left));
        }
        
        if (tree.right != null) {
            items.addAll(postOrder(tree.right));
        }
        
        items.add(tree.val);
        
        return items;
    }

}
