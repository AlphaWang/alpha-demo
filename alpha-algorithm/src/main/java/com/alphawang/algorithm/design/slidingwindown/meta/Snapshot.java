package com.alphawang.algorithm.design.slidingwindown.meta;

import java.time.Duration;

public interface Snapshot {
    
    Duration getTotalDuration();
    
    Duration getAverageDuration();
    
    int getNumberOfSlowCalls();
    
    float getSlowCallRate();
    
    int getNumberOfSuccessfulCalls();
    
    int getNumberOfFailedCalls();
    
    int getTotalNumberOfCalls();
    
    float getFailureRate();
}
