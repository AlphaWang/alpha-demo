package com.alphawang.algorithm.search;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/minesweeper/
 * Medium
 * 
 * 'M' represents an unrevealed mine, 
 * 'E' represents an unrevealed empty square, 
 * 'B' represents a revealed blank square that has no adjacent (above, below, left, right, and all 4 diagonals) mines, 
 * digit ('1' to '8') represents how many mines are adjacent to this revealed square, 
 * 'X' represents a revealed mine.
 * 
 */
public class T0529_MineSweeper {

    private static final int[][] directions = new int[][] {
      { 0, -1 }, // up
      { 1, 0 },  // right
      { 0, 1 },  // down
      { -1, 0 }, // left
      { 1, -1 }, // up-right
      { 1, 1 },  // down-right
      { -1, 1},  // down-left
      { -1, -1}  // up-left
    };

    /**
     * 1. DFS: 
     *    - 遇到 M，标记，结束
     *    - 遇到 X，结束
     *    - 否则是 E，
     *      - 如果周边有雷，更新当前位置
     *      - 如果周边无雷，DFS 向四周扩散 
     *      
     *   1ms - 53%   
     */
    public char[][] updateBoard(char[][] board, int[] click) {
        int l = board.length;
        int w = board[0].length;
        int x = click[0];
        int y = click[1];
        
        dfs(board, x, y, l, w);
        
        return board;
 
    }
    
    private void dfs(char[][] board, int x, int y, int l, int w) {
//        System.out.println(String.format("-- process [%s, %s] = \n%s", x, y, Arrays.deepToString(board)));
        // 遇到 M，结束
        if (board[x][y] == 'M') {
            board[x][y] = 'X';
            return;
        }
        // 遇到 X，也结束
        if (board[x][y] == 'X') {
            return;
        }

        // 遇到E, 计算周边的 M 数目
        int num = 0;
        for (int[] dir : directions) {
            int newX = dir[0] + x;
            int newY = dir[1] + y;
            if (newX >= 0 && newX < l && newY >= 0 && newY < w 
              && (board[newX][newY] == 'M' || board[newX][newY] == 'X')) {
                num++;
            }
        }

        // E: 周边有雷
        if (num > 0) {
            board[x][y] = (char) (num + '0');
            return; //TODO !!!
        }

        // E: 周边无雷 - DFS
        board[x][y] = 'B';
        for (int[] dir : directions) {
            int newX = dir[0] + x;
            int newY = dir[1] + y;
            if (newX >= 0 && newX < l && newY >= 0 && newY < w 
              && board[newX][newY] == 'E') {
                updateBoard(board, new int[] {newX, newY});
            }
        }

        return;
    } 
    

    public static void main(String[] args) {
        T0529_MineSweeper sut = new T0529_MineSweeper();
        /*
         * Input: 
         *
         * [['E', 'E', 'E', 'E', 'E'],
         *  ['E', 'E', 'M', 'E', 'E'],
         *  ['E', 'E', 'E', 'E', 'E'],
         *  ['E', 'E', 'E', 'E', 'E']]
         *
         * Click : [3,0]
         *
         * Output: 
         *
         * [['B', '1', 'E', '1', 'B'],
         *  ['B', '1', 'M', '1', 'B'],
         *  ['B', '1', '1', '1', 'B'],
         *  ['B', 'B', 'B', 'B', 'B']]
         */
        System.out.println(Arrays.deepToString(sut.updateBoard(new char[][]{
              {'E', 'E', 'E', 'E', 'E'},
              {'E', 'E', 'M', 'E', 'E'},
              {'E', 'E', 'E', 'E', 'E'},
              {'E', 'E', 'E', 'E', 'E'}
            }, new int[] {3, 0})));

        /*
         * Input: 
         *
         * [['B', '1', 'E', '1', 'B'],
         *  ['B', '1', 'M', '1', 'B'],
         *  ['B', '1', '1', '1', 'B'],
         *  ['B', 'B', 'B', 'B', 'B']]
         *
         * Click : [1,2]
         *
         * Output: 
         *
         * [['B', '1', 'E', '1', 'B'],
         *  ['B', '1', 'X', '1', 'B'],
         *  ['B', '1', '1', '1', 'B'],
         *  ['B', 'B', 'B', 'B', 'B']]
         */
        System.out.println(Arrays.deepToString(sut.updateBoard(new char[][]{
                {'B', '1', 'E', '1', 'B'},
                {'B', '1', 'M', '1', 'B'},
                {'B', '1', '1', '1', 'B'},
                {'B', 'B', 'B', 'B', 'B'}
            }, new int[] {1, 2})));
    }
    
    

}
