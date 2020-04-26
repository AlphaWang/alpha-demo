package com.alphawang.algorithm.tree;

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
        print(createTree(1, 2, 3, 4, 5));
        /**
         *      3
         *    / \
         *   9  20
         *     /  \
         *    15   7
         */
        print(createTree(3,9,20,null,null,15,7));
        
    }
    
    private static void print(TreeNode node) {
        System.out.println("----------");
        System.out.println(node.preOrder());
        System.out.println(node.inOrder());
        System.out.println(node.postOrder());
    }
    

}
