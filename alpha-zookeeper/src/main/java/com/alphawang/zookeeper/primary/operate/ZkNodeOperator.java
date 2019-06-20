package com.alphawang.zookeeper.primary.operate;

import com.alphawang.zookeeper.Constants;
import com.alphawang.zookeeper.primary.callback.ZkStatCallback;
import com.alphawang.zookeeper.primary.callback.ZkVoidCallback;
import com.alphawang.zookeeper.primary.connect.ZKWatcher;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

@Slf4j
@Data
public class ZkNodeOperator {

    private ZooKeeper zooKeeper;

    private static final int timeout = 50000000;

    public ZkNodeOperator(String server) {
        try {
            zooKeeper = new ZooKeeper(server, timeout, new ZKWatcher());
        } catch (IOException e) {
            log.error("Failed to connect zk server.", e);
            if (zooKeeper != null) {
                try {
                    zooKeeper.close();
                } catch (InterruptedException e1) {
                    log.error("Failed to close zk connection.", e);
                }
            }
        }
    }

    /**
     * acl：控制权限策略
     *      Ids.OPEN_ACL_UNSAFE --> world:anyone:cdrwa
     *      CREATOR_ALL_ACL --> auth:user:password:cdrwa
     *
     * createMode：节点类型, 是一个枚举
     * 		PERSISTENT：持久节点
     * 		PERSISTENT_SEQUENTIAL：持久顺序节点
     * 		EPHEMERAL：临时节点
     * 		EPHEMERAL_SEQUENTIAL：临时顺序节点
     * @param path
     * @param data
     * @param acls
     */
    public void createZKNode(String path, byte[] data, List<ACL> acls) {
        try {
            log.info("--- try to create node {}", path);
            String result = zooKeeper.create(path, data, acls, CreateMode.EPHEMERAL);
            log.info("Created node: {}, result: {}", path, result);
            //            Thread.sleep(2000);
        } catch (KeeperException | InterruptedException e) {
            log.error("Failed to create node: {}", path, e);
        }
    }

    public void setZKNode(String path, byte[] data, int version) {
        try {
            log.info("--- try to set node {} -> {}:{}", path, new String(data), version);
            Stat stat = zooKeeper.setData(path, data, version);
            log.info("Set node: {}; result: {}; version: {}", path, stat, stat.getVersion());
            //            Thread.sleep(2000);
        } catch (KeeperException | InterruptedException e) {
            log.error("Failed to set node: {}", path, e);
        }
    }

    public void setZKNodeAsync(String path, byte[] data, int version) {
        //        try {
        log.info("--- try to async set node {} -> {}:{}", path, new String(data), version);
        zooKeeper.setData(path, data, version, new ZkStatCallback(), "Test Ctx");
        log.info("Set node async: {}.", path);
        //            Thread.sleep(2000);
        //        } catch (InterruptedException e) {
        //            log.error("Failed to set node async: {}", path, e);
        //        }
    }

    public void deleteZKNode(String path, int version) {
        try {
            log.info("--- try to delete node {} :{}", path, version);
            zooKeeper.delete(path, version);
            log.info("Delete node: {} {}", path, version);
            //            Thread.sleep(2000);
        } catch (KeeperException | InterruptedException e) {
            log.error("Failed to delete node: {}", path, e);
        }
    }

    /**
     * 推荐使用回调方式删除，否则得不到通知。
     * @param path
     * @param version
     */
    public void deleteZKNodeAsync(String path, int version) {
        //        try {
        log.info("--- try to async delete node {} :{}", path, version);
        zooKeeper.delete(path, version, new ZkVoidCallback(), "TEST Ctx delete");
        log.info("Delete node async: {} {}", path, version);
        //            Thread.sleep(2000);
        //        } catch (InterruptedException e) {
        //            log.error("Failed to delete node async: {}", path, e);
        //        }
    }

    public void readZkNode(String path) {
        Stat stat = new Stat();
        try {
            byte[] data = zooKeeper.getData(path, false, stat);
            log.info("==== get data {}: value={}, version={}", path, new String(data), stat.getVersion());
        } catch (KeeperException | InterruptedException e) {
            log.error("==== Failed to read data {}", path, e);
        }
    }

    public static void main(String[] args) {
        ZkNodeOperator operator = new ZkNodeOperator(Constants.LOCAL_ZK_SERVER);

        operator.createZKNode("/testnode", "testnode-data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE);
        operator.readZkNode("/testnode");

        operator.setZKNode("/testnode", "testnode-data-2".getBytes(), 0);
        operator.readZkNode("/testnode");

        operator.setZKNode("/testnode", "testnode-data-3".getBytes(), 0);
        operator.readZkNode("/testnode");

        operator.setZKNodeAsync("/testnode", "testnode-data-4".getBytes(), 1);
        operator.readZkNode("/testnode");

        operator.createZKNode("/testdelete", "testnode-data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE);
        operator.readZkNode("/testdelete");

        operator.deleteZKNode("/testdelete", 1);
        operator.readZkNode("/testdelete");

        operator.deleteZKNode("/testdelete", 0);
        operator.readZkNode("/testdelete");

        operator.createZKNode("/testdelete2", "testnode-data2".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE);
        operator.readZkNode("/testdelete2");

        operator.deleteZKNodeAsync("/testdelete2", 1);
        operator.readZkNode("/testdelete2");

        operator.deleteZKNodeAsync("/testdelete2", 0);
        operator.readZkNode("/testdelete2");
    }

}
