package com.alphawang.jdk.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConcurrentModificationTest {

    private static List<String> names = new ArrayList<>();
    
    static {
        names.add("a");
        names.add("b");
        names.add("c");
        names.add("d"); 
    }
       
    public static void main(String[] args) {
        testListRemove();
        testMultiIterator();
    }
    
    private static void testListRemove() {
        Iterator<String> iterator1 = names.iterator();
        String next = iterator1.next();
        System.out.println(next);
        
        names.remove(0);

        /**
         * remove() / next() 中会检查 modCount == expectedModCount
         */
        next = iterator1.next(); // ConcurrentModificationException
        System.out.println(next);
    }
    
    
    private static void testMultiIterator() {
        Iterator<String> iterator1 = names.iterator();
        Iterator<String> iterator2 = names.iterator();
        
        String next = iterator1.next();
        System.out.println(next);
        
        iterator1.remove();

        /**
         * 一个 Iterator的remove, 会影响另一个Iterator
         */
        String next2 = iterator2.next(); // 运行结果？
        System.out.println(next2);
    }
    
    
    

}
