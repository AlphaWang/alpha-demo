package com.alphawang.jdk.collection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapDeadLoop {

    public static void main(String[] args) {
        testDeadLoop1();
//        testDeadLoop2();
    }
    
    
    private static void testDeadLoop1() {
        Map<String, String> map = new ConcurrentHashMap<>();

        String v = map.computeIfAbsent("dummy", key -> key + "-v");
        System.out.println(map + " : " + v);

        /**
         * AaAa 和 BBBB 碰巧 hashCode 一样
         */
        System.out.println("AaAa".hashCode());
        System.out.println("BBBB".hashCode());
        v = map.computeIfAbsent("AaAa", key -> {
            return map.computeIfAbsent("BBBB", key2 -> "111");
        });
        System.out.println(map + " : " + v);
    }
    
    
    private static void testDeadLoop2() {
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        // map默认capacity 16，当元素个数达到(capacity - capacity >> 2) = 12个时会触发rehash
        for (int i = 0; i < 11; i++) {
            map.put(i, i);
        }
        
        System.out.println(map);
        map.computeIfAbsent(12, (k) -> {
            System.out.println(map);
            
            // 这里会导致死循环 :(
            map.put(100, 100);
            System.out.println(map);
            return k;
        });
    }
}
