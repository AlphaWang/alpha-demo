package com.alphawang.algorithm.recursion;

/**
 * https://leetcode.com/problems/n-queens-ii/
 * Hard
 * 
 * 返回解决方案的数量
 */
public class T0052_NQueen2 {

    /**
     * 2. 位运算
     *    TODO
     */
    private int size;
    private int count;
    
    private void solve(int row, int ld, int rd) {
        if (row == size) {
            count++;
            return;
        }
        int pos = size & (~(row | ld | rd));

        System.out.println(String.format("---[%s] %s : %s", row, Integer.toBinaryString(ld), Integer.toBinaryString(rd)));
        while (pos != 0) {
            /**
             * 得到最低位的 1
             */
            int p = pos & (-pos);
            System.out.println(String.format("---[%s] pos = %s, p = %s", row, Integer.toBinaryString(pos), Integer.toBinaryString(p)));
            
            pos -= p; // pos &= pos - 1; 
            System.out.println(String.format("---[%s] pos = %s", row, Integer.toBinaryString(pos)));
            
            solve(row | p, (ld | p) << 1, (rd | p) >> 1);
        }
    }
    
    public int totalNQueens(int n) {
        count = 0;
        size = (1 << n) - 1;
        solve(0, 0, 0);
        return count;
    }

    public static void main(String[] args) {
        T0052_NQueen2 sut = new T0052_NQueen2();

        /*
         4 --> 2
         
         [
         [".Q..",  // Solution 1
           "...Q",
           "Q...",
           "..Q."],

         ["..Q.",  // Solution 2
          "Q...",
          "...Q",
          ".Q.."]
         ]
         */
        System.out.println(sut.totalNQueens(4));
    }
    
}
