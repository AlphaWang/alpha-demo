package com.alphawang.jdk.number;

import java.math.BigDecimal;

public class BigDecimalCompare {

    public static void main(String[] args) {
        //true
        System.out.println(BigDecimal.ZERO == BigDecimal.ZERO);
        //true
        System.out.println(BigDecimal.ZERO == BigDecimal.valueOf(0L));
        // F
        System.out.println(BigDecimal.ZERO == new BigDecimal(0L));
        //true
        System.out.println(BigDecimal.ZERO.equals(new BigDecimal(0L)));
        // F
        System.out.println(BigDecimal.ZERO.equals(BigDecimal.valueOf(0L, 2)));
        //true
        System.out.println(BigDecimal.ZERO.compareTo(BigDecimal.valueOf(0L, 2)) == 0);
    }
}
