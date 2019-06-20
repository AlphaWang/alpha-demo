package com.alphawang.jdk.jmm;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MethodAreaTest {

    /**
     * ﻿方法区 存储 类型信息 和 类的静态变量。
     * 在Sun JDK中，方法区对应了永久代（Permanent Generation），默认最小值为16MB，最大值为64MB。
     *
     * 方法区中对于每个类存储了以下数据：
     * 类及其父类的全限定名（java.lang.Object没有父类）
     * 类的类型（Class or Interface）
     * 访问修饰符（public, abstract, final）
     * 实现的接口的全限定名的列表
     * 常量池
     * 字段信息
     * 方法信息
     * 静态变量
     * ClassLoader引用
     * Class引用
     *
     * 可见类的所有信息都存储在方法区中。
     */

    /**
     *﻿﻿当方法区无法满足内存分配需求时，将抛出OutOfMemoryError。
     * ——OSGi这种频繁自定义ClassLoader的场景，需要虚拟机剧本类卸载功能，以保证永久代不会溢出。
     *
     * 通过限制永久代大小-XX:PermSize, -XX:MaxPermSize；同时大量添加常量池；或借助CGLib生成大量动态类，可以模拟OutOfMemoryError。
     * ——注：运行时添加常量池可用list.add(String.valueOf(i++).intern()) //Jdk7以下
     */
    public static void main(String[] args) {
        List<String> list = Lists.newArrayList();
        int i = 0;

        while (true) {
            log.info("adding...");
            list.add(String.valueOf(i++).intern());
        }
    }
}
