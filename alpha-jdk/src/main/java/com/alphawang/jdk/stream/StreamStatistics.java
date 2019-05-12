package com.alphawang.jdk.stream;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.IntSummaryStatistics;

@Slf4j
public class StreamStatistics {

    public static void main(String[] args) {
        int[] array = new int[] { 1, 2, 3, 4, 5, };

        /**
         * Stream.sum()
         */
        int sum = Arrays.stream(array).sum();
        log.info("stream.sum() : {}", sum);

        /**
         * Stream.summaryStatistics()
         */
        IntSummaryStatistics statistics = Arrays.stream(array)
            .summaryStatistics();
        log.info("avg = {}, sum = {}, count = {}",
            statistics.getAverage(),
            statistics.getSum(),
            statistics.getCount());
    }
}
