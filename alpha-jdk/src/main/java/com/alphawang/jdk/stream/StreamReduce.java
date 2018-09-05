package com.alphawang.jdk.stream;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class StreamReduce {

    public static void main(String[] args) {
        List<String> list = Lists.newArrayList("A", "B", "C");
        
        Optional<String> result = list.stream().reduce(String::concat);
        log.info("stream.reduce() : {}", result.get());

        String collect = list.stream().collect(Collectors.joining(" - "));
        log.info("collect(Collectors.joining()) : {}", collect);
    }
}
