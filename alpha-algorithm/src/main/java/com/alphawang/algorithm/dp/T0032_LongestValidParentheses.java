package com.alphawang.algorithm.dp;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

/**
 * https://leetcode.com/problems/longest-valid-parentheses/
 * Hard 
 * 
 * 给定一个只包含 '(' 和 ')' 的字符串，找出最长的包含有效括号的子串的长度。
 */
public class T0032_LongestValidParentheses {

    /**
     * 1. 栈，保存括号
     *    思路错误，子串需要连续！ 反例："()(()" --> 4, 正确答案是2
     */
    public int longestValidParentheses(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int count = 0;
        Deque<Character> stack = new LinkedList<>();
        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(c);
            }
            if (c == ')') {
                if (!stack.isEmpty() && stack.pop() == '(') {
                    count++;
                }
            }
        }
        
        return count * 2;
    }

    /**
     * 1. 暴力，for i = n ~ 2, 遍历子串 检查是否 valid
     *    O(N^3)
     */
    public int longestValidParentheses1(String s) {
        int length = s.length();
        int start = length % 2 == 0 ? length : length - 1;
        for (int i = start; i > 0; i = i - 2) {
            for (int j = 0; j <= length - i; j++) {
                String subStr = s.substring(j, j + i);
                System.out.println(String.format("%s~%s %s", j, j + i, subStr));
                if (isValidParentheses(subStr)) {
                    return i;
                }
            }
        }
        
        return 0;
    }
    
    private boolean isValidParentheses(String s) {
        int count = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                count++;
            } else {
                count--;
            }
            if (count < 0) 
                return false;
        }
        return count == 0;
    }

    /**
     * 2. 栈，保存index
     *    保持栈底元素为当前已经遍历过的元素中「最后一个"没有被匹配的右括号"的下标」，即有效子串的分割线
     *    
     *    //TODO 
     */
    public int longestValidParentheses2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int maxans = 0;
        Stack<Integer> stack = new Stack<>();
        // 默认的合法子串分割线 == -1
        stack.push(-1); 
        for (int i = 0; i < s.length(); i++) {
            System.out.println(String.format("[%s] char = %s, max = %s, stack = %s", i, s.charAt(i), maxans, stack));
            // 左括号：入栈
            if (s.charAt(i) == '(') {
                stack.push(i);
                System.out.println(String.format("[%s] -- push %s ",i, i));
            } 
            // 右括号：出栈；如果栈空 则表示达到合法子串分割线，将其入栈
            else {
                int pop = stack.pop();
                System.out.println(String.format("[%s] -- pop %s ",i, pop));
                
                if (stack.empty()) {
                    // 栈底元素 == 「最后一个"没有被匹配的右括号"的下标」
                    // 如果栈空 则表示达到合法子串分割线，将其入栈
                    stack.push(i);
                    System.out.println(String.format("[%s] ++ push %s ",i, i));
                } else {
                    // 否则更新max 
                    maxans = Math.max(maxans, i - stack.peek());
                    System.out.println(String.format("[%s] ++ update max %s ",i, maxans));
                }
            }

//            System.out.println(String.format("[%s] char = %s, max = %s, stack = %s", i, s.charAt(i), maxans, stack));
        }
        return maxans;
    }

    /**
     * 3. DP
     *    状态: dp[i] 从0~i的最长有效子串。注意如果i是左括号，则dp[i] = 0 
     *    方程: dp[i] = 2 + dp[i-1] + dp[i - dp[i-1] - 2]
     *    
     *         >> x = i - dp[i-1] - 1: 表示与i对应的左括号位置
     *         
     *         1："2"，与x位置匹配的基础长度 2
     *         2：dp[i-1]: 前一个位置的长度
     *         3：dp[i - dp[i-1] - 2] 即 x - 1：x之前的长度
     *         
     *   这个状态转移方程，怎么想到的！！！
     *   
     *   O(N)
     *   2ms - 78%
     */        
    public int longestValidParentheses3(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int length = s.length();
        int max = 0;
        int[] dp = new int[length];
        
        for (int i = 1; i < length; i++) {
            int match = i - dp[i-1] - 1;
            if (s.charAt(i) == ')' && match >= 0 && s.charAt(match) == '(') {
                if (match >= 1) {
                    dp[i] = 2 + dp[i - 1] + dp[match - 1];
                } else {
                    dp[i] = 2 + dp[i - 1];
                }
                
                max = Math.max(max, dp[i]);
            }
        }
        return max;
    }


    /**
     * 4. 正向+逆向遍历，记录左右括号个数: 
     *      if left == right, 找到有效子串，更新max
     *      if left < right,  右括号多，重置数目，重新统计 
     *      
     *    为何要逆向再来一遍？"(( ( (())" --> 如果只正向，i=2位置的左括号会导致后续的有效子串被忽略
     *    
     *    O(N)
     *    2ms - 78%
     */
    public int longestValidParentheses4(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int leftCount = 0, rightCount = 0, max = 0;
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '(') {
                leftCount++;
            } else {
                rightCount++;
            }
            // 左右括号相等，找到有效子串，更新max
            if (leftCount == rightCount) {
                max = Math.max(max, 2 * leftCount);
            }
            // 右括号多，重置
            if (rightCount > leftCount) {
                leftCount = 0;
                rightCount = 0;
            }
        }

        // 逆向再来一遍
        // "(( ( (())"
        leftCount = 0;
        rightCount = 0;
        for (int i = chars.length - 1; i >= 0; i--) {
            if (chars[i] == '(') {
                leftCount++;
            } else {
                rightCount++;
            }
            // 左右括号相等，找到有效子串，更新max
            if (leftCount == rightCount) {
                max = Math.max(max, 2 * leftCount);
            }
            // 右括号多，重置
            if (leftCount > rightCount) {
                leftCount = 0;
                rightCount = 0;
            }
        }
        
        return max;
    }

    public static void main(String[] args) {
        // 2
        test("(()");
        // 4
        test(")()())");
        // 2
        test("()(()"); 
        // 6
        test("()(())");
        // 4
        test("((((())");
        // 4
        test("())(())");
    }
    
    private static void test(String s) {
        T0032_LongestValidParentheses sut = new T0032_LongestValidParentheses();
        System.out.println(s + " --> " + sut.longestValidParentheses2(s));
    }

}
