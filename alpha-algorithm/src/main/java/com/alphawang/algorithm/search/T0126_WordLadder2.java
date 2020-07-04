package com.alphawang.algorithm.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 *  https://leetcode-cn.com/problems/word-ladder-ii/
 *  Hard
 *  
 *  126: 找出"所有"从 beginWord 到 endWord 的最短转换序列
 *  127: 找到从 beginWord 到 endWord 的最短转换序列的长度
 */
public class T0126_WordLadder2 {

    /**
     * 1. BFS, queue记录访问路径
     *    Time Limit Exceeded
     */
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> res = new ArrayList<>();
        if (wordList == null || !wordList.contains(endWord)) {
            return res;
        }
        
        int length = beginWord.length();
        Map<String, List<String>> patterns = initPatterns(wordList);
        // queue 保存已访问的路径，而非仅仅保存当前word!!
        Queue<List<String>> queue = new LinkedList<>();
        
        List<String> path = new ArrayList<>();
        path.add(beginWord);
        queue.offer(path);
        
        boolean found = false;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                List<String> lastPath = queue.poll();
                String lastWord = lastPath.get(lastPath.size() - 1);
                if (endWord.equals(lastWord)) {
//                    System.out.println("Found " + lastPath);
                    res.add(new ArrayList<>(lastPath));
                    found = true;
                    continue;
                }
                if (!found) {
                    for (int j = 0; j < length; j++) {
                        String pattern = encode(lastWord, j);
                        List<String> matched = patterns.getOrDefault(pattern, new ArrayList<>());
//                        System.out.println(String.format(" %s -- matching %s (%s) -> %s", lastPath, lastWord, pattern, matched));
                        for (String m : matched) {
                            if (!lastPath.contains(m)) {
                                List<String> newPath = new ArrayList<>(lastPath);
                                newPath.add(m);
//                                System.out.println(String.format(" %s -- adding %s (%s) -> %s", lastPath, lastWord, pattern, newPath));
                                queue.offer(newPath);
                            }
                        }
                    } 
                }
                
            }
            
            if (found) {
                return res;
            }
        }

        return res;
    }

    private Map<String, List<String>> initPatterns(List<String> wordList) {
        Map<String, List<String>> patterns = new HashMap<>();
        for (String word : wordList) {
            for (int i = 0; i < word.length(); i++) {
                String pattern = encode(word, i);
                List<String> matched = patterns.getOrDefault(pattern, new ArrayList<>());
                matched.add(word);
                patterns.put(pattern, matched);
            }
        }
        
        return patterns;
    }

    private String encode(String word, int i) {
        char[] chars = word.toCharArray();
        chars[i] = '*';
        return String.valueOf(chars);
    }

    public static void main(String[] args) {
        /*
         * Input:
         * beginWord = "hit",
         * endWord = "cog",
         * wordList = ["hot","dot","dog","lot","log","cog"]
         *
         * Output:
         * [
         *   ["hit","hot","dot","dog","cog"],
         *   ["hit","hot","lot","log","cog"]
         * ]
         */
        test("hit", "cog", Arrays.asList("hot", "dot", "dog", "lot", "log", "cog"));

        /*
         * Input:
         * beginWord = "hit"
         * endWord = "cog"
         * wordList = ["hot","dot","dog","lot","log"]
         *
         * Output: []
         *
         * Explanation: The endWord "cog" is not in wordList, therefore no possible transformation.
         */
        test("hit", "cog", Arrays.asList("hot","dot","dog","lot","log"));

        /*
         * 0
         */
        test("hot","dog", Arrays.asList("hot","dog"));
    }

    private static void test(String beginWord, String endWord, List<String> wordList) {
        T0126_WordLadder2 sut = new T0126_WordLadder2();
        System.out.println(String.format("%s : %s --> %s", wordList, beginWord, endWord));
        System.out.println(sut.findLadders(beginWord, endWord, wordList));
    }

}
