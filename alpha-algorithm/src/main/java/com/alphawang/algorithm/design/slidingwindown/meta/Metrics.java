package com.alphawang.algorithm.design.slidingwindown.meta;

import java.util.concurrent.TimeUnit;

public interface Metrics {

    /**
     * Records a call.
     */
    Snapshot record(long duration, TimeUnit durationUnit, Outcome outcome);

    /**
     * Returns a snapshot.
     */
    Snapshot getSnapshot();

    enum Outcome {
        SUCCESS, ERROR, SLOW_SUCCESS, SLOW_ERROR
    }

}