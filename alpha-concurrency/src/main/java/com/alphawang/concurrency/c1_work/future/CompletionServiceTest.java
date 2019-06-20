package com.alphawang.concurrency.c1_work.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CompletionServiceTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        CompletionService<Integer> cs = new ExecutorCompletionService<>(executorService);

        cs.submit(() -> {
            log.warn("Service 1: start.");
            sleep(500);
            log.warn("Service 1: finish.");

            return 333;
        });

        cs.submit(() -> {
            log.warn("Service 2: start.");
            sleep(900);
            log.warn("Service 2: finish.");

            return 222;
        });

        cs.submit(() -> {
            log.warn("Service 3: start.");
            sleep(700);
            log.warn("Service 3: finish.");

            return 111;
        });

        /**
         * 执行快的先输出
         */
        for (int i = 0; i < 3; i++) {
            Integer result = null;
            try {
                result = cs.take().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            log.warn("RESULT: " + result);
        }

    }

    private static void sleep(int timeout) {
        try {
            TimeUnit.MILLISECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
