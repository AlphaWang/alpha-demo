package com.alphawang.jdk.stream;

import java.util.HashMap;
import java.util.Map;

public class MapCompute {

    private static Map<String, Integer> counter = new HashMap<>();

    public static void main(String[] args) {
        add("Alpha", 1);
        add("M", 1);
        add("Alpha", 2);
        add("M", 1);
        
        System.out.println(counter);
        
    }
    
    private static void add(String key, int count) {
        counter.compute(key, (k, previousValue) ->  previousValue == null ? count : count + previousValue);
    }
}
