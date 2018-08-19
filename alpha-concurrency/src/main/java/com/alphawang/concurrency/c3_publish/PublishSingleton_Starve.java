package com.alphawang.concurrency.c3_publish;

import com.alphawang.concurrency.annotations.ThreadSafe;

/**
 * 饿汉模式
 * 单例实例在第一次使用时进行创建
 *
 */ 
@ThreadSafe
public class PublishSingleton_Starve {
    
    private PublishSingleton_Starve() {
        // do something
    }
    
    private static PublishSingleton_Starve instance = new PublishSingleton_Starve();
    
    public static PublishSingleton_Starve getInstance() {
        return instance;
    }
    
}
