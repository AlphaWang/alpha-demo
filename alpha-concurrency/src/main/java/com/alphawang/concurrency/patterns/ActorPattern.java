package com.alphawang.concurrency.patterns;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActorPattern {


    public static void main(String[] args) throws InterruptedException {
        ActorSystem actorSystem = ActorSystem.create("ActorDemo");

        ExecutorService es = Executors.newFixedThreadPool(4);

        ActorRef counterActor = actorSystem.actorOf(Props.create(CounterActor.class));
        
        for (int i = 0; i < 4; i++) {
            es.execute(() -> {
                for (int j = 0; j < 100000; j++) {
                    counterActor.tell(1, ActorRef.noSender());
                }
            });
        }
        
        es.shutdown();
        
        Thread.sleep(2000);
        
        counterActor.tell("", ActorRef.noSender());
        
//        actorSystem.shutdown();
        
    }

    /**
     * 无需锁，即可实现线程安全的累加器
     */
    static class CounterActor extends UntypedActor {
        
        private int counter = 0;

        @Override 
        public void onReceive(Object message) throws Throwable {
            if (message instanceof Number) {
                counter += ((Number) message).intValue();
            } else {
                System.out.print(counter);
            }
        }
    }
    
}
