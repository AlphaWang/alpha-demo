package com.alphawang.concurrency.common.c3_publish;

import com.alphawang.concurrency.common.annotations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * 23:52:53 [main] INFO  c.a.c.c3_publish.UnsafePublish - INIT: [a, b, c]
 * 23:52:53 [main] INFO  c.a.c.c3_publish.UnsafePublish - MODIFIED: [d, b, c]
 */
@Slf4j
@NotThreadSafe
public class UnsafePublish {

    private String[] states = { "a", "b", "c" };

    public String[] getStates() {
        return states;
    }

    public static void main(String[] args) {
        UnsafePublish unsafePublish = new UnsafePublish();
        log.info("INIT: {}", Arrays.toString(unsafePublish.getStates()));

        // 其他线程可以直接修改field，造成状态错误
        unsafePublish.getStates()[0] = "d";
        log.info("MODIFIED: {}", Arrays.toString(unsafePublish.getStates()));

    }
}
