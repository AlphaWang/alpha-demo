package com.alphawang.algorithm.design.slidingwindown.meta;

import java.util.concurrent.TimeUnit;

public class Measurement {
    
    long totalDurationInMillis = 0;
    int numberOfSlowCalls = 0;
    int numberOfFailedCalls = 0; //MVP
    int numberOfCalls = 0;   //MVP

    void record(long duration, TimeUnit durationUnit, Metrics.Outcome outcome){
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

    void reset() {
        this.totalDurationInMillis = 0;
        this.numberOfSlowCalls = 0;
        this.numberOfFailedCalls = 0;
        this.numberOfCalls = 0;
    }

    void removeBucket(Measurement bucket){
        this.totalDurationInMillis -= bucket.totalDurationInMillis;
        this.numberOfSlowCalls -= bucket.numberOfSlowCalls;
        this.numberOfFailedCalls -= bucket.numberOfFailedCalls;
        this.numberOfCalls -= bucket.numberOfCalls;
    }
}
