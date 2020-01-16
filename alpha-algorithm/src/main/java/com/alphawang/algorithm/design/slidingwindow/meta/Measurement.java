package com.alphawang.algorithm.design.slidingwindow.meta;

import java.util.concurrent.TimeUnit;

public class Measurement {
    
    long totalDurationInMillis = 0;
    int numberOfSlowCalls = 0;
    int numberOfFailedCalls = 0; //MVP
    int numberOfCalls = 0;   //MVP

    public void record(long duration, TimeUnit durationUnit, Metrics.Outcome outcome){
        this.numberOfCalls++;
        this.totalDurationInMillis += durationUnit.toMillis(duration);
        switch (outcome) {
            case SLOW_SUCCESS: 
                numberOfSlowCalls++;
                break;

            case SLOW_ERROR: 
                numberOfSlowCalls++; 
                numberOfFailedCalls++;
                break;

            case ERROR: 
                numberOfFailedCalls++;
                break;
        }
    }

    public void reset() {
        this.totalDurationInMillis = 0;
        this.numberOfSlowCalls = 0;
        this.numberOfFailedCalls = 0;
        this.numberOfCalls = 0;
    }

    public void removeBucket(Measurement bucket){
        this.totalDurationInMillis -= bucket.totalDurationInMillis;
        this.numberOfSlowCalls -= bucket.numberOfSlowCalls;
        this.numberOfFailedCalls -= bucket.numberOfFailedCalls;
        this.numberOfCalls -= bucket.numberOfCalls;
    }

    @Override
    public String toString() {
        return String.format("failCount=%s, slowCount=%s, totalCount=%s, totalDuration=%s", 
                             numberOfFailedCalls, numberOfSlowCalls, numberOfCalls, totalDurationInMillis);
    }
}
