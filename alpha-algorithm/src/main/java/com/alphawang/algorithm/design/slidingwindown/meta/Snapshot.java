package com.alphawang.algorithm.design.slidingwindown.meta;

import java.time.Duration;

public interface Snapshot {
    
    Duration getTotalDuration();
    
    Duration getAverageDuration();
    
    int getNumberOfSlowCalls();
    
    float getSlowCallRate();
    
    int getNumberOfSuccessfulCalls();
    
    //MVP
    int getNumberOfFailedCalls();
             
    //MVP
    int getTotalNumberOfCalls();
    
    //MVP
    float getFailureRate();
    
    void report();
    
}
