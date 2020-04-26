package com.alphawang.algorithm.tree;

import com.google.common.collect.Lists;
import java.util.List;

public class TreeNode<T> {
    T value;
    TreeNode<T> left;
    TreeNode<T> right;
    
    public TreeNode(T value) {
         this.value = value;
    }
    
    public void setLeft(TreeNode<T> left) {
        this.left = left;
    }

    public void setRight(TreeNode<T> right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
    
    public List<T> preOrder() {
        List<T> items = Lists.newArrayList();
        items.add(value);
        
        if (left != null) {
            items.addAll(left.preOrder());
        }
        if (right != null) {
            items.addAll(right.preOrder());
        }
        return items;
    }

    public List<T> inOrder() {
        List<T> items = Lists.newArrayList();
        

        if (left != null) {
            items.addAll(left.inOrder());
        }
        items.add(value);
        if (right != null) {
            items.addAll(right.inOrder());
        }
        return items;
    }

    public List<T> postOrder() {
        List<T> items = Lists.newArrayList();


        if (left != null) {
            items.addAll(left.postOrder());
        }
        if (right != null) {
            items.addAll(right.postOrder());
        }
        items.add(value);
        
        return items;
    }
}
