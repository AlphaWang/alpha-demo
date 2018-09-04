package com.alphawang.jdk.stream;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class StreamSum {

    public static void main(String[] args) {
        int[] array = new int[] {1, 2, 3, 4, 5,};

        int sum = Arrays.stream(array).sum();
        
        log.info("stream.sum() : {}", sum);
    }
}
