package com.alphawang.zookeeper.curator.listener;

import com.alphawang.zookeeper.curator.connect.CuratorConnector;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

import java.util.List;

@Slf4j
public class PathChildrenCacheTest {

    public static void main(String[] args) throws InterruptedException {
        CuratorConnector curatorConnector = new CuratorConnector();
        String nodePath = "/imooc/curator";

        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorConnector.getCuratorFramework(), nodePath, true);

        try {
            //            pathChildrenCache.start();
            /**
             * StartMode: 初始化方式
             *
             * POST_INITIALIZED_EVENT：异步初始化，初始化之后会触发事件
             * NORMAL：异步初始化
             * BUILD_INITIAL_CACHE：同步初始化
             */
            pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        } catch (Exception e) {
            log.error("Failed to start node cache", e);
        }

        getCurrentData(pathChildrenCache);

        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                log.warn("[PathChildrenCacheListener] >>> Received PathChildrenCacheEvent. type = {}, data = {}", event.getType(), event.getData());
            }
        });

        Thread.sleep(100000);
        curatorConnector.close();
    }

    private static void getCurrentData(PathChildrenCache pathChildrenCache) {
        List<ChildData> childDataList = pathChildrenCache.getCurrentData();
        log.warn("PathChildrenCache current data {}", childDataList);
    }
}
