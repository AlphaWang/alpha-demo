package com.alphawang.concurrency.c1_atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class AtomicReferenceTest {
    
    private static AtomicReference<Integer> atomicReference = new AtomicReference<>(0);

    public static void main(String[] args) {
        atomicReference.compareAndSet(0, 2); // 2
        atomicReference.compareAndSet(0, 1); // no
        atomicReference.compareAndSet(1, 3); // no
        atomicReference.compareAndSet(2, 4); // 4
        atomicReference.compareAndSet(3, 5); // no
        log.info("count:{}", atomicReference.get());  
    }
}
