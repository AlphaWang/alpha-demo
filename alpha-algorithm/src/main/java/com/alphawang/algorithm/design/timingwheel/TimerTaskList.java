package com.alphawang.algorithm.design.timingwheel;

import java.util.Comparator;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class TimerTaskList implements Delayed {
    
    private TimerTask root = new TimerTask(-1L, null);
    
    //环形！
    {
        root.setPrev(root);
        root.setNext(root);
    }
    
    private AtomicLong expiration = new AtomicLong(-1L);
    
    public boolean setExpiration(long exp) {
        long preValue = expiration.getAndSet(exp);
        return preValue != exp;
    }
    
    public long getExpiration() {
        return expiration.get();
    }

    /**
     * 新增任务：将 task 插入 root 与 root.prev 之间。
     * @param task
     */
    public void addTask(TimerTask task) {
        synchronized (this) {
            if (task.getBucket() == null) {
                task.setBucket(this);
                
                TimerTask tail = root.getPrev();
                task.setNext(root);
                task.setPrev(tail);
                tail.setNext(task);
                root.setPrev(task);
            }
        }
    }

    /**
     * 移除任务：断链
     * @param task
     */
    public void removeTask(TimerTask task) {
        synchronized (this) {
            if (task.getBucket().equals(this)) {
                task.getNext().setPrev(task.getPrev());
                task.getPrev().setNext(task.getNext());
                
                task.setBucket(null);
                task.setPrev(null);
                task.setNext(null);
            }
        }
    }
    
    public synchronized void flush(Consumer<TimerTask> flush) {
        TimerTask task = root.getNext();
        while (!task.equals(root)) {
            removeTask(task);
            flush.accept(task);
            
            task = root.getNext();
        }
        
        //TODO ?
        expiration.set(-1L);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long now = System.currentTimeMillis();
        return Math.max(0, unit.convert(expiration.get() - now, TimeUnit.MILLISECONDS));
    }

    @Override
    public int compareTo(Delayed o) {
        if (o instanceof TimerTaskList) {
            TimerTaskList that = (TimerTaskList) o;
//            return Long.compare(expiration.get(), that.getExpiration());
            return Comparator.comparingLong(TimerTaskList::getExpiration).compare(this, that);
        }
            
        return 0;
    }
}
