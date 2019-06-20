package com.alphawang.jdk.stream;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParallelStream {

    private static final int N = 100;

    public static void main(String[] args) {

        /**
         * Stream.parallel()
         * Collection.parallelStream()
         */

        IntStream.range(0, N)
            .parallel()
            .mapToObj(i -> heavyCompute(i))
            .collect(Collectors.toList());
    }

    /**
     * main computing 717
     * ForkJoinPool.commonPool-worker-2 computing 592
     * ForkJoinPool.commonPool-worker-1 computing 561
     * ForkJoinPool.commonPool-worker-6 computing 623
     */
    private static String heavyCompute(int number) {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String out = Thread.currentThread().getName() + " computing " + number;
        System.out.println(out);
        return out;
    }
}
