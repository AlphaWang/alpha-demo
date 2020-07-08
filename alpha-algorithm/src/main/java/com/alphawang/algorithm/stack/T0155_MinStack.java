package com.alphawang.algorithm.stack;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

/**
 * https://leetcode.com/problems/min-stack/
 * Easy
 * 
 * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
 *
 * push(x) —— 将元素 x 推入栈中。
 * pop() —— 删除栈顶的元素。
 * top() —— 获取栈顶元素。
 * getMin() —— 检索栈中的最小元素。
 *  
 */
public class T0155_MinStack {

    /**
     * 1. 两个栈；分别存储元素、min
     *    8ms - 29%
     */
    static class MinStack {
        
        Deque<Integer> stack = new LinkedList<>();
        Deque<Integer> min = new LinkedList<>();
        

        /** initialize your data structure here. */
        public MinStack() {

        }

        public void push(int x) {
             stack.push(x);
             
             if (min.isEmpty()) {
                 min.push(x);
             } else if (x <= min.peek()) {
                 min.push(x);
             } else {
                 min.push(min.peek());
             }
        }

        public void pop() {
            stack.pop();
            min.pop();
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return min.peek();
        }
    }


    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println(minStack.getMin());  // --> 返回 -3.
        minStack.pop();
        System.out.println(minStack.top());     // --> 返回 0.
        System.out.println(minStack.getMin());  // --> 返回 -2.
    }
}
