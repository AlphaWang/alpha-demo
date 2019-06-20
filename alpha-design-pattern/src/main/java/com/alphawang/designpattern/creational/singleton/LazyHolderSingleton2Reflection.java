package com.alphawang.designpattern.creational.singleton;

public class LazyHolderSingleton2Reflection {

    public static LazyHolderSingleton2Reflection getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final LazyHolderSingleton2Reflection INSTANCE = new LazyHolderSingleton2Reflection();
    }

    /**
     * 保护不受反射影响
     */
    private static boolean initialized = false;

    private LazyHolderSingleton2Reflection() {
        if (!initialized) {
            initialized = true;
        } else {
            throw new RuntimeException("Cannot initialize twice!!!");
        }
    }
}
