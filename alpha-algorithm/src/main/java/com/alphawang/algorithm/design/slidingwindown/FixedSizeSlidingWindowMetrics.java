package com.alphawang.algorithm.design.slidingwindown;

import com.alphawang.algorithm.design.slidingwindown.meta.Measurement;
import com.alphawang.algorithm.design.slidingwindown.meta.Metrics;
import com.alphawang.algorithm.design.slidingwindown.meta.Snapshot;
import com.alphawang.algorithm.design.slidingwindown.meta.SnapshotImpl;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class FixedSizeSlidingWindowMetrics implements Metrics {
    
    int windowSize;
    private Measurement totalAggregation;
    private Measurement[] measurements;
    // TODO
    int headIndex;
    
    public FixedSizeSlidingWindowMetrics(int windowSize) {
        this.windowSize = windowSize;
        this.headIndex = 0;
        this.totalAggregation = new Measurement();
        
        this.measurements = new Measurement[windowSize];
        for (int i = 0; i < windowSize; i++) {
            measurements[i] = new Measurement();
        }
    }
    
    @Override
    public Snapshot record(long duration, TimeUnit durationUnit, Outcome outcome) {
        this.totalAggregation.record(duration, durationUnit, outcome);
        Measurement latestMeasurement = moveWindowByOne();
        latestMeasurement.record(duration, durationUnit, outcome);
        
        printStatus();
        return getSnapshot();
    }

    @Override
    public Snapshot getSnapshot() {
        return new SnapshotImpl(totalAggregation);
    }
    
    private Measurement moveWindowByOne() {
        int newHeadIndex = moveHeadIndexByOne();
        Measurement latestMeasurement = measurements[newHeadIndex]; 
        latestMeasurement.reset();
        
        return latestMeasurement; 
    }

    /**
     * headIndex 是指环形数组中的当前指针
     * 此方法将 headIndex 往前移动
     */
    int moveHeadIndexByOne() {
        this.headIndex = (headIndex + 1) % windowSize;
        return headIndex;
    }
    
    public void printStatus() {
        String measureStr = Arrays.stream(measurements).map(Measurement::toString)
          .collect(Collectors.joining("\n"));
        System.out.println(String.format("headIndex=%s, measures=\n%s", headIndex, measureStr)); 
    }

    public static void main(String[] args) {
        testHeadIndex();

        testRecord();
        
    }
    
    private static void testHeadIndex() {
        FixedSizeSlidingWindowMetrics slidingWindow = new FixedSizeSlidingWindowMetrics(10);
        for(int i = 0; i < 15; i++) {
            slidingWindow.moveHeadIndexByOne();
            System.out.println(String.format("headIndex=%s", slidingWindow.headIndex));
        }
    }
    
    private static void testRecord() {
        FixedSizeSlidingWindowMetrics slidingWindow = new FixedSizeSlidingWindowMetrics(4);
        // test aggregation
        slidingWindow.record(1, TimeUnit.SECONDS, Outcome.ERROR).report();
        slidingWindow.record(99, TimeUnit.MILLISECONDS, Outcome.SUCCESS).report();
        slidingWindow.record(120, TimeUnit.MILLISECONDS, Outcome.SLOW_SUCCESS).report();
        slidingWindow.record(55, TimeUnit.MILLISECONDS, Outcome.SLOW_ERROR).report();
        slidingWindow.record(60, TimeUnit.MILLISECONDS, Outcome.SUCCESS).report(); 
        slidingWindow.record(102, TimeUnit.MILLISECONDS, Outcome.ERROR).report(); 
    }
}
