package com.alphawang.designpattern.singleton;

public class LazySingleton {

    /**
     * ﻿双重检查加锁
     */
    public static LazySingleton getInstance() {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    instance = new LazySingleton();
                }
            }
            
        }
        return instance;
    }

    /**
     * 注意 volatile !!!
     * 
     * ﻿如果不用volatile，则因为内存模型允许所谓的“无序写入”，可能导致失败。——某个线程可能会获得一个未完全初始化的实例。
     * LazySingleton 构造函数体执行之前，变量instance 可能成为非 null 的！
     */
    private static volatile LazySingleton instance = null;

    private LazySingleton() {

    }
}
