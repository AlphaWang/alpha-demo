package com.alphawang.algorithm.tree;

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
    
}
