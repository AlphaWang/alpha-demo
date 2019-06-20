package com.alphawang.concurrency.c1_work.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

@Slf4j
public class CompletableFutureTest {

    public static void main(String[] args) {
        log.error("Test");
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        CompletableFuture<Void> f1 = CompletableFuture.runAsync(() -> {
            log.warn("STEP 1: start async.");
            sleep(500);
            log.warn("STEP 1: finish async.");
        }, executorService);

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            log.warn("STEP 2: start async.");
            sleep(800);
            log.warn("STEP 2: finish async.");

            return "STEP 2";
        }, executorService);

        CompletableFuture<String> f3 = f1.thenCombineAsync(f2, new BiFunction<Void, String, String>() {
            @Override
            public String apply(Void aVoid, String s) {
                log.warn("STEP 3: combine");
                return "STEP 3 -- " + s;
            }
        });

        log.warn("MAIN: " + f3.join());

    }

    private static void sleep(int timeout) {
        try {
            TimeUnit.MILLISECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
