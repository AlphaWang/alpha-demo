package com.alphawang.zookeeper.lock;

import com.alphawang.zookeeper.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

import java.util.concurrent.TimeUnit;

@Slf4j
public class DistributedLockTest {

    private static DistributedLock lock;

    public static void main(String[] args) throws InterruptedException {
        // 可通过Spring注入
        RetryPolicy retryPolicy = getRetryPolicy();
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
            .connectString(Constants.LOCAL_ZK_SERVER)
            .sessionTimeoutMs(10000)
            .retryPolicy(retryPolicy)
            .namespace("curator_namespace")
            .build();

        lock = new DistributedLock(curatorFramework);
        lock.init();

        buy(4);
    }

    private static boolean buy(int buyCount) throws InterruptedException {
        lock.getLock();

        int stock = getStock();
        if (stock < buyCount) {
            log.error("库存不足.");

            lock.releaseLock();
            return false;
        }

        boolean created = createOrder();
        if (created) {
            log.info("订单创建成功");
            lock.releaseLock();

            return true;
        } else {
            log.info("订单创建失败");
            lock.releaseLock();

            return false;
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

    private static int getStock() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        log.info("==== get stock");
        return 10;
    }

    private static boolean createOrder() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        log.info("==== create order.");

        return true;
    }
}
