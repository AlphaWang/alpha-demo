package com.alphawang.jdk.jmm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StackTest {
    /**
     * ﻿Java栈描述的是Java方法执行的内存模型。
     * Java栈由栈帧组成，一个帧对应一个方法调用。调用方法时压入栈帧，方法返回时弹出栈帧并抛弃。
     * Java栈的主要任务是存储方法参数、局部变量、中间运算结果，并且提供部分其它模块工作需要的数据。
     *
     */

    /**
     *
     * ﻿如果线程请求的栈深度大于JVM所允许的深度，则抛出StackOverflowError。
     * 如果VM可以动态扩展，但是扩展是无法申请到足够的内存，则抛出OutOfMemoryError。
     * 可以通过减少-Xss，同时递归调用某个方法，模拟StackOverflowError
     *
     * -Xss1027
     *
     * Error: Could not create the Java Virtual Machine.
     * The stack size specified is too small, Specify at least 160k
     * Error: A fatal exception has occurred. Program will exit.
     */
    public static void main(String[] args) {
        //		testLoop();
        testRecursive(0);
    }

    private static void testRecursive(int i) {
        log.info("running...{}", i);
        testRecursive(++i);
    }

    private static void testLoop() {
        int i = 0;
        while (true) {
            log.info("running...{}", i++);
        }
    }

}
