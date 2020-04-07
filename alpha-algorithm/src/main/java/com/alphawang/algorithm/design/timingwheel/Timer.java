package com.alphawang.algorithm.design.timingwheel;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Timer {
    
    private TimingWheel timingWheel;
    
    //TODO?
    private DelayQueue<TimerTaskList> delayQueue = new DelayQueue<>();
    
    private ExecutorService workerThreadPool;
    
    private ExecutorService bossThreadPool;
    
    public Timer() {
        timingWheel = new TimingWheel(1, 10, System.currentTimeMillis(), delayQueue);
        workerThreadPool = Executors.newFixedThreadPool(100);
        bossThreadPool = Executors.newFixedThreadPool(1);
        
        bossThreadPool.submit(() -> {
            //20ms获取一次过期任务
            while (true) {
                this.advanceClock(20);
            }
        });
    }
    
    
    private void advanceClock(long timeout) {
        try {
            TimerTaskList taskList = delayQueue.poll(timeout, TimeUnit.MILLISECONDS);
            if (taskList != null) {
                long expiration = taskList.getExpiration();
                timingWheel.advanceClock(expiration);
                taskList.flush(this::addTask);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void addTask(TimerTask task) {
        boolean result = timingWheel.addTask(task);
        if (!result) {
            workerThreadPool.submit(task.getTask());
        }
    }
}
