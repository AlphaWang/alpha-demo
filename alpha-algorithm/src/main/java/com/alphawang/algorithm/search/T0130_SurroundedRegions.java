package com.alphawang.algorithm.search;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/surrounded-regions/
 * Medium
 */
public class T0130_SurroundedRegions {

    /**
     * 1. DFS
     *    从四条边开始，标记 O
     *    
     *    1ms - 99%
     */
    private int[][] directions = new int[][] {
      {-1, 0}, {1, 0}, {0, -1}, {0, 1}
    };
    
    public void solve(char[][] board) {
        if (board == null || board.length == 0) {
            return;
        }
        
        int m = board.length;
        int n = board[0].length;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (isEdge(i, j, m, n) && board[i][j] == 'O') {
                    dfs(board, i, j, m, n);
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
                if (board[i][j] == '#') {
                    board[i][j] = 'O';
                }
            }
        }

    }

    private void dfs(char[][] board, int i, int j, int m, int n) {
        if (i < 0 || i >= m || j < 0 || j >= n) {
            return;
        }
        
        if (board[i][j] == '#' || board[i][j] == 'X') {
            return;
        }
        if (board[i][j] == 'O') {
            board[i][j] = '#';
        }
        
        for (int[] direction : directions) {
            dfs(board, i + direction[0], j + direction[1], m, n);
        }
    }

    private boolean isEdge(int i, int j, int m, int n) {
        return i == 0 || i == m - 1 || j == 0 || j == n - 1;
    }

    public static void main(String[] args) {
        test(new char[][] {
          {'X', 'X', 'X', 'X'},
          {'X', 'O', 'O', 'X'},
          {'X', 'X', 'O', 'X'},
          {'X', 'O', 'X', 'X'}
        });
    }
    
    private static void test(char[][] board) {
        T0130_SurroundedRegions sut = new T0130_SurroundedRegions();
        System.out.println(Arrays.deepToString(board));
        sut.solve(board);
        System.out.println(Arrays.deepToString(board));
    }

}
