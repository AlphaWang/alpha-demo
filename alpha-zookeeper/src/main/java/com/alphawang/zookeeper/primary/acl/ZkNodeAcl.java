package com.alphawang.zookeeper.primary.acl;

import com.alphawang.zookeeper.primary.connect.ZKConnector;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * zkCli > getAcl path
 *
 * setAcl /imooc/acl world:anyone:cdrwa
 * setAcl /imooc/acl auth:imooc:imooc:cdrwa   //设置之前需要 addauth; 此命令可简写为setAcl /imooc/acl auth::cdrwa 
 * setAcl /imooc/acl digest:imooc:XXX:cdrwa
 * setAcl /imooc/acl ip:192.168.1.1:cdrwa
 *
 * Clean zk data before execute:
 * > addauth digest imooc1:123456
 * > rmr /imooc/acl
 */
@Slf4j
public class ZkNodeAcl {

    private static final String LOCAL_IP = "127.0.0.1";
    private static final String REMOTE_IP = "192.168.1.10";

    public static void main(String[] args) throws InterruptedException, KeeperException, NoSuchAlgorithmException {
        ZKConnector connector = new ZKConnector();

        /**
         * 1. acl 任何人都可以访问
         *
         * zkCli (setAcl scheme:id:permissions) 
         *
         * > setAcl /imooc/acl world:anyone:cdrwa
         *
         *
         * zkCli (getAcl) 
         *
         * > getAcl /imooc/acl
         *   'world,'anyone
         *   : cdrwa
         */
        log.info("1. create Node {} with OPEN_ACL_UNSAFE: {}", "/imooc/acl", ZooDefs.Ids.OPEN_ACL_UNSAFE);
        connector.getZooKeeper().create("/imooc/acl", "test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        /**
         * 2. 自定义用户认证访问
         *
         * zkCli (setAcl scheme:id:permissions) 
         *
         * > setAcl /imooc/acl/digest digest:imooc1:ee8R/pr2P4sGnQYNGyw2M5S5IMU=:cdrwa
         * > addAuth digest imooc1:123456         
         *
         * zkCli (getAcl)  
         *
         * > getAcl /imooc/acl/digest
         *  'digest,'imooc1:ee8R/pr2P4sGnQYNGyw2M5S5IMU=
         *  : cdrwa
         *   'digest,'imooc2:eBdFG0gQw0YArfEFDCRP3LzIp6k=
         *  : r
         *  'digest,'imooc2:eBdFG0gQw0YArfEFDCRP3LzIp6k=
         *  : cd
         */
        String digestPath = "/imooc/acl/digest";
        List<ACL> acls = new ArrayList<ACL>();
        // TODO Q: why not create enum for scheme "digest"?
        Id id_imooc1 = new Id("digest", DigestAuthenticationProvider.generateDigest("imooc1:123456"));
        Id id_imooc2 = new Id("digest", DigestAuthenticationProvider.generateDigest("imooc2:123456"));
        acls.add(new ACL(ZooDefs.Perms.ALL, id_imooc1));
        acls.add(new ACL(ZooDefs.Perms.READ, id_imooc2));
        acls.add(new ACL(ZooDefs.Perms.DELETE | ZooDefs.Perms.CREATE, id_imooc2));

        log.info("2.1 create Node {} with digest ACLs: {}", digestPath, acls);
        connector.getZooKeeper().create(digestPath, "testdigest".getBytes(), acls, CreateMode.PERSISTENT);

        // 注册过的用户必须通过addAuthInfo才能操作节点，参考命令行 addauth
        log.info("2.2 create child Node with digest auth {}.", "imooc1:123456");
        connector.getZooKeeper().addAuthInfo("digest", "imooc1:123456".getBytes());
        connector.getZooKeeper().create(digestPath + "/childtest", "childtest".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);

        Stat stat = new Stat();
        byte[] data = connector.getZooKeeper().getData(digestPath, false, stat);
        log.info("2.3 get data for {} : {}", digestPath, new String(data));
        connector.getZooKeeper().setData(digestPath, "now".getBytes(), 0);

        /**
         *  3. ip ACL.
         *
         *  > setAcl path ip:192.168.1.1:cdrwa
         */
        String pathForLocalIp = "/imooc/acl/ip-local";
        String pathForRemoteIp = "/imooc/acl/ip-remote";

        List<ACL> aclsIP = new ArrayList<ACL>();
        Id id_ip1 = new Id("ip", LOCAL_IP);  //REMOTE_IP
        aclsIP.add(new ACL(ZooDefs.Perms.ALL, id_ip1));

        log.info("3.1 create Node {} with ip ACLs: {}", pathForLocalIp, aclsIP);
        connector.getZooKeeper().create(pathForLocalIp, "local-ip-data".getBytes(), aclsIP, CreateMode.PERSISTENT);

        data = connector.getZooKeeper().getData(pathForLocalIp, false, stat);
        log.info("3.2 get data for {} : {}", pathForLocalIp, new String(data));

        List<ACL> aclsIP2 = new ArrayList<ACL>();
        Id id_ip2 = new Id("ip", REMOTE_IP);
        aclsIP2.add(new ACL(ZooDefs.Perms.ALL, id_ip2));

        log.info("3.2 create Node {} with ip ACLs: {}", pathForRemoteIp, aclsIP);
        connector.getZooKeeper().create(pathForRemoteIp, "remote-ip-data".getBytes(), aclsIP2, CreateMode.PERSISTENT);

        data = connector.getZooKeeper().getData(pathForRemoteIp, false, stat);
        log.info("3.2 get data for {} : {}", pathForRemoteIp, new String(data));

    }

}
