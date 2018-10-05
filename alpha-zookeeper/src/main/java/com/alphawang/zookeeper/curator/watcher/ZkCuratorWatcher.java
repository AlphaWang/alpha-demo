package com.alphawang.zookeeper.curator.watcher;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;

@Slf4j
public class ZkCuratorWatcher implements org.apache.curator.framework.api.CuratorWatcher {
    @Override 
    public void process(WatchedEvent event) throws Exception {
        log.error("[CuratorWatcher] >>> received WatchedEvent {}", event);
    }
}
