package com.alphawang.algorithm.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.com/problems/n-queens/
 * Hard
 * 
 * @deprecated 
 * see T0051_NQueens2
 */
public class T0051_NQueens {

    int[] rows; // 标记是否被row上的皇后攻击
    int[] hills; // 标记是否被主对角线上的皇后攻击
    int[] dales; // 标记是否被次对角线上的皇后攻击
    int[] queens; // 皇后位置
    
    int n;

    List<List<String>> res = new ArrayList<>();
    
    public List<List<String>> solveNQueens(int n) {
        rows = new int[n];
        hills = new int[4 * n - 1]; //主对角线：row + col = cons
        dales = new int[2 * n - 1]; //次对角线：row - col = cons
        queens = new int[n];
        this.n = n;
        
        backtrack(0);
        
        return res;
    }
    
    private void backtrack(int row) {
        if (row >= n) {
            return;
        }
        
        for (int col = 0; col < n; col++) {
            if (isNotUnderAttack(row, col)) {
                placeQueue(row, col);
                if (row + 1 == n) {
                    addSolution();
                } else {
                    backtrack(row + 1);
                }
                removeQueen(row, col);
            }
        }
    }

    private void removeQueen(int row, int col) {
        queens[row] = 0;
        rows[col] = 0;
        hills[row - col + 2 * n] = 0;
        dales[row + col] = 0;
    }

    private void addSolution() {
        List<String> solution = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int col = queens[i];
            solution.add(getQueueString(col));
        }
        
        res.add(solution);
    }
    
    private String getQueueString(int queueCol) {
        char[] str = new char[n];
        Arrays.fill(str, '.');
        str[queueCol] = 'Q';
        return String.copyValueOf(str);
    }

    private void placeQueue(int row, int col) {
        queens[row] = col;
        rows[col] = 1;
        hills[row - col + 2 * n] = 1;
        dales[row + col] = 1;

        System.out.println(String.format("Place Queen for (%s, %s): %s\n  rows = %s \n  hills = %s \n  dales=%s",
                                         row, col, Arrays.toString(queens),
                                         Arrays.toString(rows),
                                         Arrays.toString(hills),
                                         Arrays.toString(dales)));
    }

    private boolean isNotUnderAttack(int row, int col) {
        return rows[col] 
          + hills[row - col + 2 * n] //TODO 
          + dales[row + col]         //TODO
          == 0;
    }

    public static void main(String[] args) {
        T0051_NQueens sut = new T0051_NQueens();

        /*
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
        System.out.println(sut.solveNQueens(4));
    }
    
}
