package com.alphawang.concurrency.common.c3_publish;

import com.alphawang.concurrency.common.annotations.ThreadSafe;

/**
 * 饿汉模式
 * 单例实例在第一次使用时进行创建
 *
 */ 
@ThreadSafe
public class PublishSingleton_Starve_StaticBlock {
    
    private PublishSingleton_Starve_StaticBlock() {
        // do something
    }

    /**
     * 一定要注意顺序！
     * 
     * 如果这一行在 static 块之后，则main中取到的instance == null!!
     */
    private static PublishSingleton_Starve_StaticBlock instance = null;
    
    static {
        instance = new PublishSingleton_Starve_StaticBlock();
    }

    
    
    public static PublishSingleton_Starve_StaticBlock getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        System.out.println(getInstance().hashCode());
        System.out.println(getInstance().hashCode());
    }
    
}
