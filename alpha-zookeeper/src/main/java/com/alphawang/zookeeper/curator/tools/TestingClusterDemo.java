package com.alphawang.zookeeper.curator.tools;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.test.TestingCluster;
import org.apache.curator.test.TestingZooKeeperServer;

@Slf4j
public class TestingClusterDemo {

    public static void main(String[] args) throws Exception {
        TestingCluster cluster = new TestingCluster(3);
        
        cluster.start();
        Thread.sleep(2000);
        TestingZooKeeperServer leader = null;

        /**
         * 23:44:20.025 [main] INFO  c.a.z.c.t.TestingClusterDemo - == serverId: 1, serverState: following, data dir: /var/folders/5t/k079pcv16877_xj41pzgmdzh0000gp/T/1538840657884-0
         * 23:44:20.025 [main] INFO  c.a.z.c.t.TestingClusterDemo - == serverId: 2, serverState: following, data dir: /var/folders/5t/k079pcv16877_xj41pzgmdzh0000gp/T/1538840657888-0
         * 23:44:20.025 [main] INFO  c.a.z.c.t.TestingClusterDemo - == serverId: 3, serverState: leading, data dir: /var/folders/5t/k079pcv16877_xj41pzgmdzh0000gp/T/1538840657888-1
         * 23:44:20.025 [main] WARN  c.a.z.c.t.TestingClusterDemo - killing leader: org.apache.curator.test.TestingZooKeeperServer@6be46e8f
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
        
        
        log.warn("killing leader: {}", leader);
        leader.kill();
        log.warn("killed leader: {}", leader);

        /**
         * 23:44:20.056 [main] INFO  c.a.z.c.t.TestingClusterDemo - ++ serverId: 1, serverState: leaderelection, data dir: /var/folders/5t/k079pcv16877_xj41pzgmdzh0000gp/T/1538840657884-0
         * 23:44:20.057 [main] INFO  c.a.z.c.t.TestingClusterDemo - ++ serverId: 2, serverState: leaderelection, data dir: /var/folders/5t/k079pcv16877_xj41pzgmdzh0000gp/T/1538840657888-0
         * 23:44:20.057 [main] INFO  c.a.z.c.t.TestingClusterDemo - ++ serverId: 3, serverState: leaderelection, data dir: /var/folders/5t/k079pcv16877_xj41pzgmdzh0000gp/T/1538840657888-1
         */
        for (TestingZooKeeperServer zs : cluster.getServers()) {
            log.info("++ serverId: {}, serverState: {}, data dir: {}",
                zs.getInstanceSpec().getServerId(),
                zs.getQuorumPeer().getServerState(),
                zs.getInstanceSpec().getDataDirectory().getAbsolutePath());
        }

        /**
         * 23:46:04.343 [main] INFO  c.a.z.c.t.TestingClusterDemo - -- serverId: 1, serverState: following, data dir: /var/folders/5t/k079pcv16877_xj41pzgmdzh0000gp/T/1538840759188-0
         * 23:46:04.343 [main] INFO  c.a.z.c.t.TestingClusterDemo - -- serverId: 2, serverState: leading, data dir: /var/folders/5t/k079pcv16877_xj41pzgmdzh0000gp/T/1538840759192-0
         * 23:46:04.344 [main] INFO  c.a.z.c.t.TestingClusterDemo - -- serverId: 3, serverState: leaderelection, data dir: /var/folders/5t/k079pcv16877_xj41pzgmdzh0000gp/T/1538840759192-1
         */
        Thread.sleep(3000);
        for (TestingZooKeeperServer zs : cluster.getServers()) {
            log.info("-- serverId: {}, serverState: {}, data dir: {}",
                zs.getInstanceSpec().getServerId(),
                zs.getQuorumPeer().getServerState(),
                zs.getInstanceSpec().getDataDirectory().getAbsolutePath());
        }
        
        cluster.stop();
        
    }
}
