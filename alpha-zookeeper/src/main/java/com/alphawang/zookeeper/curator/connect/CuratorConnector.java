package com.alphawang.zookeeper.curator.connect;

import com.alphawang.zookeeper.Constants;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

@Slf4j
public class CuratorConnector {
    
    @Getter
    private CuratorFramework curatorFramework;
    
    public CuratorConnector() {
        RetryPolicy retryPolicy = getRetryPolicy();

        /**
         * Curator遵循Fluent设计风格
         */
        curatorFramework = CuratorFrameworkFactory.builder()
            .connectString(Constants.LOCAL_ZK_SERVER)
            .sessionTimeoutMs(10000)
            .retryPolicy(retryPolicy)
            .namespace("curator_namespace")   // 创建包含隔离命名空间的会话 
            .build();
        
        log.warn("Connected Curator {}. Detail: {}", Constants.LOCAL_ZK_SERVER, curatorFramework);
        
        curatorFramework.start();
    }

    public void close() {
        if (curatorFramework != null) {
            this.curatorFramework.close();
        }
    }
    
    private static RetryPolicy getRetryPolicy() {
        /**
         * @param baseSleepTimeMs initial amount of time to wait between retries
         * @param maxRetries max number of times to retry
         * @param maxSleepMs max time in ms to sleep on each retry
         */ 
        // return new ExponentialBackoffRetry(1000, 5);

        /**
         * Retry policy that retries a max number of times
         */
        return new RetryNTimes(3, 5000);

        /**
         * new RetryOneTime(5000) == new RetryNTimes(1, 5000) 
         */
        // return new RetryOneTime(5000);
    }
    
}
