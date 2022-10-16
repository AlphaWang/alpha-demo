package com.alphawang.algorithm.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * https://leetcode.com/problems/reverse-words-in-a-string/
 * Medium
 *
 * Input: s = "the sky is blue"
 * Output: "blue is sky the"
 */
public class T0151_ReverseWordsInString {

    /**
     * 1. 库函数
     *    6ms - 57%
     */
    public String reverseWords(String s) {
        if (s == null) {
            return s;
        }

        s = s.trim();
        List<String> words = Arrays.asList(s.split(" +"));
//        List<String> words = Arrays.asList(s.split("\\s+"));
        Collections.reverse(words);
        return String.join(" ", words);
    }

    /**
     * 2. 先翻转字符串，再逐个翻转单词
     *    注意：考虑中间的连续空格
     *    
     *    2ms - 91%
     */
    public String reverseWords2(String s) {
        if (s == null) {
            return s;
        }
        
        char[] chars = s.trim().toCharArray();
        int m = chars.length;
        swap(chars, 0, m - 1);
        System.out.println(String.valueOf(chars));
        
        int i = 0; int j = 0;
        int target = 0;
        for (; j <= m; j++) {
            if (j > m) {
                break;
            }
            if (j < m && chars[j] == ' ' || j == m) {
                swapWithTargetIndex(chars, i, j - 1, target);
                
                // 下一个待插入点
                target += j - i + 1;

                System.out.println(String.valueOf(String.format("[%s, %s], target = %s", i, j-1, target)));

                // 上一词尾 ~ 下一待插入点之间，设置为空格
                if (target < m) {
                    chars[target - 1] = ' ';
                }
                
                while (j < m && chars[j] == ' ') {
                    j++;
                }
                i = j;
            }
        }
        
        char[] res = new char[target-1];
        System.arraycopy(chars, 0, res, 0, target-1);
        return String.valueOf(res);
    }

    private void swapWithTargetIndex(char[] chars, int i, int j, int target) {
        if (target == i) {
            swap(chars, i, j);
        } else {
            while (i <= j) {
                //TODO 可能会覆盖
                if (target < i) {
                    chars[target++] = chars[j--];
                } else {
                    swap(chars, i, j);
                    break;
                } 
            }
        }
    }
    
    private void swap(char[] chars, int i, int j) {
        while (i < j) {
            char tmp = chars[i];
            chars[i] = chars[j];
            chars[j] = tmp;
            i++;
            j--;
        }
    }

    /**
     * 3. 栈
     *    8ms - 45% 
     */
    public String reverseWords3(String s) {
        if (s == null) {
            return s;
        }
        
        s = s.trim();
        Deque<String> words = new LinkedList<>();
        
        int start = 0;
        int m = s.length();
        for (int i = 0; i <= m; i++) {
            if (i >= m - 1) {    // 对最后一个word的end特殊处理
                words.addFirst(s.substring(start, m));
                break;
            }
            else if (i < m && s.charAt(i) == ' ') {
                words.addFirst(s.substring(start, i));

                while (i < m - 1 && s.charAt(i) == ' ') {
                    i++;
                }
                start = i;
            }
        }

        System.out.println(words);
        return String.join(" ", words);
    }

    /**
     * 4. 递归找出第一个单词，放到末尾
     *   8ms
     */
    public String reverseWords4(String s) {
        List<String> list = new ArrayList<>();
        int split = split(s);

        while (split != -1) {
            String sub = s.substring(0, split);
            if (!" ".equals(sub) && !"".equals(sub)) {
                list.add(sub);
            }

            s = s.substring(split + 1);
            split = split(s);
        }
        if (!" ".equals(s) && !"".equals(s)) {
            list.add(s);
        }
        System.out.println("list = " + list);

        StringBuilder sb = new StringBuilder();
        for (int i = list.size() - 1; i >= 0; i--) {
            sb.append(list.get(i));
            sb.append(" ");
        }
        System.out.println("res = " + sb);

        return sb.substring(0, sb.length() - 1);
     }

    private int split(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                return i;
            }
        }

        return -1;
    }
    
    

    public static void main(String[] args) {
             
//        // "blue is sky the"
//        test("the sky is blue");
//        // "world! hello"
//        test("  hello world!  ");
//        // "example good a"
//        test("a good   example");
//
//        // "a"
//        test("a");
        // "b a"
        test("   a   b ");
    }
    
    private static void test(String s) {
        System.out.println(String.format("%s -> \n%s\n", s, new T0151_ReverseWordsInString().reverseWords4(s)));
    }
    

}
