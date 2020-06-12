package com.alphawang.algorithm.tree;

import static com.alphawang.algorithm.tree.TreeNodePrinter.print;

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

            T value = node.val;
            int compare = target.compareTo(value);
            if (compare < 0) {
                node = node.left;
            } else if (compare > 0) {
                node = node.right;
            } else if (compare == 0) {
                break;
            }
        }

        System.out.println(String.format("Found %s in %s steps", target, step));
        return node;
    }

    /**
     * Insert a value into binary-search-tree
     */
    public static <T extends Comparable> TreeNode<T> insert(TreeNode<T> tree, T target) {
        if (tree == null) {
            return new TreeNode<>(target);
        }
        
        TreeNode<T> node = tree;

        int step = 0;
        while (node != null) {
            step++;

            T value = node.val;
            int compare = target.compareTo(value);
            if (compare < 0) {
                if (node.left == null) {
                    TreeNode newNode = new TreeNode<>(target);
                    node.setLeft(newNode);
                    break;
                }
                
                node = node.left;
            } else if (compare > 0) {
                if (node.right == null) {
                    TreeNode newNode = new TreeNode<>(target);
                    node.setRight(newNode);
                    break;
                }
                
                node = node.right;
            } else if (compare == 0) {
                System.out.println("-- Already have " + target);
                break;
            }
        }

        System.out.println(String.format("Insert %s in %s steps", target, step));
        return tree;
    }

    /**
     * Delete a value in binary-search-tree
     */
    public static <T extends Comparable> void delete(TreeNode<T> tree, T target) {
        TreeNode<T> parent = null;
        TreeNode<T> node = tree;
        
        // 1. 找到待删除节点、以及父节点
        while(node != null && !target.equals(node.val)) {
            parent = node;
            if (target.compareTo(node.val) < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        
        if (node == null) {
            System.out.println("---- Not Found " + target);
        }
        
        // 2. 当没有子节点，直接删除
        // 可以与 step-3 合并
        if (node.left == null && node.right == null) {
            if (node.equals(parent.right)) {
                parent.right = null;
            } else {
                parent.left = null;
            }
            return;
        }

        // 3. 当有一个子节点，则把子节点 链接 到父节点
        TreeNode<T> child = null;
        if (node.left == null) {
            child = node.right;
        }
        if (node.right == null) {
            child = node.left;
        }
        if (child != null) {
            if (node.equals(parent.right)) {
                parent.right = child;
            } else {
                parent.left = child;
            }
            return;
        }
        
        
        // 4. 当有两个子节点，找到右子树中的最小值，替换到当前节点；再删除最小值节点
        // TODO bug: 当最小值有右子树时，右子树会被误删除
        TreeNode<T> min = node.right;
        TreeNode<T> minParent = node;
        while (min.left != null) {
            minParent = min;
            min = min.left;
        }
        
        node.val = min.val;
        if (min.equals(minParent.left)) {
            minParent.left = null;
        }
        if (min.equals(minParent.right)) {
            minParent.right = null;
        }
    }

    public static <T extends Comparable> void delete2(TreeNode<T> tree, T target) {
        TreeNode<T> parent = null;
        TreeNode<T> node = tree;

        // 0. 找到待删除节点、以及父节点
        while(node != null && !target.equals(node.val)) {
            parent = node;
            if (target.compareTo(node.val) < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }

        if (node == null) {
            System.out.println("---- Not Found " + target);
        }


        // 1. 当有两个子节点，找到右子树中的最小值，替换到当前节点；再删除最小值节点
        if (node.left != null & node.right != null) {
            TreeNode<T> min = node.right;
            TreeNode<T> minParent = node;
            while (min.left != null) {
                minParent = min;
                min = min.left;
            }

            node.val = min.val;
            
            //接下来 删除 min
            node = min;
            parent = minParent;
        }
       

        // 2. 当没有子节点，直接删除
        // 可以与 step-3 合并
        if (node.left == null && node.right == null) {
            if (node.equals(parent.right)) {
                parent.right = null;
            } else {
                parent.left = null;
            }
            return;
        }

        // 3. 当有一个子节点，则把子节点 链接 到父节点
        TreeNode<T> child = null;
        if (node.left == null) {
            child = node.right;
        }
        if (node.right == null) {
            child = node.left;
        }
        if (child != null) {
            if (node.equals(parent.right)) {
                parent.right = child;
            } else {
                parent.left = child;
            }
            return;
        }

    }


    public static void main(String[] args) {
        TreeNode tree = create(1, 2, 3, 5, 6, 8, 9);

        System.out.println("------- AS-IS ");
        print(tree);
        System.out.println(find(tree, 8));
        System.out.println(find(tree, 0));

        System.out.println("------- Insert 4: ");
        print(insert(tree, 4));
        System.out.println("------- Insert 10: ");
        print(insert(tree, 10));

        System.out.println("------- Delete 4: ");
        delete2(tree, 4);
        print(tree);

        System.out.println("------- Delete 2: ");
        delete2(tree, 2);
        print(tree);

        System.out.println("------- Delete 8: ");
        delete2(tree, 8);
        print(tree);
    }


}
