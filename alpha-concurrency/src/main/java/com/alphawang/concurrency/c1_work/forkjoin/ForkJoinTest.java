package com.alphawang.concurrency.c1_work.forkjoin;

import com.alphawang.concurrency.common.Printer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        
        CountTask task = new CountTask(1, 100);

        Future<Integer> future = forkJoinPool.submit(task);

        Integer result = future.get();

        Printer.print(result);
    }
}

/**
 * Fork 任务。
 * 
 * 如果有返回值，用 RecursiveTask
 * 如果无返回值，用 RecursiveAction
 */
class CountTask extends RecursiveTask<Integer> {
    
    private static final int THRESHOLD = 5;
    private int start;
    private int end;
    
    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        
        boolean canCompute = (end - start) <= THRESHOLD;
        // 任务足够小
        if (canCompute) {
            if (3 > start && 3 < end) {
                throw new RuntimeException("mock exception"); 
            }
            for (int i = start; i <= end; i ++) {
               sum += i; 
            }
        }
        // 否则拆分
        else {
            int middle = start + (end - start) / 2;
            Printer.print(String.format("--- Split to [%s, %s], [%s, %s]", start, middle, middle + 1, end));
            CountTask leftTask = new CountTask(start, middle);
            CountTask rightTask = new CountTask(middle + 1, end);

            /**
             * Fork
             */
            leftTask.fork();
            rightTask.fork();

            /**
             * Join
             */

            if (leftTask.isCompletedAbnormally()) {
                Printer.print(leftTask.getException());
            }
            if (rightTask.isCompletedAbnormally()) {
                Printer.print(rightTask.getException());
            }
            
            int leftResult = leftTask.join();
            int rightResult = rightTask.join();

            if (leftTask.isCompletedAbnormally()) {
                Printer.print(leftTask.getException());
            }
            if (rightTask.isCompletedAbnormally()) {
                Printer.print(rightTask.getException());
            }
            
            sum = leftResult + rightResult;
        }
        
        return sum;
    }
}
