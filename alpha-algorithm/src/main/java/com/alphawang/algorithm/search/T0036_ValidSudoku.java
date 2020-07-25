package com.alphawang.algorithm.search;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/valid-sudoku/description/
 * Medium
 * 
 * 数字 1-9 在每一行只能出现一次。
 * 数字 1-9 在每一列只能出现一次。
 * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
 */
public class T0036_ValidSudoku {

    /**
     * 1. 用3个数组，分别存储 行、列、块 的已出现数字个数
     *    注意 blockIndex = (i / 3) * 3 + j
     *    
     *    2ms - 89%
     */
    public boolean isValidSudoku(char[][] board) {
        if (board == null || board.length != 9) {
            return false;
        }
        
        int[][] row = new int[9][9];
        int[][] col = new int[9][9];
        int[][] block = new int[9][9];
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    continue;
                }
                
                int num = board[i][j] - '1';
                if (row[i][num] > 0) {
                    return false;
                } else {
                    row[i][num]++;
                }
                
                if (col[j][num] > 0) {
                    return false;
                } else {
                    col[j][num]++;
                }
                
                int blockIndex = blockIndex(i, j);
                if (block[blockIndex][num] > 0) {
                    return false;
                } else {
                    block[blockIndex][num]++;
                }
            }
        }
        return true;
    }
    
    private int blockIndex(int i, int j) {
        return (i / 3) * 3 + j / 3;
    }

    public static void main(String[] args) {
        // true
        test(new char[][] {
          {'5','3','.','.','7','.','.','.','.'},
          {'6','.','.','1','9','5','.','.','.'},
          {'.','9','8','.','.','.','.','6','.'},
          {'8','.','.','.','6','.','.','.','3'},
          {'4','.','.','8','.','3','.','.','1'},
          {'7','.','.','.','2','.','.','.','6'},
          {'.','6','.','.','.','.','2','8','.'},
          {'.','.','.','4','1','9','.','.','5'},
          {'.','.','.','.','8','.','.','7','9'}
        });

        //false
        test(new char[][] {
           {'8','3','.','.','7','.','.','.','.'}, 
           {'6','.','.','1','9','5','.','.','.'}, 
           {'.','9','8','.','.','.','.','6','.'},
           {'8','.','.','.','6','.','.','.','3'},
           {'4','.','.','8','.','3','.','.','1'},
           {'7','.','.','.','2','.','.','.','6'},
           {'.','6','.','.','.','.','2','8','.'},
           {'.','.','.','4','1','9','.','.','5'},
          {'.','.','.','.','8','.','.','7','9'}
        });
        
    }
    
    private static void test(char[][] board) {
        T0036_ValidSudoku sut = new T0036_ValidSudoku();
        System.out.println(Arrays.deepToString(board));
        System.out.println("--> " + sut.isValidSudoku(board));
    }

}
