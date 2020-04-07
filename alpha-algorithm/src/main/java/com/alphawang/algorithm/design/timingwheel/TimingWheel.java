package com.alphawang.algorithm.design.timingwheel;

import java.util.concurrent.DelayQueue;

public class TimingWheel {
    
    private long tickMs;
    private int wheelSize;
    
    // 一轮的总时间
    private long interval;
    
    private TimerTaskList[] buckets;
    
    private long currentTime;
    
    // 上层时间轮
    private volatile TimingWheel overflowWheel;
               
    //TODO 
    private DelayQueue<TimerTaskList> delayQueue;
    
    public TimingWheel(long tickMs, int wheelSize, long currentTime, DelayQueue<TimerTaskList> delayQueue) {
        this.currentTime = currentTime;
        this.tickMs = tickMs;
        this.wheelSize = wheelSize;
        this.interval = tickMs * wheelSize;
        
        // 取整
        this.currentTime = currentTime - (currentTime % tickMs);
        this.delayQueue = delayQueue;

        this.buckets = new TimerTaskList[wheelSize];
        for (int i = 0; i < wheelSize; i++) {
            buckets[i] = new TimerTaskList();
        }
    }
    
    private TimingWheel getOverflowWheel() {
        if (overflowWheel == null) {
            synchronized (this) {
                if (overflowWheel == null) {
                    overflowWheel = new TimingWheel(interval, wheelSize, currentTime, delayQueue);
                }
            }
        }
        
        return overflowWheel;
    }
    
    public boolean addTask(TimerTask task) {
        long executeMs = task.getExecuteMs();
        
        // 已过期
        if (executeMs < currentTime + tickMs) {
            return false;
        }
        
        // 上一时间轮
        if (executeMs >= currentTime + interval) {
            TimingWheel overflowWheel = getOverflowWheel();
            overflowWheel.addTask(task);
            return true;
        } 
        
        // 当前时间轮
        long virtualId = executeMs / tickMs;
        int index = (int) (virtualId % wheelSize); 
        TimerTaskList slot = buckets[index];
        slot.addTask(task);
        
        //TODO? 
        if (slot.setExpiration(virtualId * tickMs)) {
            delayQueue.offer(slot);
        }
        return true;
    }
    
    //TODO
    public void advanceClock(long timestamp) {
        if (timestamp > currentTime + tickMs) {
            currentTime = timestamp - (timestamp % tickMs);
            
            if (overflowWheel != null) {
                overflowWheel.advanceClock(timestamp);
            }
        }
    }
    
}
