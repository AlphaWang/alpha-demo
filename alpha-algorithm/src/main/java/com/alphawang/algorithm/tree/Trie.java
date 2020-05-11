package com.alphawang.algorithm.tree;

public class Trie {
    
    private TrieNode root = new TrieNode('/');

    /**
     * Tire 树节点定义
     */
    public static class TrieNode {
        public char data;
        public TrieNode[] children = new TrieNode[26];

        /**
         * 是否最后一个字符。
         * 用于判断是否完全匹配
         */
        public boolean isEndingChar = false;
        
        public TrieNode(char data) {
            this.data = data;
        }
    }

    /**
     * 插入一个字符串
     * 
     * 逐个遍历字符，每个字符对应一个TrieNode节点
     */
    public void insert(char[] text) {
        TrieNode parent = root;
        for (int i = 0; i < text.length; i++) {
            char data = text[i];
            int index = data - 'a';
            if (parent.children[index] == null) {
                TrieNode child = new TrieNode(data);
                parent.children[index] = child;
            }
            parent = parent.children[index];
        }
        // 标记最后一个节点
        parent.isEndingChar = true;
    }
    
    public boolean find(char[] pattern) {
        TrieNode parent = root;
        for (int i = 0; i < pattern.length; i++) {
            char pattenData = pattern[i];
            int index = pattenData - 'a';

            TrieNode node = parent.children[index];
            if (node == null) {
                return false;
            }
            
            parent = node;
        }
        
        if (parent.isEndingChar) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        
        trie.insert("hello".toCharArray());
        trie.insert("her".toCharArray());

        System.out.println(trie.find("her".toCharArray()));
        System.out.println(trie.find("he".toCharArray()));
    }
    

}
