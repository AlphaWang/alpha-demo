package com.alphawang.jdk.stream;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class StreamMapCombineDemo {

    public static void main(String[] args) {

        /**
         * 相互组合
         */
        List<String> list2 = Arrays.asList("hello", "hi", "你好");
        List<String> list3 = Arrays.asList("zhangsan", "lisi", "wangwu", "zhaoliu");

        log.error("---------- map");
        List<Stream<String>> collect = list2.stream()
            .map(item -> list3.stream().map(item2 -> item + " " + item2))
            .collect(Collectors.toList());

        for (Stream<String> stream : collect) {
            System.out.println(">> stream string:");
            stream.forEach(System.out::println);
        }

        log.error("---------- flatMap");
        List<String> collect1 = list2.stream()
            .flatMap(item -> list3.stream().map(item2 -> item + " " + item2))
            .collect(Collectors.toList());

        collect1.forEach(System.out::println);

    }
}
