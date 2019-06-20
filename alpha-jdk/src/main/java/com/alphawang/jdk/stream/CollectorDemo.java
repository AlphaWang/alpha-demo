package com.alphawang.jdk.stream;

import com.google.common.collect.Lists;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

public class CollectorDemo {

    public static void main(String[] args) {
        /**
         * 转换成其他集合
         * toCollection(Supplier)
         */
        List<Integer> numbers = Lists.newArrayList(1, 5, 3, 4);  // IntStream.of(1, 5, 3, 4);

        TreeSet<Integer> treeSet = numbers.stream()
            .collect(toCollection(TreeSet::new));
        System.out.println("to TreeSet" + treeSet);

        /**
         * 转换成值
         * Collectors.maxBy
         * Collectors.averagingInt
         */
        Optional<Integer> max = numbers.stream()
            .collect(maxBy(Comparator.comparing(x -> x)));
        System.out.println("max: " + max.get());

        Double avg = numbers.stream()
            .collect(averagingInt(x -> x));
        System.out.println("avg: " + avg);

        /**
         * 数据分块
         * Collectors.partitionBy
         */
        Map<Boolean, List<Integer>> partition = numbers.stream()
            .collect(partitioningBy(x -> x % 2 == 0));
        System.out.println("partition: " + partition);

        /**
         * 数据分组
         * Collectors.groupingBy + counting + mapping
         */
        //   group: {0=[4], 1=[1, 5, 3]}
        Map<Integer, List<Integer>> group = numbers.stream()
            .collect(groupingBy(x -> x % 2));
        System.out.println("group: " + group);

        // groupingBy + mapping: 分组+转换
        //   group mapping: {0=[int-4], 1=[int-1, int-5, int-3]}
        Map<Integer, List<String>> groupMappipng = numbers.stream()
            .collect(groupingBy(x -> x % 2, mapping(x -> "int-" + x, toList())));
        System.out.println("group mapping: " + groupMappipng);

        // groupingBy + counting: 分组+计数
        //   group counting: {0=1, 1=3}
        Map<Integer, Long> groupCounting = numbers.stream()
            .collect(groupingBy(x -> x % 2, counting()));
        System.out.println("group counting: " + groupCounting);

        /**
         * 拼接字符串
         * Collectors.joining(CharSequence delimiter,
         *                    CharSequence prefix,
         *                    CharSequence suffix) 
         */
        String joining = numbers.stream()
            .map(x -> "int-" + x)
            .collect(joining(", ", "[", "]"));
        System.out.println("joining: " + joining);
    }
}
