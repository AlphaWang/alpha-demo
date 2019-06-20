package com.alphawang.zookeeper.curator.tools;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingCluster;
import org.apache.curator.test.TestingZooKeeperServer;

@Slf4j
public class TestingClusterDemo {

    public static void main(String[] args) throws Exception {
        TestingCluster cluster = new TestingCluster(3);

        cluster.start();
        Thread.sleep(2000);

        testKillLeader(cluster);
        testGetClusterData(cluster);

        cluster.stop();

    }

    private static void testGetClusterData(TestingCluster cluster) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString(cluster.getConnectString())
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .sessionTimeoutMs(5000)
            .build();
        client.start();

        client.create().forPath("/alpha-zk-cluster", "test-data".getBytes());

        byte[] data = client.getData().forPath("/alpha-zk-cluster");
        log.warn("==== get data {}", new String(data));
    }

    private static void testKillLeader(TestingCluster cluster) throws InterruptedException {
        TestingZooKeeperServer leader = null;

        /**
         * [main] INFO  - == serverId: 1, serverState: following, data dir: /var/folders/5t/k079pcv16877_xj41pzgmdzh0000gp/T/1538921575991-0
         * [main] INFO  - == serverId: 2, serverState: following, data dir: /var/folders/5t/k079pcv16877_xj41pzgmdzh0000gp/T/1538921575994-0
         * [main] INFO  - == serverId: 3, serverState: leading, data dir: /var/folders/5t/k079pcv16877_xj41pzgmdzh0000gp/T/1538921575995-0
         */
        for (TestingZooKeeperServer zs : cluster.getServers()) {
            log.info("== serverId: {}, serverState: {}, data dir: {}",
                zs.getInstanceSpec().getServerId(),
                zs.getQuorumPeer().getServerState(),
                zs.getInstanceSpec().getDataDirectory().getAbsolutePath());

            if (zs.getQuorumPeer().getServerState().equals("leading")) {
                leader = zs;
            }
        }

        /**
         * [main] WARN  - killing leader: org.apache.curator.test.TestingZooKeeperServer@68837a77
         * [main] INFO  - Shutting down
         * [main] INFO  - Shutdown called
         * ...
         * [main] WARN  - killed leader: org.apache.curator.test.TestingZooKeeperServer@68837a77
         */
        log.warn("killing leader: {}", leader);
        leader.kill();
        log.warn("killed leader: {}", leader);

        /**
         * [main] INFO  - ++ serverId: 1, serverState: leaderelection, data dir: /var/folders/5t/k079pcv16877_xj41pzgmdzh0000gp/T/1538921575991-0
         * [main] INFO  - ++ serverId: 2, serverState: leaderelection, data dir: /var/folders/5t/k079pcv16877_xj41pzgmdzh0000gp/T/1538921575994-0
         * [main] INFO  - ++ serverId: 3, serverState: leaderelection, data dir: /var/folders/5t/k079pcv16877_xj41pzgmdzh0000gp/T/1538921575995-0
         */
        for (TestingZooKeeperServer zs : cluster.getServers()) {
            log.info("++ serverId: {}, serverState: {}, data dir: {}",
                zs.getInstanceSpec().getServerId(),
                zs.getQuorumPeer().getServerState(),
                zs.getInstanceSpec().getDataDirectory().getAbsolutePath());
        }

        /**
         *
         [main] INFO  - -- serverId: 1, serverState: following, data dir: /var/folders/5t/k079pcv16877_xj41pzgmdzh0000gp/T/1538921575991-0
         [main] INFO  - -- serverId: 2, serverState: leading, data dir: /var/folders/5t/k079pcv16877_xj41pzgmdzh0000gp/T/1538921575994-0
         [main] INFO  - -- serverId: 3, serverState: leaderelection, data dir: /var/folders/5t/k079pcv16877_xj41pzgmdzh0000gp/T/1538921575995-0
         */
        Thread.sleep(3000);
        for (TestingZooKeeperServer zs : cluster.getServers()) {
            log.info("-- serverId: {}, serverState: {}, data dir: {}",
                zs.getInstanceSpec().getServerId(),
                zs.getQuorumPeer().getServerState(),
                zs.getInstanceSpec().getDataDirectory().getAbsolutePath());
        }
    }
}
