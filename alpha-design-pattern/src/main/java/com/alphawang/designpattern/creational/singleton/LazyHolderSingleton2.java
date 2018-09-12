package com.alphawang.designpattern.creational.singleton;

public class LazyHolderSingleton2 {

    public static LazyHolderSingleton2 getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final LazyHolderSingleton2 INSTANCE = new LazyHolderSingleton2();
    }

    /**
     * 保护不受反射影响
     */
    private static boolean initialized = false;
    private LazyHolderSingleton2() {
        if (!initialized) {
            initialized = true;
        } else {
            throw new RuntimeException("Cannot initialize twice!!!");
        }
    }
}
