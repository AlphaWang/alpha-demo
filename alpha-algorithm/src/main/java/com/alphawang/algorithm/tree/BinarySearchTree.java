package com.alphawang.algorithm.tree;

import static com.alphawang.algorithm.tree.TreeNodeTraversal.print;

public class BinarySearchTree {

    /**
     * Create a binary-search-tree
     */
    public static <T extends Comparable> TreeNode<T> create(T... values) {
        return create(values, 0, values.length - 1);
    }

    public static <T extends Comparable> TreeNode<T> create(T[] values, int start, int end) {
        if (start == end) {
            return new TreeNode<>(values[start]);
        }
        if (start > end) {
            return null;
        }

        int mid = start + (end - start) / 2;
        TreeNode<T> node = new TreeNode<>(values[mid]);
        TreeNode<T> left = create(values, start, mid - 1);
        TreeNode<T> right = create(values, mid + 1, end);
        node.setLeft(left);
        node.setRight(right);

//        System.out.println(String.format("-- [%s, %s] --> %s", start, end, mid));

        return node;
    }

    /**
     * Find a value in binary-search-tree
     */
    public static <T extends Comparable> TreeNode<T> find(TreeNode<T> tree, T target) {
        TreeNode<T> node = tree;

        int step = 0;
        while (node != null) {
            step++;

            T value = node.value;
            int compare = target.compareTo(value);
            if (compare == 0) {
                break;
            } else if (compare < 0) {
                node = node.left;
            } else if (compare > 0) {
                node = node.right;
            }
        }

        System.out.println(String.format("Found %s in %s steps", target, step));
        return node;
    }


    public static void main(String[] args) {
        TreeNode tree = create(1, 2, 3, 4, 5, 6, 7, 8, 9);

        print(tree);
        System.out.println(find(tree, 8));
        System.out.println(find(tree, 0));
    }


}
