package com.alphawang.jdk.file;

import lombok.Getter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Objects;
import java.util.Properties;

/**
 * Created by Alpha on 12/15/17.
 *
 * java.nio.file.WatchService 测试
 */
@Getter
public class WatchServiceTest {

    private String fileName = "application.properties";
    private ClassPathResource resource = new ClassPathResource(fileName);
    private Properties properties;
    private WatchService watchService;

    public static void main(String[] args) {
        WatchServiceTest test = new WatchServiceTest();

        // 1. register & printLatency
        try {
            test.register();
            test.loadProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 2. watch
        Thread watchThread = new Thread(() -> {
            try {
                test.watch();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        watchThread.setDaemon(true);
        watchThread.start();

        // 3. wait
        try {
            watchThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                print("====Shutting down");
                test.getWatchService().close();
                print("====DONE");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    private void register() throws IOException {
        watchService = FileSystems.getDefault().newWatchService();

        // /build/resources/main
        String parent = resource.getFile().getParent();
        print("Register watching resource parent: " + parent);

        Paths.get(parent)
            .register(
                watchService,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE);
    }

    private void watch() throws InterruptedException, IOException {
        while (true) {
            WatchKey watchKey = watchService.take();
            print("watching..." + watchKey);
            for (WatchEvent event : watchKey.pollEvents()) {
                print("watch event: " + event.context().toString());

                if (Objects.equals(event.context().toString(), fileName)) {
                    loadProperties();
                    break;
                }
            }

            watchKey.reset();
        }
    }

    private void loadProperties() throws IOException {
        properties = PropertiesLoaderUtils.loadProperties(resource);
        print(properties);
    }

    private static void print(Object o) {
        System.out.println(o);
    }
}
