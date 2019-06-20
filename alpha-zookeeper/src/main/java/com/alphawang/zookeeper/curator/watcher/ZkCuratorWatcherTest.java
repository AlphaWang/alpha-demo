package com.alphawang.zookeeper.curator.watcher;

import com.alphawang.zookeeper.curator.connect.CuratorConnector;
import com.alphawang.zookeeper.primary.connect.ZKWatcher;

public class ZkCuratorWatcherTest {

    public static void main(String[] args) throws Exception {
        CuratorConnector curatorConnector = new CuratorConnector();
        String nodePath = "/imooc/curator";

        // watcher 事件  当使用usingWatcher的时候，监听只会触发一次，监听完毕后就销毁
        byte[] data1 = curatorConnector.getCuratorFramework()
            .getData()
            .usingWatcher(new ZKWatcher())
            .forPath(nodePath);

        byte[] data2 = curatorConnector.getCuratorFramework()
            .getData()
            .usingWatcher(new ZkCuratorWatcher())
            .forPath(nodePath);
    }
}
