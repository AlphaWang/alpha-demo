package com.alphawang.zookeeper.curator.acl;

import com.alphawang.zookeeper.curator.connect.CuratorConnector;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CuratorAcl {

    public static void main(String[] args) throws Exception {
        CuratorConnector curatorConnector = new CuratorConnector();
        String nodePath = "/imooc/curator/acl";

        List<ACL> acls = new ArrayList<>();
        Id idImooc1 = new Id("digest", DigestAuthenticationProvider.generateDigest("imooc1:123456"));
        Id idImooc2 = new Id("digest", DigestAuthenticationProvider.generateDigest("imooc2:123456"));
        acls.add(new ACL(ZooDefs.Perms.ALL, idImooc1));
        acls.add(new ACL(ZooDefs.Perms.READ, idImooc2));
        acls.add(new ACL(ZooDefs.Perms.DELETE | ZooDefs.Perms.CREATE, idImooc2));

        String result = curatorConnector.getCuratorFramework()
            .create()
            .creatingParentsIfNeeded()
            .withMode(CreateMode.PERSISTENT)
            .withACL(acls)
            .forPath(nodePath);
        log.warn("--- create node {}, result {}", nodePath, result);

        curatorConnector.close();

    }
}
