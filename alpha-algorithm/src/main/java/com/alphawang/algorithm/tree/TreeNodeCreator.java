package com.alphawang.algorithm.tree;

import static com.alphawang.algorithm.tree.TreeNodePrinter.print;
import static com.alphawang.algorithm.tree.TreeNodeTraversal.traverse;

public class TreeNodeCreator {
    
    public static <T> TreeNode<T> createTree(T... values) {
        int len = values.length;
        
        TreeNode[] nodes = new TreeNode[len+1];
        for (int i = 0; i < len; i++) {
            T value = values[i];
            if (value == null) {
                continue; 
            }
            
            int arrayIndex = i + 1;
            TreeNode node = new TreeNode(value);
            nodes[arrayIndex] = node;
            
            int parentIndex = arrayIndex / 2;
            if (parentIndex > 0) {
                TreeNode parent = nodes[parentIndex];
                if (i % 2 == 0) {
                    parent.setRight(node);
                } else {
                    parent.setLeft(node);
                }
            }
        }
        
        return nodes[1];
    }

    public static void main(String[] args) {
        /**
         *      1
         *    / \
         *   2  3
         *  / \
         * 4  5
         */
        TreeNode tree = createTree(1, 2, 3, 4, 5);
        traverse(tree);
        print(tree);
        /**
         *      3
         *    / \
         *   9  20
         *     /  \
         *    15   7
         */
        tree = createTree(3, 9, 20, null, null, 15, 7);
        traverse(tree);
        print(tree);
        
    }
    

    

}
