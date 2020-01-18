package com.alphawang.algorithm.design.slidingwindow.meta;

public class TimeMeasurement extends Measurement {
    
    private long epochSecond;
    
    public TimeMeasurement(long epochSecond) {
        this.epochSecond = epochSecond;
    }

    public void reset(long epochSecond) {
        super.reset();
        this.epochSecond = epochSecond;
    }
    
    public long getEpochSecond() {
        return epochSecond;
    }

    @Override
    public String toString() {
        String measurement = super.toString();
        return String.format("epochSecond=%s, %s", epochSecond, measurement);
    }
}
