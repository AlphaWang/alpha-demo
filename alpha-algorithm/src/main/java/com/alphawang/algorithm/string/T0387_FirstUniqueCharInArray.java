package com.alphawang.algorithm.string;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * https://leetcode.com/problems/first-unique-character-in-a-string/
 * Easy
 */
public class T0387_FirstUniqueCharInArray {

    /**
     * 1. 两次遍历：1）记录次数、2）统计
     *    32ms - 59%
     */
    public int firstUniqChar(String s) {
        if (s == null || s.length() == 0) {
            return -1;
        }
        int m = s.length();
        Map<Character, Integer> chars = new HashMap<>();
        
        for (int i = 0; i < m; i++) {
            char c = s.charAt(i);
            chars.put(c, chars.getOrDefault(c, 0) + 1);
        }
        
        for (int i = 0; i < m; i++) {
            if (chars.get(s.charAt(i)) == 1) {
                return i;
            }
        }
        
        return -1;
    }

    /**
     * 2. 一次遍历，用 Hash + Set 存储
     *    Set: 已遍历过的元素
     *    LinkedHashMap: 非重复元素
     *    
     *    29ms - 36%
     */
    public int firstUniqChar2(String s) {
        if (s == null || s.length() == 0) {
            return -1;
        }
        int m = s.length();
        Set<Character> visited = new HashSet<>();
        Map<Character, Integer> uniqueChars = new LinkedHashMap<>();
        for (int i = 0; i < m; i++) {
            char c = s.charAt(i);
            if (visited.contains(c)) {
                if (uniqueChars.containsKey(c)) {
                    uniqueChars.remove(c);
                }
            } else {
                visited.add(c);
                uniqueChars.put(c, i);
            }
        }
        
        return uniqueChars.size() == 0
          ? -1 : uniqueChars.entrySet().iterator().next().getValue();
    }

    /**
     * 3: 一次倒序遍历
     *    Q: 如何识别 -1 的场景？ TODO
     *
     *         dicts={}
     *         for i in range(len(s)-1, -1, -1):
     *             if s[i] not in dicts:
     *                     dicts[s[i]] = i
     *             else:
     *                 dicts[s[i]] = len(s) 
     *         return -1 if min(dicts.values()) == len(s) else min(dicts.values())
     *
     */
    public int firstUniqChar3(String s) {
        if (s == null || s.length() == 0) {
            return -1;
        }
        int m = s.length();
        int minIndex = Integer.MAX_VALUE;
        Set<Character> chars = new HashSet<>();
        for (int i = m - 1; i >= 0; i--) {
            char c = s.charAt(i);

            if (!chars.contains(c)) {
                minIndex = Math.min(i, minIndex);
                chars.add(c);
            } else {
                // 当遍历结束，[0] 有重复，且[minIndex] ！= [0] 
                if (i == 0) {
                    return -1;
                }
            }
//
//            if (i == 0 && i != minIndex && s.charAt(i) == s.charAt(minIndex)) {
//                return -1;
//            }
        }

        return minIndex;
    }

    public static void main(String[] args) {
        // 0: l不重复
        test("leetcode");
        
        // 2: v不重复
        test("loveleetcode");
        
        // -1: 
        test("cc");
        
        // -1: 
        test("acaadcad");
    }
    
    private static void test(String s) {
        System.out.println(String.format("%s -> %s", s, new T0387_FirstUniqueCharInArray().firstUniqChar2(s)));
    }

}
