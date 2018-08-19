package com.alphawang.concurrency.c3_publish;

import com.alphawang.concurrency.annotations.ThreadSafe;

/**
 * 懒汉模式
 * 单例实例在第一次使用时进行创建
 *
 */
@ThreadSafe
public class PublishSingleton_Lazy3_DoubleCheckVolatile {
    
    private PublishSingleton_Lazy3_DoubleCheckVolatile() {
        // do something
    }

    /**
     * 解决方法3：双重检查 + volatile
     * 
     * volatile 可以禁止指令重排!!!
     */
    private static volatile PublishSingleton_Lazy3_DoubleCheckVolatile instance = null;

    
    public static PublishSingleton_Lazy3_DoubleCheckVolatile getInstance() {
        if (instance == null) {  // A
            synchronized (PublishSingleton_Lazy3_DoubleCheckVolatile.class) {
                if (instance == null) {   //B
                    instance = new PublishSingleton_Lazy3_DoubleCheckVolatile();
                }
            }
        }
        return instance;
    }
    
}
