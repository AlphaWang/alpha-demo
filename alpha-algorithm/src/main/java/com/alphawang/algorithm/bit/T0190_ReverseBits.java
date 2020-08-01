package com.alphawang.algorithm.bit;

/**
 * https://leetcode.com/problems/reverse-bits/
 * Easy
 */
public class T0190_ReverseBits {

    /**
     * 1. n >> 1，依次处理最低位
     * https://leetcode-cn.com/problems/reverse-bits/solution/zhi-qi-ran-zhi-qi-suo-yi-ran-wei-yun-suan-jie-fa-x/
     * 
     * 2ms - 46%
     */
    public int reverseBits(int n) {
        int res = 0;
        int loop = 32;
        while (loop-- > 0) {
            res = res << 1;
            res = res + (n & 1);
            n = n >> 1;
        }
        
        return res;
    }

    /**
     * 2. TODO
     * https://leetcode.com/problems/reverse-bits/discuss/54738/Sharing-my-2ms-Java-Solution-with-Explanation
     */
    public int reverseBits2(int n) {
        if (n == 0) return 0;

        int result = 0;
        for (int i = 0; i < 32; i++) {
            result <<= 1;
            if ((n & 1) == 1) result++;
            n >>= 1;
        }
        return result;
    }

    public static void main(String[] args) {
        // 00000010100101000001111010011100
        // 00111001011110000010100101000000
        test("00000010100101000001111010011100");
        // 11111111111111111111111111111101
        // 10111111111111111111111111111111
        test("11111111111111111111111111111101");
    }
    
    private static void test(String binaryStr) {
        long n = Long.parseLong(binaryStr, 2);
        int res = new T0190_ReverseBits().reverseBits((int) n);
        
        System.out.println(String.format("%s (%s) --> %s (%s)", n,
                                         binaryStr, res, Integer.toBinaryString(res)
                                         ));
    }
}
