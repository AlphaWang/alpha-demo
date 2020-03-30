package com.alphawang.concurrency.c1_work.future;

import static com.alphawang.concurrency.common.Printer.print;

import com.alphawang.concurrency.common.Printer;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

@Slf4j
public class CompletableFutureTest {

    /**
     * [pool-1-thread-1] - STEP 1: start async.
     * [pool-1-thread-2] - STEP 2: start async.
     * [pool-1-thread-1] - STEP 1: finish async.
     * [pool-1-thread-2] - STEP 2: finish async.
     * [ForkJoinPool.commonPool-worker-9] - STEP 3: combine
     * 
     * [main] - STEP 3 -- STEP 2
     */
    public static void main(String[] args) {
        System.out.println("Test");
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        CompletableFuture<Void> f1 = CompletableFuture.runAsync(() -> {
            print("STEP 1: start async.");
            sleep(500);
            print("STEP 1: finish async.");
        }, executorService);

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            print("STEP 2: start async.");
            sleep(800);
            print("STEP 2: finish async.");

            return "STEP 2";
        }, executorService);

        // 不指定线程池，则用 ForkJoinPool
        CompletableFuture<String> f3 = f1.thenCombineAsync(f2, new BiFunction<Void, String, String>() {
            @Override
            public String apply(Void aVoid, String s) {
                print("STEP 3: combine");
                return "STEP 3 -- " + s;
            }
        });
        
        String response = f3.join();

        print(response);

    }

    private static void sleep(int timeout) {
        try {
            TimeUnit.MILLISECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
