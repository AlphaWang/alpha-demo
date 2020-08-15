package com.alphawang.algorithm.array;

import java.util.Arrays;

/**
 * 小Q在进行射击气球的游戏，如果小Q在连续T枪中打爆了所有颜色的气球，将得到一只QQ公仔作为奖励。（每种颜色的球至少被打爆一只）。
 *
 * 这个游戏中有m种不同颜色的气球，编号1到m。
 * 小Q一共有n发子弹，然后连续开了n枪。
 * 小Q想知道在这n枪中，打爆所有颜色的气球最少用了连续几枪？
 *
 * 输入格式
 * 第一行包含两个整数n和m。
 * 第二行包含n个整数，分别表示每一枪打中的气球的颜色，0表示没打中任何颜色的气球。
 *
 * 输出格式
 * 一个整数表示小Q打爆所有颜色气球用的最少枪数。
 * 如果小Q无法在这n枪打爆所有颜色的气球，则输出-1。
 *
 * 数据范围
 * 1≤n≤1061≤n≤106,
 * 1≤m≤20001≤m≤2000
 *
 * 输入样例：
 * 12 5
 * 2 5 3 1 3 2 4 1 0 5 4 3
 * 输出样例：
 * 6
 * 
 * 样例解释
 * 有五种颜色的气球，编号1到5。
 * 游客从第二枪开始直到第七枪，这连续六枪打爆了5 3 1 3 2 4这几种颜色的气球，包含了从1到5的所有颜色，所以最少枪数为6。
 */
public class T_SlidingWindowBalloon {

    /**
     * 
     * @param ballons
     * @param n  子弹数目
     * @param m  气球颜色总数
     */
    public int min(int[] ballons, int n, int m) {
        int res = n;
        int colors = 0;
        int[] count = new int[m + 1];
        
        boolean found = false;
        for (int i = 0, j = 0; j < n; j++) {
            int color = ballons[j];
            if (color > 0 && count[color] == 0) { // 遇到未处理过的颜色
                colors++;
            }
            
            count[color]++;
            if (colors == m) { // 满足目标
                found = true;
                while (ballons[i] == 0 || count[ballons[i]] > 1) {
                    i++;
                    count[ballons[i]]--;
                }
                res = Math.min(res, j - i + 1);
            }
        }
        
        return found ? res : -1;
    }

    public static void main(String[] args) {
         test(new int[] {2, 5, 3, 1, 3, 2, 4, 1, 0, 5, 4, 3}, 12, 5);
        
    }

    public static void test(int[] ballons, int n, int m) {
        System.out.println(String.format("%s : %s, %s --> %s", 
           Arrays.toString(ballons), m, n, new T_SlidingWindowBalloon().min(ballons, n, m))); 
    }

}
