package com.alphawang.algorithm.hash;

import java.util.Arrays;

/**
 *  https://leetcode.com/problems/valid-anagram/description/
 *  Easy
 */
public class T0242_ValidAnagram {

    /**
     * 1. 排序，比较
     */
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        
        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();
        Arrays.sort(sChars);
        Arrays.sort(tChars);
        
        return Arrays.equals(sChars, tChars);
    }

    /**
     * 2. 记录每个字母出现的次数
     */
    public boolean isAnagram2(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        
        int[] sCounts = new int[26];
        for (char c : s.toCharArray()) {
            sCounts[c - 'a']++;
        }

        int[] tCounts = new int[26];
        for (char c : t.toCharArray()) {
            tCounts[c - 'a']++;
        }

        return Arrays.equals(sCounts, tCounts);
    }

    /**
     * 3. 优化：无需存储两个次数
     */
    public boolean isAnagram3(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        
        int[] counts = new int[26];
        for (int i = 0; i < s.length(); i++) {
            counts[s.charAt(i) - 'a']++;
            counts[t.charAt(i) - 'a']--;
        }

        for (int count : counts) {
            if (count != 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        T0242_ValidAnagram sut = new T0242_ValidAnagram();

        System.out.println(sut.isAnagram("anagram", "nagaram")); //true
        System.out.println(sut.isAnagram("rat", "car")); //false

        System.out.println(sut.isAnagram2("anagram", "nagaram")); //true
        System.out.println(sut.isAnagram2("rat", "car")); //false

        System.out.println(sut.isAnagram3("anagram", "nagaram")); //true
        System.out.println(sut.isAnagram3("rat", "car")); //false
    }

}
