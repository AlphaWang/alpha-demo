package com.alphawang.algorithm.string;

/**
 * https://leetcode.com/problems/string-to-integer-atoi/
 * Medium
 */
public class T0008_StringToIntegerAtoi {

    /**
     * 1: 先解析 sign, 再 res * 10 + curr.
     *    注意：判断数据范围
     *    
     *    2ms - 87%
     */
    public int myAtoi(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        
        Integer sign = null;
        char[] chars = str.toCharArray();
        
        long res = 0L;
        for (int i = 0; i < chars.length; i++) {
            if (sign == null) {
                while (i < chars.length - 1 && chars[i] == ' ') {  // remove pre-0
                    i++;
                }
                if (chars[i] == '-') {
                    sign = -1;
                } else if (chars[i] == '+') {
                    sign = 1;
                } else if (Character.isDigit(chars[i])) {
                    sign = 1;
                    i--;
                } else {
                    return 0;
                }
                continue;
            }
            
            if (Character.isDigit(chars[i]))  {
                int curr = chars[i] - '0';
                res = res * 10 + curr;
                
                if (res > Integer.MAX_VALUE) {
                    return parse(res, sign);
                }
            } else {
                break;
            }
            
        }
        
        return parse(res, sign);
    }
    
    private int parse(long res, Integer sign) {
        if (sign == 1 && (res >= Integer.MAX_VALUE || res < 0)) {
            return Integer.MAX_VALUE;
        }
        if (sign == -1 && res > Integer.MAX_VALUE || res < 0) {
            return Integer.MIN_VALUE;
        }
        
        return (int) res * sign;
    }

    public static void main(String[] args) {
        test("42");  // 42
        test("    -42");  // -42
        test("4193 with words");  // 4193
        test("-91283472332");         // MIN_VALUE  (-2147483648)
        // < Long.MIN
        test("-9223372036854775809"); // MIN_VALUE  (-2147483648)
        // > Long.MAX
        test("9223372036854775808");  // MAX_VALUE  (2147483647)  
        test("18446744073709551617");  // MAX_VALUE  (2147483647)  
        test("-2147483647");          // -2147483647
        test("words and 987"); // 0
        test(""); // 0
        test(" "); //0
        test("+1"); // 1
    }

    public static void test(String str) {
        System.out.println(String.format("%s --> %s", str, new T0008_StringToIntegerAtoi().myAtoi(str)));
    }
}
