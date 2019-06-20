package com.alphawang.zookeeper.primary.callback;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.AsyncCallback;

@Slf4j
public class ZkVoidCallback implements AsyncCallback.VoidCallback {
    @Override
    public void processResult(int rc, String path, Object ctx) {
        /**
         * if version is bad: rc=-103
         * if version is good: rc=0
         */
        log.warn("[VoidCallback] >>>> rc={}, path={}, ctx={}", rc, path, ctx);
    }
}
