package com.alphawang.concurrency.c3_mutex.atomic;

import com.alphawang.concurrency.common.Printer;
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
            log.info("#1 update success, {}", example.getCount());  //print 120
        }

        if (updater.compareAndSet(example, 100, 130)) {
            log.info("#2 update success, {}", example.getCount());
        } else {
            log.info("#2 update failed , {}", example.getCount()); //print 120
        }
        
        log.info("getAndIncrement 1: {}", updater.getAndIncrement(example));
        log.info("getAndIncrement 2: {}", updater.getAndIncrement(example));
    }
}
