package com.alphawang.concurrency.c2_cooperation;

import com.alphawang.concurrency.common.Printer;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import lombok.SneakyThrows;
import scala.concurrent.java8.FuturesConvertersImpl.P;

public class BarrierTest {

    /**
     * [Thread-1] - thread-2 waiting
     * [Thread-0] - thread-1 waiting
     * [main] - Main thread waiting
     * [Thread-0] - ---- Action.
     * [Thread-0] - thread-1 waiting done.
     * [Thread-1] - thread-2 waiting done.
     */
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(3, new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.sleep(100);
                Printer.print("---- Action.");
            }
        });
        
        new Thread(() -> {
            Printer.print("thread-1 waiting. waiting number=" + barrier.getNumberWaiting());
            
            try {
                Thread.sleep(200);
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            } 
            Printer.print("thread-1 waiting done. waiting number=" + barrier.getNumberWaiting());
        }).start();

        new Thread(() -> {
            Printer.print("thread-2 waiting. waiting number=" + barrier.getNumberWaiting());
            try {
                Thread.sleep(100);
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Printer.print("thread-2 waiting done.  waiting number=" + barrier.getNumberWaiting());
        }).start();

        Printer.print("Main thread waiting.  waiting number=" + barrier.getNumberWaiting());
        try {
            barrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Printer.print("Main thread waiting done.  waiting number=" + barrier.getNumberWaiting());
    }

}
