package com.alphawang.zookeeper.primary.operate;

import com.alphawang.zookeeper.primary.connect.ZKConnector;
import com.alphawang.zookeeper.primary.connect.ZKWatcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.List;

@Slf4j
public class ZkChildrenNoteOperator implements Watcher {

    public static void main(String[] args) throws KeeperException, InterruptedException {
        ZKConnector connector = new ZKConnector();
        
        List<String> strChildList = connector.getZooKeeper().getChildren("/imooc", true);
        log.info("get Children for /imooc (watch=true): {}", strChildList);


        List<String> strChildList2 = connector.getZooKeeper().getChildren("/imooc", new ZKWatcher());
        log.info("get Children for /imooc (new Watcher): {}", strChildList2);
            
        // wait for children change.
        Thread.sleep(10000);
        connector.close();    
    }

    //TODO 为什么没有执行 ?
    @Override 
    public void process(WatchedEvent event) {
        log.info("[watch=true] >>> Received WatchedEvent {}", event);
    }
}
