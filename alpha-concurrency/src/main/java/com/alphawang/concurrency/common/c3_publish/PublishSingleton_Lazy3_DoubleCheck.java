package com.alphawang.concurrency.common.c3_publish;

import com.alphawang.concurrency.common.annotations.NotThreadSafe;

/**
 * 懒汉模式
 * 单例实例在第一次使用时进行创建
 *
 */
@NotThreadSafe
public class PublishSingleton_Lazy3_DoubleCheck {

    private PublishSingleton_Lazy3_DoubleCheck() {
        // do something
    }

    private static PublishSingleton_Lazy3_DoubleCheck instance = null;

    /**
     * 解决方法2：双重检查; 双重同步锁单例模式
     * 问题：指令重排，非线程安全！！
     *
     *
     *
     *      1、memory = allocate() 分配对象的内存空间
     *      2、ctorInstance() 初始化对象
     *      3、instance = memory 设置instance指向刚分配的内存
     *
     *      JVM和cpu优化，发生了指令重排
     *
     *      1、memory = allocate() 分配对象的内存空间
     *      3、instance = memory 设置instance指向刚分配的内存
     *      2、ctorInstance() 初始化对象
     *
     *      所以，A 处可能取到不为null的实例，但其实并没有初始化完成。
     *
     *
     */
    public static PublishSingleton_Lazy3_DoubleCheck getInstance() {
        if (instance == null) {  // A
            synchronized (PublishSingleton_Lazy3_DoubleCheck.class) {

                /**
                 * Q: 这里为什么要判空？
                 * A: 当 instance 为 null 时，两个线程可以并发地进入if 语句内部。
                 *    然后，一个线程进入 synchronized 块来初始化 instance，而另一个线程则被阻断。
                 *    当第一个线程退出 synchronized 块时，等待着的线程进入并创建另一个Singleton 对象。
                 *
                 *    注意：当第二个线程进入 synchronized 块时，它并没有检查 instance 是否非 null。
                 *
                 *    还是会创建2个对象！！！！
                 */
                if (instance == null) {   //B
                    instance = new PublishSingleton_Lazy3_DoubleCheck();
                }
            }
        }
        return instance;
    }

}
