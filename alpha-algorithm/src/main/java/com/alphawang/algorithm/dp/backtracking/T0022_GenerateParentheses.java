package com.alphawang.algorithm.dp.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/generate-parentheses
 * 
 * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
 *
 * 示例：
 *
 * 输入：n = 3
 * 输出：[
 *        "((()))",
 *        "(()())",
 *        "(())()",
 *        "()(())",
 *        "()()()"
 *      ]
 *
 */
public class T0022_GenerateParentheses {

    public List<String> generateParenthesis(int n) {
        dfs(n, n, "");
        return res;
    }

    List<String> res = new ArrayList<>();
    private void dfs(int left, int right, String curStr) {
        // 剪枝：如果已用掉的右括号更多（剩余的左括号更多），则非法
        // 已经在调用 dfs 时判断！
//        if (left > right) {
//            System.out.println(String.format("skip %s-%s", left, right));
//            return;
//        }
//        if (left < 0 || right < 0) {
//            System.out.println(String.format("skip %s-%s", left, right));
//            return;
//        }

        // 左右括号都不剩余了，递归终止
        if (left == 0 && right == 0) { 
            res.add(curStr);
            return;
        }

        // 如果左括号还剩余的话，可以拼接左括号
        if (left > 0) { 
            dfs(left - 1, right, curStr + "(");
        }
        // 如果右括号剩余多于左括号剩余的话，可以拼接右括号
        if (right > left) { 
            dfs(left, right - 1, curStr + ")");
        }
    }

    public static void main(String[] args) {
        T0022_GenerateParentheses sut = new T0022_GenerateParentheses();
        System.out.println(sut.generateParenthesis(3));
    }
    
    
}
