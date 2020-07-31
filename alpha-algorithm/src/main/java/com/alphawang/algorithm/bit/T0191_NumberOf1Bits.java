package com.alphawang.algorithm.bit;

/**
 * https://leetcode.com/problems/number-of-1-bits/
 * Easy
 * 
 * 
 */
public class T0191_NumberOf1Bits {

    /**
     * 1. 转换为字符串，遍历计数
     * 
     *    2ms - 15%
     */
    public int hammingWeight(int n) {
        int count = 0;
        String binaryStr = Integer.toBinaryString(n);
        for (int i = 0, l = binaryStr.length(); i < l; i++) {
            if (binaryStr.charAt(i) == '1') {
                count++;
            }
        }
        
        return count;
    }

    /**
     * 2. 枚举所有位，%2 == 1，表示最低位为1
     * 
     *    2ms - 15%
     */
    public int hammingWeight2(int n) {
        int count = 0;
        int loop = 32;
        
        while (loop > 0) {
        // 这种判断无法处理 负数    
        // while (n > 0) {
            if ((n & 1) == 1) {
                count++;
            }
            n = n >> 1;
            loop--;
        }
        
        return count;
    }

    /**
     *  2_2. 优化: 去掉 loop = 32  
     *
     *    1ms - 55%
     */
    public int hammingWeight2_2(int n) {
        int count = 0;
        /*
         * 不能用 n > 0，否则无法处理 Integer.MAX_VALUE + 1
         * 
         * Integer.MAX_VALUE     = 0111 1111 1111 1111 1111 1111 1111 1111
         * Integer.MAX_VALUE + 1 = 1000 0000 0000 0000 0000 0000 0000 0000
         */
        while (n != 0) {
            count += (n & 1);
            /*
             * 不能用 n >> 1，否则无法处理 Integer.MAX_VALUE + 1
             * 
             * Integer.MAX_VALUE + 1 >> 1
             * 1000 0000 0000 0000 0000 0000 0000 0000
             * 1100 0000 0000 0000 0000 0000 0000 0000
             * 1110 0000 0000 0000 0000 0000 0000 0000
             * 
             * Integer.MAX_VALUE + 1 >>> 1
             * 1000 0000 0000 0000 0000 0000 0000 0000
             * 0100 0000 0000 0000 0000 0000 0000 0000
             * 0010 0000 0000 0000 0000 0000 0000 0000
             */
            n = n >>> 1;
        }

        return count;
    }

    /**
     * 3. n & mask
     *    
     *    1ms - 55%
     */
    public int hammingWeight3(int n) {
        int count = 0, mask = 1, loop = 32;

        while (loop-- > 0) {
            if ((n & mask) == mask) {
                count++;
            }
            mask = mask << 1;
        }
        return count;
    }

    /**
     * 4.  x = x & (x-1) 清零最低位的1
     * 
     *    0ms - 100%
     */
    public int hammingWeight4(int n) {
        int count = 0;
        while (n != 0) {
            n = n & (n - 1);
            count++;
        }
        return count;
    }

    /**
     * 5. Integer.bitCount(); 
     * 
     *    2ms - 15%
     */
    public int hammingWeight5(int n) {
        return Integer.bitCount(n);
    }


    public static void main(String[] args) {
        // 3
        test("00000000000000000000000000001011");
        // 1
        test("00000000000000000000000010000000");
        //31
        test("11111111111111111111111111111101");
    }
    
    private static void test(String binaryStr) {
        long n = Long.parseLong(binaryStr, 2);
        System.out.println(String.format("%s (%s) --> %s", n,
            binaryStr,
            new T0191_NumberOf1Bits().hammingWeight2_2((int) n)));
    }
}
