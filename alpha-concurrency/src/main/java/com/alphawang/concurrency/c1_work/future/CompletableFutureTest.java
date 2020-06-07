package com.alphawang.concurrency.c1_work.future;

import static com.alphawang.concurrency.common.Printer.print;

import com.alphawang.concurrency.common.Printer;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        testParallel();
        testParallel2();
        testParallel3();
//        testCombine();
    }
    
    private static void testCombine() {
        System.out.println("------testCombine");
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

    private static void testParallel() throws ExecutionException, InterruptedException {
        System.out.println("------testParallel");
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            print("STEP 1: start async.");
            sleep(1000);
            print("STEP 1: finish async.");
            
            return "STEP 1";
        }, executorService);

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            print("STEP 2: start async.");
            sleep(1000);
            print("STEP 2: finish async.");

            return "STEP 2";
        }, executorService);

        print(f1.get());
        print(f2.get());
    }


    private static void testParallel2() throws ExecutionException, InterruptedException {
        System.out.println("------testParallel2");
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            print("STEP 1: start async.");
            sleep(1000);
            print("STEP 1: finish async.");

            return "STEP 1";
        }, executorService)
        .supplyAsync(() -> {
            print("STEP 2: start async.");
            sleep(1000);
            print("STEP 2: finish async.");

            return "STEP 2";
        }, executorService);

        print(f1.get());
    }

   
    private static void testParallel3() throws ExecutionException, InterruptedException {
        System.out.println("------testParallel3");
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        List<Integer> input = Lists.newArrayList(1, 2, 3, 4);

        /**
         * TODO: 实际是串行!
         */
        List<String> res = input.stream().map(i -> CompletableFuture.supplyAsync(() -> {
            print("start async. " + i);
            sleep(1000);
            print("finish async. " + i);

            return "STEP " + i;
        }))
        .map(CompletableFuture::join).collect(Collectors.toList());
        print(res);

        /**
         * 这样才是并行
         */
        List<CompletableFuture<String>> res2 = input.stream().map(i -> CompletableFuture.supplyAsync(() -> {
            print("start async2. " + i);
            sleep(1000);
            print("finish async2. " + i);

            return "STEP " + i;
        }))
        .collect(Collectors.toList());
        
        print(res2.stream().map(CompletableFuture::join).collect(Collectors.toList()));
    }
    
    private static void sleep(int timeout) {
        try {
            TimeUnit.MILLISECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
