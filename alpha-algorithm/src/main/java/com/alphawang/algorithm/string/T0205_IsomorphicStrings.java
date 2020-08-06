package com.alphawang.algorithm.string;

import java.util.HashMap;
import java.util.Map;

/**
 *  https://leetcode.com/problems/isomorphic-strings/
 *  Easy
 */
public class T0205_IsomorphicStrings {

    /**
     * 1. Map 记录"所在组的位置" 
     *    16ms - 27%
     */
    public boolean isIsomorphic(String s, String t) {
        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();
        int m = sChars.length;

        Map<Character, Integer> sIndex = new HashMap<>();
        Map<Character, Integer> tIndex = new HashMap<>();
        for (int i = 0; i < m; i++) {
            int sCode;
            if (sIndex.containsKey(sChars[i])) {
                sCode = sIndex.get(sChars[i]);
            } else {
                sCode = i;
                sIndex.put(sChars[i], i);
            }

            int tCode;
            if (tIndex.containsKey(tChars[i])) {
                tCode = tIndex.get(tChars[i]);
            } else {
                tCode = i;
                tIndex.put(tChars[i], i);
            }
            
            if (sCode != tCode) {
                return false;
            }
        }
        
        return true;
    }

    /**
     * 2. Map 记录 字符对应关系
     *    5ms - 86%
     */
    public boolean isIsomorphic2(String s, String t) {
        return helper(s, t) && helper(t, s);
    }
    
    public boolean helper(String s, String t) {
        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();
        int m = sChars.length;
        
        Map<Character, Character> pair = new HashMap<>();
        for (int i = 0; i < m; i++) {
            char sChar = sChars[i];
            char tChar = tChars[i];
            if (pair.containsKey(sChar)) {
                if (pair.get(sChar) != tChar) {
                    return false;
                }
            } else {
                pair.put(sChar, tChar);
            }
        }
        
        return true;
    }

    /**
     * 3. encode
     * 
     *    8ms - 61%
     */
    public boolean isIsomorphic3(String s, String t) {
        return encode(s).equals(encode(t)); 
    }
    
    private String encode(String s) {
        char[] chars = s.toCharArray();
        int[] encoded = new int[128]; // why 128?
        int m = chars.length;
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            if (encoded[chars[i]] == 0) {
                encoded[chars[i]] = i + 1;
            }
            sb.append(encoded[chars[i]]);
        }

        System.out.println(sb);
        return sb.toString();
    }

    public static void main(String[] args) {
        /*
         * Input: s = "egg", t = "add"
         * Output: true
         */
        test("egg", "add");
        /*
         * Input: s = "foo", t = "bar"
         * Output: false
         */
        test("foo", "bar");
        /*
         * Input: s = "paper", t = "title"
         * Output: true
         */
        test("paper", "title");
        
        // false
        test("ab", "aa");
        
    }
    
    private static void test(String s, String t) {
        System.out.println(String.format("%s - %s --> %s", s, t, new T0205_IsomorphicStrings().isIsomorphic3(s, t)));
    }

}
