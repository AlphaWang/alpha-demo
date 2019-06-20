package com.alphawang.jdk.jmm;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class HeapTest {

    /**
     * ﻿堆用于存储 对象实例 以及 数组。
     *
     * ﻿如果堆中没有内存完成实例分配，并且堆也无法再扩展时，则抛出OutOfMemoryError。
     * 可以通过减少-Xms, -Xmx；同时创建无数对象来模拟OutOfMemoryError。
     * 同时-XX:+HeapDumpOnOutOfMemoryError，可以dump出当前的内存堆转储快照，以便分析。
     */
    public static void main(String[] args) {
        List<Inner> list = Lists.newArrayList();
        while (true) {
            log.info("creating object...");
            list.add(new Inner());
        }
    }

    private static class Inner {
    }
}
