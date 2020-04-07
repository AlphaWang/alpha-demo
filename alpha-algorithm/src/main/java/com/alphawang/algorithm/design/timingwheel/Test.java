package com.alphawang.algorithm.design.timingwheel;

import java.util.concurrent.CountDownLatch;
import javax.sound.midi.Soundbank;

public class Test {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(5);
        
        Timer timer = new Timer();
        
        for (int i = 1; i <= 5; i++) {
            TimerTask task = new TimerTask(i, () -> {
                latch.countDown();

                int runCount = addRun();
                System.out.println("++++ Running " + runCount);
            });

            timer.addTask(task);

            System.out.println("---- Add " + i);

            inCount++;
        }
        
//        TimerTask task = new TimerTask(5000, () -> {
//            latch.countDown();
//            
//            int runCount = addRun();
//            System.out.println("---- Running2 : " + runCount);
//        });
//        timer.addTask(task);

        try {
            latch.await();
            System.out.println("inCount = " + inCount);
            System.out.println("runCount = " + runCount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
    
    static int inCount = 0;
    static int runCount = 0;
    
    public synchronized static int addRun() {
        runCount ++;
        return runCount;
    }

}
