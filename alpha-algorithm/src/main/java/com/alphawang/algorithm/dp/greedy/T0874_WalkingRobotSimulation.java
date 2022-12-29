package com.alphawang.algorithm.dp.greedy;

import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode.com/problems/walking-robot-simulation/
 * Easy
 * 
 * 机器人在一个无限大小的网格上行走，从点 (0, 0) 处开始出发，面向北方。该机器人可以接收以下三种类型的命令：
 *
 *     -2：向左转 90 度
 *     -1：向右转 90 度
 *     1 <= x <= 9：向前移动 x 个单位长度
 * 
 * 在网格上有一些格子被视为障碍物。
 * 第 i 个障碍物位于网格点  (obstacles[i][0], obstacles[i][1])
 * 机器人无法走到障碍物上，它将会停留在障碍物的前一个网格方块上，但仍然可以继续该路线的其余部分。
 * 返回从原点到机器人所有经过的路径点（坐标为整数）的最大欧式距离的平方。
 * 
 */
public class T0874_WalkingRobotSimulation {

    /**
     *  295ms - 8%
     */
    public int robotSim(int[] commands, int[][] obstacles) {
        int res = 0;
//        Set<String> obstacleSet = new HashSet<>();
//        for (int[] obstacle : obstacles) {
//            obstacleSet.add(encode(obstacle));
//        }

        Set<Long> obstacleSet = new HashSet<>();
        for (int[] obstacle : obstacles) {
            obstacleSet.add(encode2(obstacle[0], obstacle[1]));
        }
        
        // up, right, down, left
        int[] dx = new int[] {0, 1, 0, -1};
        int[] dy = new int[] {1, 0, -1, 0};
        
        int x = 0, y = 0;
        int di = 0;
        for (int command : commands) {
            // turn left
            if (command == -2) {
                di = (di + 3) % 4; //?
            } 
            // turn right
            else if (command == -1) {
                di = (di + 1) % 4; 
            }
            // go
            else {
                for (int i = 0; i < command; i++) {
                    int tmpX = x + dx[di];
                    int tmpY = y + dy[di];
                    if (!obstacleSet.contains(encode2(tmpX, tmpY))) {
                        x = tmpX;
                        y = tmpY;
                        res = Math.max(res, x * x + y * y);
                    }
                }
            }
        }
        

        return res;
    }
    
    private String encode(int[] pos) {
        return encode(pos[0], pos[1]);
    }

    private String encode(int x, int y) {
        return String.format("%s-%s", x, y);
    }

    /**
     * 优化 encode
     * 9ms - 98%
     */
    private Long encode2(int x, int y) {
        long ox = (long) x + 30000;
        long oy = (long) y + 30000;
        return (ox << 16) + oy;
    }
    

    public static void main(String[] args) {
        T0874_WalkingRobotSimulation sut = new T0874_WalkingRobotSimulation();
        /*
         * Input: commands = [4,-1,3], obstacles = []
         * Output: 25
         * Explanation: robot will go to (3, 4)
         */
        System.out.println(sut.robotSim(new int[]{4,-1,3}, new int[0][0]));

        /*
         * Input: commands = [4,-1,4,-2,4], obstacles = [[2,4]]
         * Output: 65
         * Explanation: robot will be stuck at (1, 4) before turning left and going to (1, 8)
         */
        System.out.println(sut.robotSim(new int[]{4,-1,4,-2,4}, new int[][] {{2, 4}}));
    }

}
