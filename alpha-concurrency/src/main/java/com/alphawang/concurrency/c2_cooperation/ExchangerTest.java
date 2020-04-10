package com.alphawang.concurrency.c2_cooperation;

import com.alphawang.concurrency.common.Printer;
import java.util.concurrent.Exchanger;

public class ExchangerTest {

    /**
     * 在 exchange() 中，"两" 个线程交换数据。
     * 
     * [main] - START.
     * [Thread-0] - Thread-1 exchanging...
     * [Thread-1] - Thread-2 exchanging...
     * [main] - END.
     * [Thread-2] - Thread-3 exchanging...
     * [Thread-0] - Thread-1 exchanged: Thread-3
     * [Thread-2] - Thread-3 exchanged: Thread-1
     */
    public static void main(String[] args) {

        Exchanger<String> exchanger = new Exchanger<>();

        Printer.print("START.");
        
        new Thread(() -> {
            try {
                Printer.print("Thread-1 exchanging...");
                Thread.sleep(100);
                String exchange = exchanger.exchange("Thread-1");
                Printer.print("Thread-1 exchanged: " + exchange);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                Printer.print("Thread-2 exchanging...");
                Thread.sleep(200);
                String exchange = exchanger.exchange("Thread-2");
                Printer.print("Thread-2 exchanged: " + exchange);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                Printer.print("Thread-3 exchanging...");
                Thread.sleep(100);
                String exchange = exchanger.exchange("Thread-3");
                Printer.print("Thread-3 exchanged: " + exchange);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        
        Printer.print("END.");
    }
    

}
