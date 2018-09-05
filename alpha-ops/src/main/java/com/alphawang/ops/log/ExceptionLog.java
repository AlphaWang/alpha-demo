package com.alphawang.ops.log;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionLog {

    public static void main(String[] args) {
        RuntimeException exception = new RuntimeException("test", new RuntimeException("nested"));
        log.error("failed to get badge for {}", "rocket", exception);
        
        System.out.println("-----------");
        log.error("failed to get badge for ", "rocket", exception);

        System.out.println("-----------");
        log.error("failed to get badge for {} {}", "rocket", exception);
    }
}
