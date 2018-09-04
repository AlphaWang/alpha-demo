package com.alphawang.jdk.stream;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class StreamMapDemo {

    public static void main(String[] args) {
        /**获取单词，并且去重**/
        List<String> list = Arrays.asList(
            "hello welcome", 
            "world hello", 
            "hello world", 
            "hello world welcome");

        /**
         * map和flatmap的区别
         */
        log.error("---------- map");
        List<Stream<String>> mapResult = list.stream()
                .map(item -> Arrays.stream(item.split(" ")))
                .distinct()
                .collect(Collectors.toList());

        for (Stream<String> stream : mapResult) {
            System.out.println(">> stream string:");
            stream.forEach(System.out::println);
        }
        
        log.error("---------- flatMap");
        List<String> flatMapResult = list.stream()
                .flatMap(item -> Arrays.stream(item.split(" ")))
                .distinct()
                .collect(Collectors.toList());
        flatMapResult.forEach(System.out::println);



        log.error("---------- flatMap forEach");

        //也可以这样
        list.stream()
            .map(item -> item.split(" "))
            .flatMap(Arrays::stream)
            .distinct()
            .collect(Collectors.toList())
            .forEach(System.out::println);

    }
}
