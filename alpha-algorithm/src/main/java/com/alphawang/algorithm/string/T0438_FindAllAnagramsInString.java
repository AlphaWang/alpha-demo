package com.alphawang.algorithm.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.com/problems/find-all-anagrams-in-a-string/
 * Medium
 */
public class T0438_FindAllAnagramsInString {

    /**
     * 1: 暴力，逐个对比
     *    1153ms - 10%
     */
    public List<Integer> findAnagrams(String s, String p) {
         List<Integer> res = new ArrayList<>();
         int m = p.length();
         int[] encodeP = encode(p, 0, m - 1);
         for (int i = 0; i <= s.length() - m; i++) {
             if (isAnagrams(s, i, i + m - 1, encodeP)) {
                 res.add(i);
             }
         }
         
         return res;
    }
    
    private static int[] encode(String s, int start, int end) {
        int[] encoded = new int[26]; 
        for (int i = start; i <= end; i++) {
            encoded[s.charAt(i) - 'a'] ++;
        }
        
        return encoded;
    }
    
    private static boolean isAnagrams(String s, int start, int end, int[] encodeP) {
        int[] encodeS = encode(s, start, end);
        return Arrays.equals(encodeP, encodeS);
    }

    /**
     * 2： 滑动窗口
     *    6ms - 90%
     */
    public List<Integer> findAnagrams2(String s, String p) {
        List<Integer> res = new ArrayList<>();
        int size = s.length();
        int windowSize = p.length();
        
        int[] encodeP = encode(p, 0, windowSize - 1);
        int[] encodeS = new int[26];
        
        for (int i = 0; i < size; i++) {
            int index = s.charAt(i) - 'a';
            encodeS[index] ++;
            if (i >= windowSize) {
                encodeS[s.charAt(i - windowSize) - 'a'] --;
            }
            
            if (Arrays.equals(encodeP, encodeS)) {
                res.add(i - windowSize + 1);
            }
        }
        
        return res;
    }

    public static void main(String[] args) {
        /*
         * s: "cbaebabacd" p: "abc"
         * [0, 6]
         */
        test("cbaebabacd", "abc");

        /*
         * s: "abab" p: "ab"
         * [0, 1, 2]
         */
        test("abab", "ab");
    }

    public static void test(String s, String p) {
        System.out.println(String.format("%s - %s --> %s", s, p, new T0438_FindAllAnagramsInString().findAnagrams2(s, p)));
    }

}
