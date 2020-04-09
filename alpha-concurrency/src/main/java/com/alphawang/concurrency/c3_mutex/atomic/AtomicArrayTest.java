package com.alphawang.concurrency.c3_mutex.atomic;

import com.alphawang.concurrency.common.Printer;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicArrayTest {
    
    static int[] values = new int[] { 1, 2, 3 };
    static AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(values);

    public static void main(String[] args) {
        atomicIntegerArray.compareAndSet(0, 1, 10); // true
        atomicIntegerArray.compareAndSet(0, 1, 11); // false
        // [10, 2, 3]
        Printer.print(atomicIntegerArray);
        // [1, 2, 3]
        Printer.print(Arrays.toString(values));
    }

}
