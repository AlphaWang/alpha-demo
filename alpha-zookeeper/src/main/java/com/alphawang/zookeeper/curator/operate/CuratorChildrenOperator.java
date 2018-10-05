package com.alphawang.zookeeper.curator.operate;

import com.alphawang.zookeeper.curator.connect.CuratorConnector;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class CuratorChildrenOperator {

    public static void main(String[] args) throws Exception {
        String nodePath = "/imooc/curator";
        
        CuratorConnector curatorConnector = new CuratorConnector();
        
        List<String> childNodes = curatorConnector.getCuratorFramework()
            .getChildren()
            .forPath(nodePath);
        log.info("--- get children for {} = {}", nodePath, childNodes);
    }
}
