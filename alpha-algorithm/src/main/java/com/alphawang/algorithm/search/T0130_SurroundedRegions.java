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

    /**
     * 2. 并查集
     * 
     *    靠近边界的 O, 都让其从属与一个 dummyNode
     *    其余的O，与相邻O组成集合
     *    
     *    24ms - 5%
     */
    public void solve2(char[][] board) {
        if (board == null || board.length == 0) {
            return;
        }

        int m = board.length;
        int n = board[0].length;
        
        JointSet jointSet = new JointSet(m * n + 1);
        int dummy = m * n;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') {
                    int current = encode(i, j, n);
                    if (isEdge(i, j, m, n)) {
                        jointSet.join(current, dummy);
                    } else {
                        if (i > 0 && board[i - 1][j] == 'O') {
                            jointSet.join(encode(i - 1, j, n), current);
                        }
                        if (i < m - 1 && board[i + 1][j] == 'O') {
                            jointSet.join(encode(i + 1, j, n), current);
                        }
                        if (j > 0 && board[i][j - 1] == 'O') {
                            jointSet.join(encode(i, j - 1, n), current);
                        }
                        if (j < n - 1 && board[i][j + 1] == 'O') {
                            jointSet.join(encode(i, j + 1, n), current);
                        }
                    }
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O' && jointSet.find(encode(i, j, n)) != jointSet.find(dummy)) {
                    board[i][j] = 'X';
                }
            }
        }
        
    }
    
    private int encode(int i, int j, int n) {
        return i * n + j;
    }


    private static class JointSet {
        int n;
        int[] parent;
        
        public JointSet(int n) {
            this.n = n;
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }
        
        public int find(int p) {
            while(p != parent[p]) {
                parent[p] = parent[parent[p]];
                p = parent[p];
            }
            
            return p;
        }
        
        public void join(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP != rootQ) {
                parent[rootP] = rootQ;
            }
        }
        
    }

    public static void main(String[] args) {
        test(new char[][] {
          {'X', 'X', 'X', 'X'},
          {'X', 'O', 'O', 'X'},
          {'X', 'X', 'O', 'X'},
          {'X', 'O', 'X', 'X'}
        });
        
        test(new char[][]{
          {'X','O','X','X'},
          {'O','X','O','X'},
          {'X','O','X','O'},
          {'O','X','O','X'},
          {'X','O','X','O'},
          {'O','X','O','X'}
        });
    }
    
    private static void test(char[][] board) {
        T0130_SurroundedRegions sut = new T0130_SurroundedRegions();
        System.out.println(Arrays.deepToString(board));
        sut.solve2(board);
        System.out.println(Arrays.deepToString(board));
    }

}
