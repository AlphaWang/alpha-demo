package com.alphawang.designpattern.creational.singleton;

public class LazyHolderSingleton {

    public static LazyHolderSingleton getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * ﻿定义一个内部类，这个内部类专门用于构造单例。
     * 这种形式兼顾饿汉式的内存浪费，也兼顾synchronized性能问题
     * 
     * 问题：如果通过反射构造，会得到两个不同的实例
     */
    private static class LazyHolder {
        private static final LazyHolderSingleton INSTANCE = new LazyHolderSingleton();
    }

    private LazyHolderSingleton() {

    }
}
