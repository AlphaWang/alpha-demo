package com.alphawang.algorithm.design.slidingwindow.meta;

import java.time.Duration;

public class SnapshotImpl implements Snapshot {
    private final long totalDurationInMillis;
    private final int totalNumberOfSlowCalls;
    private final int totalNumberOfFailedCalls;
    private final int totalNumberOfCalls;

    public SnapshotImpl(Measurement totalAggregation) {
        this.totalDurationInMillis = totalAggregation.totalDurationInMillis;
        this.totalNumberOfSlowCalls = totalAggregation.numberOfSlowCalls;
        this.totalNumberOfFailedCalls = totalAggregation.numberOfFailedCalls;
        this.totalNumberOfCalls = totalAggregation.numberOfCalls;
    }

    @Override
    public Duration getTotalDuration() {
        return Duration.ofMillis(totalDurationInMillis);
    }

    @Override
    public int getNumberOfSlowCalls() {
        return totalNumberOfSlowCalls;
    }

    @Override
    public float getSlowCallRate() {
        if(totalNumberOfCalls == 0){
            return 0;
        }
        return totalNumberOfSlowCalls * 100.0f / totalNumberOfCalls;
    }

    @Override
    public int getNumberOfSuccessfulCalls() {
        return totalNumberOfCalls - totalNumberOfFailedCalls;
    }

    /**
     * MVP
     * @return
     */
    @Override
    public int getNumberOfFailedCalls() {
        return totalNumberOfFailedCalls;
    }

    /**
     * MVP
     * @return
     */
    @Override
    public int getTotalNumberOfCalls() {
        return totalNumberOfCalls;
    }

    /**
     * MVP
     * @return
     */
    @Override
    public float getFailureRate() {
        if(totalNumberOfCalls == 0){
            return 0;
        }
        return totalNumberOfFailedCalls * 100.0f / totalNumberOfCalls;
    }

    /**
     * MVP
     * Q: What if need P95 duration?
     * @return
     */
    @Override
    public Duration getAverageDuration() {
        if(totalNumberOfCalls == 0){
            return Duration.ZERO;
        }
        return Duration.ofMillis(totalDurationInMillis / totalNumberOfCalls);
    }

    @Override
    public void report() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        int total = getTotalNumberOfCalls();
        int totalFail = getNumberOfFailedCalls();
        float failRate = getFailureRate();
        
        return String.format("Fail Rate: %f = %s / %s", failRate, totalFail, total);
    }
}
