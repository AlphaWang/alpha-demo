package com.alphawang.concurrency.c1_work.future;

import static com.alphawang.concurrency.common.Printer.print;

import com.google.common.collect.Lists;
import com.pivovarit.collectors.ParallelCollectors;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ParallelCollectorsTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        testParallelCompletableFuture();
        testParallelCollectors();
    }

    private static void testParallelCollectors() throws ExecutionException, InterruptedException {
        System.out.println("------testParallel");
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        List<Integer> input = Lists.newArrayList(1, 2, 3, 4);
        CompletableFuture<List<String>> collect = input.stream().collect(ParallelCollectors.parallelToList(id -> {
            print("start ParallelCollectors-" + id);
            sleep(1000);
            print("finish ParallelCollectors-" + id);

            return "STEP " + id;
        }, executorService));

        print("start get");
        print(collect.get());
        print("finish get");
    }


    private static void testParallelCompletableFuture() throws ExecutionException, InterruptedException {
        System.out.println("------testParallel3");
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        List<Integer> input = Lists.newArrayList(1, 2, 3, 4);
        CompletableFuture[] futures = input.stream().map(id -> CompletableFuture.supplyAsync(() -> {
            print("start async. " + id);
            sleep(1000);
            print("finish async. " + id);

            return "STEP " + id;
        }, executorService)).toArray(size -> new CompletableFuture[size]);

//        CompletableFuture.anyOf(futures).join();//anyOf()为任意一个子任务线程执行完毕后返回
        CompletableFuture.allOf(futures).join();//allOf()为等待所有子任务线程全部执行完毕后返回

//        print(res);
    }

    private static void sleep(int timeout) {
        try {
            TimeUnit.MILLISECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
