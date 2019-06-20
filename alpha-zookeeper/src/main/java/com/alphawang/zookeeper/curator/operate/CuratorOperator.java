package com.alphawang.zookeeper.curator.operate;

import com.alphawang.zookeeper.curator.connect.CuratorConnector;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.TimeUnit;

@Slf4j
public class CuratorOperator {

    public static void main(String[] args) throws Exception {
        CuratorConnector curatorConnector = new CuratorConnector();
        boolean isZkCuratorStarted = curatorConnector.getCuratorFramework().isStarted();

        log.info("Curator Started? {} ", isZkCuratorStarted);

        String nodePath = "/imooc/curator";
        String data = "curator-data";
        log.info("---- 1. creating node {}", nodePath);
        curatorConnector.getCuratorFramework()
            .create()
            .creatingParentsIfNeeded()
            .withMode(CreateMode.PERSISTENT)
            .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
            .forPath(nodePath, data.getBytes());

        Stat stat = new Stat();
        byte[] result = curatorConnector.getCuratorFramework()
            .getData()
            .storingStatIn(stat)
            .forPath(nodePath);
        log.info("---- 2. get data {} = {}, version = {}", nodePath, new String(result), stat.getVersion());

        data = "curator-data2";
        log.info("---- 3. setting data {} = {}", nodePath, data);

        curatorConnector.getCuratorFramework()
            .setData()
            .withVersion(0)
            .forPath(nodePath, data.getBytes());

        Stat statExists = curatorConnector.getCuratorFramework()
            .checkExists()
            .forPath(nodePath);
        log.info("---- 4. check exists for node {}", nodePath, statExists);

        log.info("---- 4. deleting node {}", nodePath);

        curatorConnector.getCuratorFramework()
            .delete()
            .guaranteed()
            .deletingChildrenIfNeeded()
            .withVersion(0)
            .forPath(nodePath);

        statExists = curatorConnector.getCuratorFramework()
            .checkExists()
            .forPath(nodePath);
        log.info("---- 4. check exists for node {}", nodePath, statExists);

        TimeUnit.SECONDS.sleep(10);
        curatorConnector.close();
    }
}
