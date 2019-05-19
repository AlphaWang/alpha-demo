package com.alphawang.concurrency.mutex.atomic;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 不常用。
 */
@Slf4j
public class AtomicReferenceFieldUpdaterTest {

    /**
     * 必须是volatile
     * 必须不是static
     */
    @Getter
    public volatile int count = 100; 
    
    // 原子性地更新某个类的某个实例变量
    private static AtomicIntegerFieldUpdater<AtomicReferenceFieldUpdaterTest> updater = 
        AtomicIntegerFieldUpdater.newUpdater(AtomicReferenceFieldUpdaterTest.class, "count");

    public static void main(String[] args) {
        AtomicReferenceFieldUpdaterTest example = new AtomicReferenceFieldUpdaterTest();
        
        if (updater.compareAndSet(example, 100, 120)) {
            log.info("update success 1#, {}", example.getCount());  //print 120
        }

        if (updater.compareAndSet(example, 100, 120)) {
            log.info("update success 2#, {}", example.getCount());
        } else {
            log.info("update failed, {}", example.getCount()); //print 120
        }
    }
}
