package com.alphawang.concurrency.c3_mutex.atomic;

import com.alphawang.concurrency.common.Printer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceTest {

    private static AtomicReference<Integer> atomicReference = new AtomicReference<>(0);
    private static AtomicReference<Range> rangeRef = new AtomicReference<>(new Range(0, 0));

    public static void main(String[] args) {
        atomicReference.compareAndSet(0, 2); // 2
        atomicReference.compareAndSet(0, 1); // no
        atomicReference.compareAndSet(1, 3); // no
        atomicReference.compareAndSet(2, 4); // 4
        atomicReference.compareAndSet(3, 5); // no
        // [main] - count: 4
        Printer.print("count: " + atomicReference.get());
        
        // [main] - Range(start=0, end=10)
        setEnd(10);
        Printer.print(rangeRef.get());
        // IllegalArgumentException 
        setEnd(-1);
        Printer.print(rangeRef.get());
    }
    
    public static void setEnd(int newEnd) {
        while (true) {
            Range oldRange = rangeRef.get();
            if (newEnd < oldRange.getStart()) {
                throw new IllegalArgumentException("end is smaller than start");
            }
            
            Range newRange = new Range(oldRange.getStart(), newEnd);
            if (rangeRef.compareAndSet(oldRange, newRange)) {
                return;
            }
        }
    }
    
    
    
}

@Data
@AllArgsConstructor
class Range {
    private int start;
    private int end;
}
