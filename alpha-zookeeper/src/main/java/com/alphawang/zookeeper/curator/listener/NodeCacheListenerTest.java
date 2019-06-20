package com.alphawang.zookeeper.curator.listener;

import com.alphawang.zookeeper.curator.connect.CuratorConnector;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;

import java.util.concurrent.TimeUnit;

@Slf4j
public class NodeCacheListenerTest {

    public static void main(String[] args) throws InterruptedException {
        CuratorConnector curatorConnector = new CuratorConnector();
        String nodePath = "/imooc/curator";

        /**
         * NodeCache: 监听数据节点的变更，会触发事件
         */
        NodeCache nodeCache = new NodeCache(curatorConnector.getCuratorFramework(), nodePath);
        try {
            nodeCache.start(); // NodeCache 参数: 监听数据节点的变更，会触发事件
        } catch (Exception e) {
            log.error("Failed to start node cache", e);
        }

        getCurrentData(nodeCache);

        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                log.warn("[NodeCacheListener] >>> Received Node Change Event.");
                getCurrentData(nodeCache);
            }
        });

        TimeUnit.SECONDS.sleep(2);
        curatorConnector.close();
    }

    private static void getCurrentData(NodeCache nodeCache) {
        ChildData currentData = nodeCache.getCurrentData();
        if (currentData != null) {
            log.warn("NodeCache current data: {} = {}", currentData.getPath(), new String(currentData.getData()));
        } else {
            log.warn("NodeCache no init data.");
        }
    }
}
