package com.alphawang.designpattern.creational.singleton;

import java.io.Serializable;

public class LazyHolderSingleton3Serialize implements Serializable {

    public static LazyHolderSingleton3Serialize getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final LazyHolderSingleton3Serialize INSTANCE = new LazyHolderSingleton3Serialize();
    }

    /**
     * 保护不受反射影响
     */
    private static boolean initialized = false;
    private LazyHolderSingleton3Serialize() {
        if (!initialized) {
            initialized = true;
        } else {
            throw new RuntimeException("Cannot initialize twice!!!");
        }
    }

    /**
     * 保护不受反序列化影响
     */
    public Object readResolve() {
        return LazyHolder.INSTANCE;
    }
    
}
