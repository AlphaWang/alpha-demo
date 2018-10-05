package com.alphawang.zookeeper.lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

@Slf4j 
public class DistributedLock {

    private CuratorFramework curatorFramework;

    // 分布式锁 总节点
    private static final String ZK_LOCK_PROJECT = "/imooc-locks";
    // 分布式锁 节点
    private static final String DISTRIBUTED_LOCK = "distributed_lock";

    // 用于挂起当前请求，等待上一个分布式锁释放
    private static CountDownLatch zkLockLatch = new CountDownLatch(1);

    public DistributedLock(CuratorFramework curatorFramework) {
        this.curatorFramework = curatorFramework;
    }

    public void init() {
        curatorFramework = curatorFramework.usingNamespace("ZKLocks-Namespace");

        try {
            if (curatorFramework.checkExists().forPath(ZK_LOCK_PROJECT) == null) {
                curatorFramework.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT)
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                    .forPath(ZK_LOCK_PROJECT);

            }
            
            addWatcherToLock(ZK_LOCK_PROJECT);
            
        } catch (Exception e) {
            log.error("Failed to connect zookeeper server.", e);
        }
    }

    public void getLock() {
        while(true) {
            try {
                curatorFramework.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                    .forPath(ZK_LOCK_PROJECT + "/" + DISTRIBUTED_LOCK);
                log.info("Got distributed lock.");
                return;
            } catch (Exception e) {
                log.warn("Failed to get distributed lock.", e);
                
                if (zkLockLatch.getCount() <= 0) {
                    zkLockLatch = new CountDownLatch(1);
                }
                try {
                    zkLockLatch.await();
                } catch (InterruptedException e1) {
                    log.error("Failed to wait CountDownLatch", e1);
                }
            }
        }

    }

    public boolean releaseLock() {
        try {
            Stat stat = curatorFramework.checkExists().forPath(ZK_LOCK_PROJECT + "/" + DISTRIBUTED_LOCK);
            if (stat != null) {
                curatorFramework.delete()
                    .forPath(ZK_LOCK_PROJECT + "/" + DISTRIBUTED_LOCK);
            }
        } catch (Exception e) {
            log.error("Failed to check/delete zk node", e);
            return false;
        }
        
        log.info("Released lock.");
        return false;
    }

    private void addWatcherToLock(String path) throws Exception {
        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, path, true);
        pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override 
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                if (event.getType() == PathChildrenCacheEvent.Type.CHILD_REMOVED) {
                    String path = event.getData().getPath();
                    log.info("Path Deleted. {}", path);
                    
                    if (path.contains(DISTRIBUTED_LOCK)) {
                        log.info("release CountDownLatch");
                        zkLockLatch.countDown();
                    }
                }
            }
        });

    }

}
