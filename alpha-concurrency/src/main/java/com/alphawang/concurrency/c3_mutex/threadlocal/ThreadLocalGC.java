package com.alphawang.concurrency.c3_mutex.threadlocal;

import java.lang.reflect.Field;

public class ThreadLocalGC {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        Thread t = new Thread(()->test("abc",false));
        t.start();
        t.join();
        
        System.out.println("--gc后--");
        Thread t2 = new Thread(() -> test("def", true));
        t2.start();
        t2.join();
    }

    private static void test(String s, boolean isGC)  {
        try {
            /**
             * 有强引用指向 ThreadLocal，GC 后 key/value 均不为null
             */
            ThreadLocal threadLocal = new ThreadLocal<>();
            threadLocal.set(s + "-1");

            /**
             * 无强引用指向 ThreadLocal，GC 后 key == null, value != null
             * 出现内存泄漏！
             */
            new ThreadLocal<>().set(s + "-2");
            
            if (isGC) {
                System.gc();
            }
            Thread t = Thread.currentThread();
            Class<? extends Thread> clz = t.getClass();
            Field field = clz.getDeclaredField("threadLocals");
            field.setAccessible(true);
            Object threadLocalMap = field.get(t);
            Class<?> tlmClass = threadLocalMap.getClass();
            Field entryArray = tlmClass.getDeclaredField("table");
            entryArray.setAccessible(true);
            Object[] arr = (Object[]) entryArray.get(threadLocalMap);
            for (Object entry : arr) {
                if (entry != null) {
                    Class<?> entryClass = entry.getClass();
                    Field entryValue = entryClass.getDeclaredField("value");
                    Field referenceField = entryClass.getSuperclass().getSuperclass().getDeclaredField("referent");
                    entryValue.setAccessible(true);
                    referenceField.setAccessible(true);
                    System.out.println(String.format("弱引用key = %s, value = %s", referenceField.get(entry), entryValue.get(entry)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
