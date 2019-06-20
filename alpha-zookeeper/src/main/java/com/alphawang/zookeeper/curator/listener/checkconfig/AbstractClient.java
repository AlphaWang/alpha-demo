package com.alphawang.zookeeper.curator.listener.checkconfig;

import com.alphawang.zookeeper.curator.connect.CuratorConnector;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

import java.util.concurrent.CountDownLatch;

import static org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent.Type.CHILD_UPDATED;

@Slf4j
public abstract class AbstractClient {

    public final static String CONFIG_NODE_PATH = "/imooc/curator/config";
    public final static String SUB_PATH = "/redis-config";
    public static CountDownLatch countDown = new CountDownLatch(1);

    protected CuratorConnector curatorConnector;

    public AbstractClient() {
        curatorConnector = new CuratorConnector();
    }

    protected void listen() throws Exception {
        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorConnector.getCuratorFramework(), CONFIG_NODE_PATH, true);
        pathChildrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);

        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                log.warn("[PathChildrenCacheListener] >>>> {} received PathChildrenCacheEvent {}", getClientName(), event);
                if (event.getType() != CHILD_UPDATED) {
                    return;
                }

                String path = event.getData().getPath();
                if ((CONFIG_NODE_PATH + SUB_PATH).equals(path)) {
                    log.warn("---- config changed!!!");

                    String jsonConfig = new String(event.getData().getData());
                    log.warn("---- new config: {}", jsonConfig);

                    // convert to object
                    // apply the new config.
                }

            }
        });
    }

    protected abstract String getClientName();

}
