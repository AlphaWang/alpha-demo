package com.alphawang.zookeeper.primary.connect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.List;

@Slf4j
public class ZKWatcher implements Watcher {
    
    @Override 
    public void process(WatchedEvent event) {
        log.warn("[Watcher] >>> accepted event {}", event);
        printData(event);
    }
    
    private void printData(WatchedEvent event) {
        if (StringUtils.isEmpty(event.getPath())) {
            return;
        }

        String path = event.getPath();
        ZKConnector connector = new ZKConnector();
        try {
            if (event.getType() == Event.EventType.NodeChildrenChanged) {
                List<String> children = connector.getZooKeeper().getChildren(path, false);
                log.info("[Watcher] >>> {} children changed: {}", path, children);
            }
        } catch (KeeperException | InterruptedException e) {
            log.error("get data error. ", e);
        } 
    }
}
