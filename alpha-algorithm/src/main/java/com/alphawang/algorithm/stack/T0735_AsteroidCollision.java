package com.alphawang.algorithm.stack;

import static com.alphawang.algorithm.Utils.print;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * https://leetcode.com/problems/asteroid-collision/
 * 给定一个整数数组 asteroids，表示在同一行的行星。 对于数组中的每一个元素，其绝对值表示行星的大小，正负表示行星的移动方向（正表示向右移动，负表示向左移动）。每一颗行星以相同的速度移动。
 * 找出碰撞后剩下的所有行星。
 *
 * 碰撞规则：两个行星相互碰撞，较小的行星会爆炸。如果两颗行星大小相同，则两颗行星都会爆炸。两颗移动方向相同的行星，永远不会发生碰撞。
 */
public class T0735_AsteroidCollision {

  public int[] asteroidCollision(int[] a) {
    int n = a.length;
    Deque<Integer> stack = new ArrayDeque<>();
    stack.push(a[0]);

    for (int i = 1; i < n; i++) {
      Integer item = a[i];
      while (!stack.isEmpty() && item * stack.peek() < 0) {
        if (Math.abs(item) < Math.abs(stack.peek())) {
          // a[i] collides, do nothing
          break;
        } else if (Math.abs(item) > Math.abs(stack.peek())) {
          // stack peek collides
          stack.pop();
        } else {
          // both collides
          item = null;
          stack.pop();
          break;
        }
      }

      if (item != null && item * stack.peek() > 0) {
        stack.push(a[i]);
      }
    }

    int[] res = new int[stack.size()];
    for (int i = stack.size() - 1; i >= 0; i--) {
      res[i] = stack.pop();
    }
    return res;
  }

  public static void main(String[] args) {
    T0735_AsteroidCollision sut = new T0735_AsteroidCollision();
    print(sut.asteroidCollision(new int[]{10, 2, -5})); //[10]
    print(sut.asteroidCollision(new int[]{5, 10, -5})); //[5, 10]
    print(sut.asteroidCollision(new int[]{8, -8})); //[]
  }
}
