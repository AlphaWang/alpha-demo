package com.alphawang.algorithm.design.slidingwindow;

import com.alphawang.algorithm.design.slidingwindow.meta.Measurement;
import com.alphawang.algorithm.design.slidingwindow.meta.Metrics;
import com.alphawang.algorithm.design.slidingwindow.meta.Snapshot;
import com.alphawang.algorithm.design.slidingwindow.meta.SnapshotImpl;
import com.alphawang.algorithm.design.slidingwindow.meta.TimeMeasurement;
import java.time.Clock;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * The sliding window does not store call outcomes (tuples) individually, but incrementally updates partial aggregations (bucket) and a total aggregation.
 * 
 * 复杂度：
 * The time to retrieve a Snapshot is constant O(1), since the Snapshot is pre-aggregated and is independent of the time window size.
 * The space requirement (memory consumption) of this implementation should be nearly constant O(n), since the call outcomes (tuples) are not stored individually. Only N partial aggregations and 1 total total aggregation are created.
 */
public class TimeSlidingWindowMetrics implements Metrics {
    
    private int timeWindowSize;

    /**
     * The time-based sliding window is implemented with a circular array of N partial aggregations (buckets).
     * 
     * If the time window size is 10 seconds, the circular array has always 10 partial aggregations (buckets). 
     * Every bucket aggregates the outcome of all calls which happen in a certain epoch second. (Partial aggregation). 
     * 
     * The head bucket of the circular array stores the call outcomes of the current epoch second. 
     * The other partial aggregations store the call outcomes of the previous seconds.
     */
    private TimeMeasurement[] timeAggregations;

    /**
     * The total aggregation is updated incrementally when a new call outcome is recorded. 
     */
    private Measurement totalAggregation;
    private int headIndex;
    
    private Clock clock;
    
    public TimeSlidingWindowMetrics(int timeWindowSize) {
        this.timeWindowSize = timeWindowSize;
        this.headIndex = 0;
        this.totalAggregation = new Measurement();
        
        this.clock = Clock.systemUTC();
        long epochSecond = clock.instant().getEpochSecond();
        this.timeAggregations = new TimeMeasurement[timeWindowSize];
        for (int i = 0; i < timeWindowSize; i++) {
            timeAggregations[i] = new TimeMeasurement(epochSecond);
            epochSecond++;
        }
    }

    @Override
    public Snapshot record(long duration, TimeUnit durationUnit, Outcome outcome) {
        totalAggregation.record(duration, durationUnit, outcome);
        
        TimeMeasurement currentTimeMeasurement = moveWindowToCurrentEpochSecond(getLatestTimeMeasurement());
        currentTimeMeasurement.record(duration, durationUnit, outcome);
        
        printStatus();
        return new SnapshotImpl(totalAggregation);
    }

    @Override
    public Snapshot getSnapshot() {
        // 为什么要move?
        // 清空过期的 TimeMeasurement
        TimeMeasurement currentTimeMeasurement = moveWindowToCurrentEpochSecond(getLatestTimeMeasurement());
        return new SnapshotImpl(totalAggregation);
    }

    /**
     * Moves the end of the time window to the current epoch second.
     * The latest bucket of the circular array is used to calculate how many seconds the window must be moved.
     *
     * @param latestTimeAggregation the latest partial aggregation of the circular array
     */
    private TimeMeasurement moveWindowToCurrentEpochSecond(TimeMeasurement latestTimeAggregation) {
        long currentEpochSecond = clock.instant().getEpochSecond();

        /**
         * The difference is calculated by subtracting the epoch second from the latest bucket from the current epoch second.
         */
        long diffInSeconds = currentEpochSecond - latestTimeAggregation.getEpochSecond();
        System.out.println(String.format(">> latestEpoch = %s, currentEpoch = %s", latestTimeAggregation.getEpochSecond(), currentEpochSecond));
        if (diffInSeconds == 0) {
            return latestTimeAggregation;
        }

        /**
         * If the difference is greater than the time window size, the time window size is used.
         */
        long secondsToMoveTheWindow = Math.min(diffInSeconds, timeWindowSize);

        /**
         * 1. headIndex 后移
         * 2. 总统计中，移除headIndex指标
         * 3. headIndex指标清空
         */
        TimeMeasurement currentTimeMeasurement;
        do {
            secondsToMoveTheWindow--;
            moveHeadIndexByOne();
            
            currentTimeMeasurement = getLatestTimeMeasurement();

            /**
             * When the oldest bucket is evicted, 
             * the partial total aggregation of that bucket is subtracted from the total aggregation and the bucket is reset. 
             * (Subtract-on-Evict)
             */
            totalAggregation.removeBucket(currentTimeMeasurement);
            // currentEpochSecond - secondsToMoveTheWindow ==> epochSecond 递增
            currentTimeMeasurement.reset(currentEpochSecond - secondsToMoveTheWindow);
            
        } while (secondsToMoveTheWindow > 0);
        
        return currentTimeMeasurement;
    }
    
    private TimeMeasurement getLatestTimeMeasurement() {
        return timeAggregations[headIndex];
    }
    
    private void moveHeadIndexByOne() {
        this.headIndex = (headIndex + 1) % timeWindowSize;
    }

    public void printStatus() {
        String measureStr = Arrays.stream(timeAggregations).map(TimeMeasurement::toString)
                                  .collect(Collectors.joining("\n"));
        System.out.println(String.format("headIndex=%s, measures=\n%s", headIndex, measureStr));
    }

    public static void main(String[] args) {

        testEpoch();
        testRecord();

    }
    
    private static void testEpoch() {
        // 1579346206671
        System.out.println(System.currentTimeMillis());
        // 1579346206672
        System.out.println(Clock.systemUTC().millis());
        // 1579346206
        System.out.println(Clock.systemUTC().instant().getEpochSecond());
    }

    private static void testRecord() {
        TimeSlidingWindowMetrics slidingWindow = new TimeSlidingWindowMetrics(3);
        // test aggregation
        slidingWindow.record(1, TimeUnit.SECONDS, Outcome.ERROR).report();
        sleep(400);
        
        slidingWindow.record(99, TimeUnit.MILLISECONDS, Outcome.SUCCESS).report();
        sleep(400);
        
        slidingWindow.record(120, TimeUnit.MILLISECONDS, Outcome.SLOW_SUCCESS).report();
        sleep(400);
        
        slidingWindow.record(55, TimeUnit.MILLISECONDS, Outcome.SLOW_ERROR).report();
        sleep(400);
        
        slidingWindow.record(60, TimeUnit.MILLISECONDS, Outcome.SUCCESS).report();
        sleep(400);
        
        slidingWindow.record(102, TimeUnit.MILLISECONDS, Outcome.ERROR).report();
        sleep(400);
    }
    
    private static void sleep(int milliSeconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
