package com.alphawang.jdk.optional;

import java.util.Optional;

public class OptionalElse {

    public static void main(String[] args) {
        String result = Optional.ofNullable(Service.get())
            .map(s -> s.toUpperCase())
            .orElse("NULL");

        System.out.println(result);
    }

    static class Service {
        public static String get() {
            //            return null;
            return "alpha";
        }
    }
}
