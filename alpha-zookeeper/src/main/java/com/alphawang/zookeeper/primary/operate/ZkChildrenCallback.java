package com.alphawang.zookeeper.primary.operate;

import com.alphawang.zookeeper.primary.connect.ZKConnector;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.data.Stat;

import java.util.List;

@Slf4j
public class ZkChildrenCallback {

    public static void main(String[] args) throws InterruptedException {
        ZKConnector connector = new ZKConnector();
        log.info("Started.");

        // 异步调用
        String ctx = "{'callback':'ChildrenCallback'}";
        connector.getZooKeeper().getChildren("/imooc", true, new ChildrenCallBack(), ctx);
        connector.getZooKeeper().getChildren("/imooc", true, new Children2CallBack(), ctx);

        Thread.sleep(5000);
        log.info("Finished.");
    }

    static class ChildrenCallBack implements AsyncCallback.ChildrenCallback {

        @Override
        public void processResult(int rc, String path, Object ctx, List<String> children) {
            log.info("[ChildrenCallback] >>> rc={}, path={}, children={}, ctx={}",
                rc, path, children, ctx);
        }
    }

    /**
     * 推荐使用。Children2Callback能获取Stat状态。
     *
     * stat=38776,38776,1530719129196,1530719129196,0,13,0,0,10,13,38837
     *
     * cZxid = 0x9778
     * ctime = Wed Jul 04 23:45:29 CST 2018
     * mZxid = 0x9778
     * mtime = Wed Jul 04 23:45:29 CST 2018
     * pZxid = 0x97b5
     * cversion = 13
     * dataVersion = 0
     * aclVersion = 0
     * ephemeralOwner = 0x0
     * dataLength = 10
     * numChildren = 13
     */
    static class Children2CallBack implements AsyncCallback.Children2Callback {

        @Override
        public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
            log.info("[Children2Callback] >>> rc={}, path={}, children={}, stat={}, ctx={}",
                rc, path, children, stat, ctx);
        }
    }
}
