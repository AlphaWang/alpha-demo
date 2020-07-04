package com.alphawang.algorithm.search;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 *  https://leetcode.com/problems/minimum-genetic-mutation/
 *  Medium
 *  
 * 一条基因序列由一个带有8个字符的字符串表示，其中每个字符都属于 "A", "C", "G", "T"中的任意一个。
 * 假设我们要调查一个基因序列的变化。一次基因变化意味着这个基因序列中的一个字符发生了变化。
 * 例如，基因序列由"AACCGGTT" 变化至 "AACCGGTA" 即发生了一次基因变化。
 * 与此同时，每一次基因变化的结果，都需要是一个合法的基因串，即该结果属于一个基因库。
 * 
 * 现在给定3个参数 — start, end, bank，分别代表起始基因序列，目标基因序列及基因库，
 * 请找出能够使起始基因序列变化为目标基因序列所需的最少变化次数。如果无法实现目标变化，请返回 -1。
 *  
 *  类似 127-单词接龙： https://leetcode.com/problems/word-ladder/
 */
public class T0433_MinimumGeneticMutation {

    /**
     * 1. BFS
     *    2ms - 21%
     */
    public int minMutation(String start, String end, String[] bank) {
        if (bank == null || bank.length == 0) {
            return -1;
        }

        Set<String> banks = new HashSet<>(Arrays.asList(bank));
        if (!banks.contains(end)) {
            return -1;
        }
        
        char[] mutations = new char[] {'A', 'C', 'G', 'T'};
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.offer(start);
        visited.add(start);
        int level = 0;
        int length = start.length();
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            level++;
            for (int i = 0; i < size; i++) {
                String string = queue.poll();
                if (end.equals(string)) {
                    return level - 1;
                }
                for (int j = 0; j < length; j++) {
                     for (char ch : mutations) {
                         String mutated = mutate(string, ch, j);
                         if (banks.contains(mutated) && !visited.contains(mutated)) {
                             System.out.println("+ " + mutated);
                             queue.offer(mutated);
                             visited.add(mutated);
                         }
                     }
                }
            }
        }
        
        return -1;
    }
    
    private String mutate(String string, char ch, int index) {
        char[] characters = string.toCharArray();
        characters[index] = ch;
        
        return String.valueOf(characters);
    }

    public static void main(String[] args) {
        /*
         * start: "AACCGGTT"
         * end:   "AACCGGTA"
         * bank: ["AACCGGTA"]
         *
         * return: 1
         */
        test("AACCGGTT", "AACCGGTA", new String[] {"AACCGGTA"});
        /*
         * start: "AACCGGTT"
         * end:   "AAACGGTA"
         * bank: ["AACCGGTA", "AACCGCTA", "AAACGGTA"]
         *
         * return: 2
         */
        test("AACCGGTT", "AAACGGTA", new String[] {"AACCGGTA", "AACCGCTA", "AAACGGTA"});
        /*
         * start: "AAAAACCC"
         * end:   "AACCCCCC"
         * bank: ["AAAACCCC", "AAACCCCC", "AACCCCCC"]
         * 
         * return: 3
         */
        test("AAAAACCC", "AACCCCCC", new String[] {"AAAACCCC", "AAACCCCC", "AACCCCCC"});
        /*
         * "AACCGGTT"
         * "AACCGCTA"
         * ["AACCGGTA","AACCGCTA","AAACGGTA"]
         */
        test("AACCGGTT", "AACCGCTA", new String[] {"AACCGGTA","AACCGCTA","AAACGGTA"});
    }

    private static void test(String start, String end, String[] bank) {
        T0433_MinimumGeneticMutation sut = new T0433_MinimumGeneticMutation();
        System.out.println(sut.minMutation(start, end, bank));
    }


}
