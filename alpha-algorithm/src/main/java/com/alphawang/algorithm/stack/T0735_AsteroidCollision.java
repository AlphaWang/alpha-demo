package com.alphawang.algorithm.stack;

import static com.alphawang.algorithm.Utils.print;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

/**
 * https://leetcode.com/problems/asteroid-collision/
 * 给定一个整数数组 asteroids，表示在同一行的行星。 对于数组中的每一个元素，其绝对值表示行星的大小，正负表示行星的移动方向（正表示向右移动，负表示向左移动）。每一颗行星以相同的速度移动。
 * 找出碰撞后剩下的所有行星。
 *
 * 碰撞规则：两个行星相互碰撞，较小的行星会爆炸。如果两颗行星大小相同，则两颗行星都会爆炸。两颗移动方向相同的行星，永远不会发生碰撞。
 */
public class T0735_AsteroidCollision {

  /**
   * 更直观
   * overall, there are totally 4 scenarios will happen: 1.+ + 2.- - 3.+ - 4.- +
   * when collision happens: only 3 which is + -
   * use a stack to keep track of the previous and compare current value with previous ones
   */
  public int[] asteroidCollision(int[] a) {
    Deque<Integer> stack = new ArrayDeque<>();
    for (int item : a) {
      if (item > 0) {
        // 1.++ 4.-+: push
        stack.push(item);
      } else {
        // 3.+-: 如果当前值大，则 peek collides.
        // destroy previous positive ones
        while (!stack.isEmpty() && stack.peek() > 0 && stack.peek() < -item) {
          stack.pop();
        }

        if (stack.isEmpty() || stack.peek() < 0) {
          // 2.--: push
          stack.push(item);
        } else if (stack.peek() == -item) {
          // 3.+-: 如果值相同，则 both collides.
          stack.pop();
        }
      }
    }

    int[] res = new int[stack.size()];
    for (int i = stack.size() - 1; i >= 0; i--) {
      res[i] = stack.pop();
    }
    return res;
  }

  // bug: don't need to' handle [-8, 8]
  public int[] asteroidCollision1(int[] a) {
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

  public int[] asteroidCollision2(int[] arr) {
    Deque<Integer> stack = new ArrayDeque<>();
    for (int a : arr) {
      if (a > 0) {
        stack.push(a);
      } else {
        // 对于负数，回头看之前的行星：如果质量小，则撞飞
        while (!stack.isEmpty() && stack.peek() > 0 && stack.peek() < -a) {
          stack.pop();
        }
        if (!stack.isEmpty() && stack.peek() == -a) {
          stack.pop(); // 如果之前有行星，且质量相等，则撞飞 + 当前行星不放入
        } else if (stack.isEmpty() || stack.peek() < 0) {
          stack.push(a); // 如果之前无行星，或之前行星为负数，则放入当前行星
        }
      }
    }

    int[] res = new int[stack.size()];
    for (int i = res.length - 1; i >=0; i--) {
      res[i] = stack.pop();
    }
    return res;
  }

  public int[] asteroidCollision3(int[] asteroids) {
    Stack<Integer> stack = new Stack();
    for (int ast: asteroids) {
      collision: {
        while (!stack.isEmpty() && ast < 0 && 0 < stack.peek()) {
          if (stack.peek() < -ast) {
            stack.pop();
            continue;
          } else if (stack.peek() == -ast) {
            stack.pop();
          }
          break collision;
        }
        stack.push(ast);
      }
    }

    int[] ans = new int[stack.size()];
    for (int t = ans.length - 1; t >= 0; --t) {
      ans[t] = stack.pop();
    }
    return ans;
  }

  public int[] asteroidCollision4(int[] a) {
    LinkedList<Integer> s = new LinkedList<>();
    for (int i = 0; i < a.length; i++) {
      if (a[i] > 0 || s.isEmpty() || s.getLast() < 0) {
        s.add(a[i]);
      } else if (s.getLast() <= -a[i]) {
        if (s.pollLast() < -a[i])
          i--;
      }
    }
    return s.stream().mapToInt(i->i).toArray();
  }

  public static void main(String[] args) {
    T0735_AsteroidCollision sut = new T0735_AsteroidCollision();
    sut.test(new int[]{10, 2, -5}); //[10]
    sut.test(new int[]{5, 10, -5}); //[5, 10]
    sut.test(new int[]{-8, 8}); //[] //应该返回 [-8, 8] !!!负数表示往左！
    sut.test(new int[]{8, -8}); //[]
  }

  private void test(int[] a) {
//    print(asteroidCollision1(a));
    print(asteroidCollision2(a));
//    print(asteroidCollision(a));
//    print(asteroidCollision4(a));
  }
}
