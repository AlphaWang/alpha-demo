package com.alphawang.algorithm.design.timingwheel;


public class TimerTask {
    
    private long executeMs;
    
    private Runnable task;
    
    
    private TimerTask prev;
    private TimerTask next;
    
    // TODO
    private TimerTaskList bucket;

    public TimerTask(long delayMs, Runnable task) {
        this.executeMs = System.currentTimeMillis() + delayMs;
        this.task = task;
        
        this.prev = null;
        this.next = null;
    }

    ////// GETTER & SETTER
    
    public long getExecuteMs() {
        return executeMs;
    }

    public void setExecuteMs(long executeMs) {
        this.executeMs = executeMs;
    }

    public Runnable getTask() {
        return task;
    }

    public void setTask(Runnable task) {
        this.task = task;
    }

    public TimerTask getPrev() {
        return prev;
    }

    public void setPrev(TimerTask prev) {
        this.prev = prev;
    }

    public TimerTask getNext() {
        return next;
    }

    public void setNext(TimerTask next) {
        this.next = next;
    }

    public TimerTaskList getBucket() {
        return bucket;
    }

    public void setBucket(TimerTaskList bucket) {
        this.bucket = bucket;
    }
}
