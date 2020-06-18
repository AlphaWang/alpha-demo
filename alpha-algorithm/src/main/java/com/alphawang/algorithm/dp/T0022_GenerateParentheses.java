package com.alphawang.algorithm.dp;

import java.util.ArrayList;
import java.util.List;

/**
 *  https://leetcode.com/problems/generate-parentheses/
 *  Medium
 */
public class T0022_GenerateParentheses {

    /**
     * 1. 暴力解法：
     *    遍历 1~2n, 构造括号字符串
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
        _generateParenthesis2(n, n, chars, res);

        return res;
    }

    private void _generateParenthesis2(int leftAvailable, int rightAvailable, char[] chars, List<String> res) {
        if (leftAvailable == 0 && rightAvailable == 0) {
            res.add(String.copyValueOf(chars));
        }

        int curPos = chars.length - leftAvailable - rightAvailable;
        if (leftAvailable > 0) {
            chars[curPos] = '(';
            _generateParenthesis2(leftAvailable - 1, rightAvailable, chars, res);
            chars[curPos] = ' ';
        }
        if (rightAvailable > leftAvailable) {
            chars[curPos] = ')';
            _generateParenthesis2(leftAvailable, rightAvailable - 1, chars, res);
            chars[curPos] = ' ';
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
