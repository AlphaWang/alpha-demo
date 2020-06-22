package com.alphawang.algorithm.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  https://leetcode.com/problems/generate-parentheses/
 *  Medium
 */
public class T0022_GenerateParentheses {

    /**
     * 1. 暴力解法：
     *    遍历 1~2n, 构造所有可能的括号字符串
     */
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        char[] chars = new char[2 * n];
        _generateParenthesis(0, chars, res);
        
        return res;
    }
    
    private void _generateParenthesis(int pos, char[] chars, List<String> res) {
         if (pos == chars.length) {
             if ( _isValidParentheses(chars)) {
                 res.add(String.copyValueOf(chars));
             }
         } else {
             chars[pos] = '(';
             _generateParenthesis(pos + 1, chars, res);
             chars[pos] = ')';
             _generateParenthesis(pos + 1, chars, res);
         }
    }
    
    private boolean _isValidParentheses(char[] chars) {
        int balance = 0;
        for(char c : chars) {
            if (c == '(') {
                balance++;
            }
            if (c == ')') {
                balance--;
                if (balance < 0) return false;
            }
        }
        return balance == 0;
    }

    /**
     * 2. 剪枝
     */
    public List<String> generateParenthesis2(int n) {
        List<String> res = new ArrayList<>();
        char[] chars = new char[2 * n];
        int level = 1;
        _generateParenthesis2(n, n, chars, res, level);

        return res;
    }

    private void _generateParenthesis2(int leftAvailable, int rightAvailable, char[] chars, List<String> res, int level) {
        if (leftAvailable == 0 && rightAvailable == 0) {
            res.add(String.copyValueOf(chars));
            return;
        }

        int curPos = chars.length - leftAvailable - rightAvailable;
        if (leftAvailable > 0) {
            chars[curPos] = '(';
            System.out.println(String.format("%s - [%s] append ( --> %s", level, curPos, Arrays.toString(chars)));
            _generateParenthesis2(leftAvailable - 1, rightAvailable, chars, res, level + 1);
            // TODO 无需清除当前状态？
//            chars[curPos] = ' ';
//            System.out.println(String.format("%s - [%s] clear (  --> %s", level, curPos, Arrays.toString(chars)));
        }
        if (rightAvailable > leftAvailable) {
            chars[curPos] = ')';
            System.out.println(String.format("%s - [%s] append ) --> %s", level, curPos, Arrays.toString(chars)));
            _generateParenthesis2(leftAvailable, rightAvailable - 1, chars, res, level + 1);
//            chars[curPos] = ' ';
//            System.out.println(String.format("%s - [%s] clear )  --> %s", level, curPos, Arrays.toString(chars)));
        }
    }

    public static void main(String[] args) {
        T0022_GenerateParentheses sut = new T0022_GenerateParentheses();
        /*
        [
          "((()))",
          "(()())",
          "(())()",
          "()(())",
          "()()()"
        ]
         */
        System.out.println(sut.generateParenthesis2(3));
    }
}
