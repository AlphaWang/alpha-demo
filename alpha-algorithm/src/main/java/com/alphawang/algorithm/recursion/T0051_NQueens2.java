package com.alphawang.algorithm.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class T0051_NQueens2 {
    
    Set<Integer> cols = new HashSet<>();
    Set<Integer> pie = new HashSet<>();
    Set<Integer> na = new HashSet<>();

    /**
     * Backtracking
     */
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<>();
        
        dfs(0, n, new int[n], res);
        
        return res;
    }

    private void dfs(int row, int n, int[] queensCols, List<List<String>> res) {
        if (row == n) {
            res.add(format(queensCols));
            return;
        }
        
        for (int col = 0; col < n; col++) {
            //[row, col] 放入 Queue
            if (isValidPos(row, col)) {
                cols.add(col);
                pie.add(row + col);
                na.add(row - col);
                
                queensCols[row] = col;
                dfs(row + 1, n, queensCols, res);
                
                queensCols[row] = -1;
                na.remove(row - col);
                pie.remove(row + col);
                cols.remove(col);
            }
        }
    }

    private boolean isValidPos(int row, int col) {
        return !cols.contains(col)
          && !pie.contains(row + col)
          && !na.contains(row - col);
    }

    private List<String> format(int[] queensCols) {
        List<String> res = new ArrayList<>();
        for (int col : queensCols) {
            char[] pos = new char[queensCols.length];
            Arrays.fill(pos, '.');
            if (col >= 0) {
                pos[col] = 'Q';
            } else {
                System.err.println("Invalid queensCols " + Arrays.toString(queensCols));
            }
            res.add(String.copyValueOf(pos));
        }
        
        return res;
    }
    

    public static void main(String[] args) {
        T0051_NQueens2 sut = new T0051_NQueens2();

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
