package com.alphawang.algorithm.design.slidingwindown;

import com.alphawang.algorithm.design.slidingwindown.meta.Metrics;
import com.alphawang.algorithm.design.slidingwindown.meta.Snapshot;
import java.util.concurrent.TimeUnit;

public class FixedSizeSlidingWindowMetrics implements Metrics {
    
    @Override
    public Snapshot record(long duration, TimeUnit durationUnit, Outcome outcome) {
        return null;
    }

    @Override
    public Snapshot getSnapshot() {
        return null;
    }
}
