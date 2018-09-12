package com.alphawang.designpattern.singleton;

public class HungrySingleton {

    /**
     * 饿汉式单例
     * 在类加载的时候就立即初始化，并且创建单例对象
     *
     * 优点：没有加任何的锁、执行效率比较高
     *
     * 缺点：类加载的时候就初始化，浪费了内存，
     *
     * 绝对线程安全，在线程还没出现以前就是实例化了，不可能存在访问安全问题
     */
    public static HungrySingleton getInstance() {
        return INSTANCE;
    }
    
    private static final HungrySingleton INSTANCE = new HungrySingleton();
    
    private HungrySingleton() {
        
    }
}
