package com.alphawang.algorithm.string;

/**
 * https://leetcode.com/problems/reverse-words-in-a-string-iii/submissions/
 * Easy
 */
public class T0557_ReverseWordsInString2 {

    /**
     * 1. 遍历找单词，翻转
     *    8ms - 56%
     */
    public String reverseWords(String s) {
        if (s == null) {
            return s;
        }
        
        s = s.trim();
        int m = s.length();
        if (m < 2) {
            return s;
        }

        char[] chars = s.toCharArray();
        int start = 0, end = 1;
        while (end <= m) {
            if (end < m && chars[end] == ' ' && chars[end - 1] != ' '  // meet ' '
              || end == m) {                                           // meet end
                System.out.println(String.format("[%s, %s]", start, end-1));
                swap(chars, start, end-1);
                end++;
                start = end;

                System.out.println(start);
            } else {
                end++;
            }
        }

        return String.valueOf(chars);
    }

    /**
     * 1. 遍历找单词，翻转
     *    - 优化判断逻辑: 按 start 遍历，无需特殊处理结束字符
     *    
     *    5ms - 75%
     */
    public String reverseWords2(String s) {
        if (s == null) {
            return s;
        }

        s = s.trim();
        char[] chars = s.toCharArray();
        int m = chars.length;
        
        
        for (int i = 0; i < m; i++) {
            if (chars[i] != ' ') {
                int end = i;
                while (end < m - 1 && chars[end + 1] != ' ') {
                    end++;
                }
                
                System.out.println(String.format("swap [%s, %s]", i, end));
                swap(chars, i, end);
                i = end;
            }
        }
        
        return String.valueOf(chars);
    }

    private void swap(char[] chars, int start, int end) {
        while (start < end) {
            char tmp = chars[start];
            chars[start] = chars[end];
            chars[end] = tmp;
            
            start++;
            end--;
        }
    }

    /**
     * 扩展：保留非字母的位置
     */
    public String reverseWords2_2(String s) {
        if (s == null) {
            return s;
        }

        s = s.trim();
        char[] chars = s.toCharArray();
        int m = chars.length;


        for (int i = 0; i < m; i++) {
            if (chars[i] != ' ') {
                int end = i;
                while (end < m - 1 && chars[end + 1] != ' ') {
                    end++;
                }

                System.out.println(String.format("swap [%s, %s]", i, end));
                swap2(chars, i, end);
                i = end;
            }
        }

        return String.valueOf(chars);
    }

    private void swap2(char[] chars, int start, int end) {
        while (start < end) {
            char startChar = chars[start];
            char endChar = chars[end];
            if (!Character.isLetter(startChar)) {
                start++;
                continue;
            }
            if (!Character.isLetter(endChar)) {
                end--;
                continue;
            }
            chars[start] = endChar;
            chars[end] = startChar;

            start++;
            end--;
        }
    }

    
    public static void main(String[] args) {
        /*
         * "Let's take LeetCode contest"
         * "s'teL ekat edoCteeL tsetnoc"
         */
        test("Let's take LeetCode contest");
        test("How did we ever live without Coupang?");
        test("How did we ever  ^&* live without Coupang?");
        test("");
        test("How");
        test("How?");
        test("How ?");
    }
    
    private static void test(String s) {
        System.out.println(String.format("%s -->\n%s", s, new T0557_ReverseWordsInString2().reverseWords2_2(s)));
    }
}
