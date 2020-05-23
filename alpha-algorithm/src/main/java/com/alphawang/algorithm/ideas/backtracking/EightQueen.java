package com.alphawang.algorithm.ideas.backtracking;

public class EightQueen {
    /**
     * 有一个 8x8 的棋盘，希望往里放 8 个棋子（皇后），
     * 每个棋子所在的行、列、对角线都不能有另一个棋子。
     * 
     * 期望找到所有满足这种要求的放棋子方式。
     */
    int[] result = new int[8]; // 下标：行数；值：queen
    
    public void cal(int row) {
        if (row == 8) {
            printQueen(result);
            return;
        }
        
        // 依次尝试每一列
        for (int col = 0; col < 8; col++) {
            if (validate(row, col)) {
                // 如果校验通过，则当前列放入Q 
                result[row] = col;
                // 计算下一行
                cal(row + 1);
            }
        }
    }

    private boolean validate(int row, int col) {
        int leftUp = col - 1;
        int rightUp = col + 1;
        
        for (int prevRow = row - 1; prevRow >= 0; prevRow--) {
            int queueCol = result[prevRow];
            // 上一行：检查正上方
            if (queueCol == col) {
                return false;
            }
            // 上一行：检查左上方
            if (leftUp >= 0 && queueCol == leftUp) {
                return false;
            }
            // 上一行：检查右上方
            if (rightUp < 8 && queueCol == rightUp) {
                return false;
            }
            
            leftUp--;
            rightUp++;
        }
        return true;
    }

    private static void printQueen(int[] result) {
         for (int row = 0; row < result.length; row++) {
             for (int col = 0; col < 8; col++) {
                 int queueCol = result[row];
                 System.out.print(queueCol == col ? "Q  " : "*  ");
             }
             System.out.println();
         }
        System.out.println();
    }

    public static void main(String[] args) {
        EightQueen eightQueen = new EightQueen();
        eightQueen.cal(0);
    }

}
