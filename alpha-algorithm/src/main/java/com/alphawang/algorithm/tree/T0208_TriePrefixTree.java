package com.alphawang.algorithm.tree;

/**
 *  https://leetcode.com/problems/implement-trie-prefix-tree/
 *  Medium
 *  
 *  29ms - 99%
 */
public class T0208_TriePrefixTree {
    
    private static final int SIZE = 26;
    
    private Node root;
    
    private static class Node {
        private Node[] next = new Node[SIZE];
        private char c;
        private boolean end;
        
        public Node(char c) {
            this.c = c;
        }
        
        public void put(char c) {
            Node node = new Node(c);
            next[index(c)] = node;
        }
        
        public Node get(char c) {
            return next[index(c)];
        }
        
        public boolean contains(char c) {
            return get(c) != null;
        }
        
        public boolean isEnd() {
            return end;
        }
        
        public void markEnd() {
            this.end = true;
        }
        
        private int index(char c) {
            return c - 'a';
        }
        
    }

    /** Initialize your data structure here. */
    public T0208_TriePrefixTree() {
        this.root = new Node('/');
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        if (word == null || word.length() == 0) {
            return;
        }
        
        Node node = root;
        for (char c : word.toCharArray()) {
            if (!node.contains(c)) {
                node.put(c);
            }
            
            node = node.get(c);
        }
        
        node.markEnd();
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        Node node = searchPrefix(word);
        return node != null && node.isEnd();
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        Node node = searchPrefix(prefix);
        return node != null;
    }
    
    private Node searchPrefix(String word) {
        Node node = root;
        if (word == null || word.length() == 0) {
            return node;
        }
        
        for (char c : word.toCharArray()) {
            if (node.contains(c)) {
                node = node.get(c);
            } else {
                return null;
            }
        }
        
        return node;
    }


    public static void main(String[] args) {
        T0208_TriePrefixTree trie = new T0208_TriePrefixTree();

        trie.insert("apple");
        System.out.println(trie.search("apple"));   // 返回 true
        System.out.println(trie.search("app"));     // 返回 false
        System.out.println(trie.startsWith("app")); // 返回 true
        trie.insert("app");
        System.out.println(trie.search("app"));     // 返回 true
    }
    
}
