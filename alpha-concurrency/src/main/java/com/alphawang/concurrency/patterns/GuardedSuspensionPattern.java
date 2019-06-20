package com.alphawang.concurrency.patterns;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

@Slf4j
public class GuardedSuspensionPattern {

    /**
     * 保护性暂停。将异步转换为同步。
     */
    @Data
    static class GuardedObject<T> {
        private T obj;
        private final Lock lock = new ReentrantLock();
        private final Condition done = lock.newCondition();

        private final int timeout = 2;
        private final static Map<Object, GuardedObject> gos = new ConcurrentHashMap<>();

        public static <K> GuardedObject create(K key) {
            GuardedObject go = new GuardedObject();
            gos.put(key, go);

            return go;
        }

        public static <K, T> void fireEvent(K key, T obj) {
            GuardedObject go = gos.remove(key);
            if (go != null) {
                go.onChange(obj);
            }
        }

        public T get(Predicate<T> predicate) {
            lock.lock();

            try {
                log.info("[await] GuardedObject get: await()");
                while (!predicate.test(obj)) {
                    done.await(timeout, TimeUnit.SECONDS);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

            log.info("[await-done] GuardedObject get: await() finished!!!!!");
            return obj;
        }

        public void onChange(T obj) {
            lock.lock();

            try {
                log.info("[signalAll] GuardedObject onChange: signalAll()");
                this.obj = obj;
                done.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

    /////////////////// 测试代码
    public static void main(String[] args) {
        GuardedSuspensionPattern mock = new GuardedSuspensionPattern();

        new Thread(() -> {
            String request = "mock request param";

            log.info("handleWebReq request: {}", request);
            String response = mock.handleWebReq(request);
            log.info("handleWebReq response: {} !!!!!", response);
        }).start();

        new Thread(() -> {
            sleep(1);
            log.info("onMessage: mock received response");
            mock.onMessage(0, "Async response");
        }).start();

        sleep(5);
    }

    /////////////////// 处理浏览器请求
    private String handleWebReq(String req) {
        int id = 0; //generateId();

        /////////////////// 发送到异步 MQ
        String msg = "MSG_" + req;
        //send(msg);
        log.info("sending request {}", msg);

        /////////////////// 等待异步响应返回
        GuardedObject<String> go = GuardedObject.create(id);
        String response = go.get(t -> t != null);

        return response;
    }

    /////////////////// 异步响应返回后 回调函数
    private void onMessage(int id, String msg) {
        GuardedObject.fireEvent(id, msg);
    }

    private static void sleep(int timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
