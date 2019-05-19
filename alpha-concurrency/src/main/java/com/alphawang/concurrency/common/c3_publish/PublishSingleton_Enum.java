package com.alphawang.concurrency.common.c3_publish;

import com.alphawang.concurrency.common.annotations.ThreadSafe;

/**
 *  枚举模式：最安全
 */
@ThreadSafe
public class PublishSingleton_Enum {
    
    private PublishSingleton_Enum() {
        // do something
    }

    private enum Singleton {
        INSTANCE;
        
        private PublishSingleton_Enum instance;

        // JVM保证这个构造方法绝对只调用一次
        Singleton() {
            instance = new PublishSingleton_Enum();
        }
        
        public PublishSingleton_Enum getInstance() {
            return instance;
        }
    }

    
    public static PublishSingleton_Enum getInstance() {
        return Singleton.INSTANCE.getInstance();
    }
    
}
