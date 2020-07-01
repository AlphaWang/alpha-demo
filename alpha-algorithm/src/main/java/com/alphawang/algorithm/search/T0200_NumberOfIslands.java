package com.alphawang.algorithm.search;

/**
 * https://leetcode.com/problems/number-of-islands/
 * Medium
 */
public class T0200_NumberOfIslands {

    int[] dx = new int[] {0, 1, 0, -1};
    int[] dy = new int[] {-1,0, 1, 0 };

    /**
     * 1. 遍历矩阵，碰到1 --> res++，并且 DFS 遍历周边节点，置为0
     *    1ms - 99.9%
     */
    public int numIslands(char[][] grid) {
        int res = 0;
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                if (grid[x][y] == '1') {
                    res++;
                    mark(grid, x, y);
                }
            }
        }
        
        return res;
    }
    
    private void mark(char[][] grid, int x, int y) {
        if (grid[x][y] == '0') {
            return;
        }
        grid[x][y] = '0';
        for (int i = 0; i < 4; i++) {
            int nextX = x + dx[i];
            int nextY = y + dy[i];
            if (nextX >= 0 && nextX < grid.length 
              && nextY >= 0 && nextY < grid[0].length) {
                mark(grid, nextX, nextY);
            }
        }
    }

    public static void main(String[] args) {
        T0200_NumberOfIslands sut = new T0200_NumberOfIslands();
        /*
         * Input:
         * 11110
         * 11010
         * 11000
         * 00000
         *
         * Output: 1
         */
        System.out.println(sut.numIslands(new char[][]{
          {'1','1','1','1','0'},
          {'1','1','0','1','0'},
          {'1','1','0','0','0'},
          {'0','0','0','0','0'}
        }));
        
        /*
         * Input:
         * 11000
         * 11000
         * 00100
         * 00011
         *
         * Output: 3
         */
        System.out.println(sut.numIslands(new char[][]{
          {'1','1','0','0','0'},
          {'1','1','0','0','0'},
          {'0','0','1','0','0'},
          {'0','0','0','1','1'}
        }));

        /**
         * [['1','1','1','1','0'],
         * ['1','1','0','1','0'],
         * ['1','1','0','0','0'],
         * ['0','0','0','0','0']]
         * 
         * 1
         */
        System.out.println(sut.numIslands(new char[][]{
          {'1','1','1','1','0'},
          {'1','1','0','1','0'},
          {'1','1','0','0','0'},
          {'0','0','0','0','0'}
        }));
    }


}
