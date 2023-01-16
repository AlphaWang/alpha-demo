package com.alphawang.algorithm.dp.backtracking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://leetcode.com/problems/letter-combinations-of-a-phone-number/
 * Medium
 * 
 * Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent.
 * A mapping of digit to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.
 */
public class T0017_LetterCombinationsOfPhoneNumber {

    /**
     * 1. 回溯
     *    1ms - 82%
     */
    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        if (digits == null || digits.length() == 0) {
            return res;
        }

        Map<Character, String> letterMap = new HashMap<>();
        letterMap.put('2', "abc");
        letterMap.put('3', "def");
        letterMap.put('4', "ghi");
        letterMap.put('5', "jkl");
        letterMap.put('6', "mno");
        letterMap.put('7', "pqrs");
        letterMap.put('8', "tuv");
        letterMap.put('9', "wxyz");
        
//        dfs(digits.toCharArray(), letterMap, res, "");
        dfs2(digits.toCharArray(), 0, letterMap, res, "");
        return res;
    }

    /**
     *  WRONG: 有重复元素 
     *  dfs 中需要区分当前level，即当前待处理的是第几个数字
     *  
     *  [aa, ab, ac, ad, ae, af, ba, bb, bc, bd, be, bf, ca, cb, cc, cd, ce, cf, da, db, dc, dd, de, df, ea, eb, ec, ed, ee, ef, fa, fb, fc, fd, fe, ff]
     */
    private void dfs(char[] nums, Map<Character, String> letterMap, List<String> res, String curr) {
        System.out.println("---- testing " + curr);
        if (curr.length() == nums.length) {
            res.add(curr);
            return;
        }
        
        for (char c : nums) { //TODO WRONG
            String letters = letterMap.get(c);
            for (char letter : letters.toCharArray()) {
                dfs(nums, letterMap, res, curr.concat(String.valueOf(letter)));
            }
        }
    }

    /**
     * CORRECT
     */
    private void dfs2(char[] nums, int level, Map<Character, String> letterMap, List<String> res, String curr) {
        System.out.println("---- testing " + curr);
        // terminator
        if (curr.length() == nums.length) {
            res.add(curr);
            return;
        }
        
        // process
        char c = nums[level]; // ！！！
        String letters = letterMap.get(c);
        for (char letter : letters.toCharArray()) {
            //drill down
            dfs2(nums, level + 1, letterMap, res, curr.concat(String.valueOf(letter)));
        }
        
        // reverse
        // 没有修改全局变量，无需reverse
    }

    /**
     * 1. 迭代
     *    TODO
     */
    public List<String> letterCombinations2(String digits) {
        List<String> res = new ArrayList<>();

        Map<Character, String> letterMap = new HashMap<>();
        letterMap.put('2', "abc");
        letterMap.put('3', "def");
        letterMap.put('4', "ghi");
        letterMap.put('5', "jkl");
        letterMap.put('6', "mno");
        letterMap.put('7', "pqrs");
        letterMap.put('8', "tuv");
        letterMap.put('9', "wxyz");
        
        
        for (char digit : digits.toCharArray()) {
            String output = "";
            String letters = letterMap.get(digit);
            for (char letter : letters.toCharArray()) {
                
            }
        }

        return res;
    }

    public static void main(String[] args) {
        T0017_LetterCombinationsOfPhoneNumber sut = new T0017_LetterCombinationsOfPhoneNumber();
        
        /*
        Input: "23"
        Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
        */
        System.out.println(sut.letterCombinations("23"));
        
    }
}
