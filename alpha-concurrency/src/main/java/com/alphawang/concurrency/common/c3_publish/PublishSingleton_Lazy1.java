package com.alphawang.concurrency.common.c3_publish;

import com.alphawang.concurrency.common.annotations.NotThreadSafe;

/**
 * 懒汉模式
 * 单例实例在第一次使用时进行创建
 *
 */
@NotThreadSafe
public class PublishSingleton_Lazy1 {

    private PublishSingleton_Lazy1() {
        // do something
    }

    private static PublishSingleton_Lazy1 instance = null;

    public static PublishSingleton_Lazy1 getInstance() {
        if (instance == null) {
            /**
             * 问题：多线程访问时，可能创建两个不同的instance.
             */
            instance = new PublishSingleton_Lazy1();
        }
        return instance;
    }

}
