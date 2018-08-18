package com.alphawang.ops.log;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
public class Mdc {

    public static void main(String[] args) {
        MDC.put("mdc_track_id", "MDC track id test.");
        
        log.info("this is a test msg.");
    }
    
}
