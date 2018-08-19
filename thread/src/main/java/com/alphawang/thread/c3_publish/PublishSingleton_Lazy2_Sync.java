package com.alphawang.thread.c3_publish;

import com.alphawang.concurrency.annotations.NotRecommend;
import com.alphawang.concurrency.annotations.ThreadSafe;

/**
 * 懒汉模式
 * 单例实例在第一次使用时进行创建
 *
 */
@ThreadSafe
@NotRecommend
public class PublishSingleton_Lazy2_Sync {
    
    private PublishSingleton_Lazy2_Sync() {
        // do something
    }
    
    private static PublishSingleton_Lazy2_Sync instance = null;

    /**
     * 解决方法1：加锁
     * 问题：性能开销过大
     */
    public static synchronized PublishSingleton_Lazy2_Sync getInstance() {
        if (instance == null) {
            instance = new PublishSingleton_Lazy2_Sync();
        }
        return instance;
    }
    
}
