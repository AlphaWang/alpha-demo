package com.alphawang.designpattern.creational.prototype.test;

import com.alphawang.designpattern.creational.prototype.Prototype;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrototypeTest {

    public static void main(String[] args) {
        Prototype prototype = new Prototype();
        prototype.setName("alpha");
        prototype.getList().add("1");

        try {
            Prototype obj = (Prototype) prototype.clone();
            log.info("name : {}, list : {}", obj.getName(), obj.getList());

            System.out.println("original: " + prototype);
            System.out.println("target: " + obj);

            /**
             * 浅拷贝，list字段是相等的、共用的
             */
            System.out.println("list equals: " + (prototype.getList() == obj.getList()));

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
