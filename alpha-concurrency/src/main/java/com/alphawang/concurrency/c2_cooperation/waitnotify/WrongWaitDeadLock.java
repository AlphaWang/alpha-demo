package com.alphawang.concurrency.c2_cooperation.waitnotify;

import java.util.LinkedList;

public class WrongWaitDeadLock {

    interface Stack {
        void push(Object x);
        Object pop() throws Exception;
    }

    static class WrongWaitStack implements Stack {

        LinkedList list = new LinkedList();

        public synchronized void push(Object x) {
            System.out.println("++ enter push()");
            synchronized (list) {
                System.out.println("++ synced list");

                list.addLast(x);
                System.out.println("++ added element");

                notify();
                System.out.println("++ notified");
            }
        }

        public synchronized Object pop() throws InterruptedException {
            System.out.println("-- enter pop()");
            synchronized (list) {
                System.out.println("-- synced list");

                if (list.size() <= 0) {
                    System.out.println("-- start wait, size=" + list.size());
                    wait(); // release stack.lock, but not release list.lock
                    System.out.println("-- finished wait, size=" + list.size());
                }

                Object o = list.removeLast();
                System.out.println("-- pop " + o);

                return o;
            }
        }
    }

    static class CorrectWaitStack implements Stack {

        LinkedList list = new LinkedList();

        public synchronized void push(Object x) {
            System.out.println("++ enter push()");

            list.addLast(x);
            System.out.println("++ added element");

            notify();
            System.out.println("++ notified");
        }

        public synchronized Object pop() throws InterruptedException {
            System.out.println("-- enter pop()");

            if (list.size() <= 0) {
                System.out.println("-- start wait, size=" + list.size());
                wait();
                System.out.println("-- finished wait, size=" + list.size());
            }

            Object o = list.removeLast();
            System.out.println("-- pop " + o);

            return o;
        }
    }

    private static void testPushPop(Stack stack) throws InterruptedException {
        Thread pushThread = new Thread(() -> {
            stack.push("abc");
        }, "push-1");
        pushThread.start();

        Thread popThread = new Thread(() -> {
            try {
                Object o = stack.pop();
                System.out.println(o);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "pop-1");
        popThread.start();

        pushThread.join();
        popThread.join();

    }

    private static void testPopPush(Stack stack) throws InterruptedException {
        Thread popThread = new Thread(() -> {
            try {
                Object o = stack.pop();
                System.out.println(o);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "pop-2");
        popThread.start();

        Thread pushThread = new Thread(() -> {
            stack.push("abc");
        }, "push-2");
        pushThread.start();

        popThread.join();
        pushThread.join();
    }


    public static void main(String[] args) throws InterruptedException {
        /**
         * ++ enter push()
         * ++ added element
         * ++ notified
         * -- enter pop()
         * -- pop abc
         */
        testPushPop(new CorrectWaitStack());
        System.out.println("-------------");

        /**
         * -- enter pop()
         * -- start wait, size=0
         * ++ enter push()
         * ++ added element
         * ++ notified
         * -- finished wait, size=1
         * -- pop abc
         */
        testPopPush(new CorrectWaitStack());
        System.out.println("-------------");

        /**
         * ++ enter push()
         * ++ synced list
         * ++ added element
         * ++ notified
         * -- enter pop()
         * -- synced list
         * -- pop abc
         */
        testPushPop(new WrongWaitStack());

        /**
         * -- enter pop()
         * -- synced list
         * -- start wait, size=0
         * ++ enter push()
         *
         * 此时push() 等待 list.lock
         *
         * "push-1" #20 prio=5 os_prio=31 tid=0x00007fe052884800 nid=0x6403 waiting for monitor entry [0x000070000dc30000]
         *    java.lang.Thread.State: BLOCKED (on object monitor)
         * 	at com.alphawang.concurrency.c2_cooperation.waitnotify.WrongWaitDeadLock$WrongWaitStack.push(WrongWaitDeadLock.java:18)
         * 	- waiting to lock <0x00000007157d4330> (a java.util.LinkedList) >>>> TODO DEAD LOCK
         * 	- locked <0x00000007157d3d60> (a com.alphawang.concurrency.c2_cooperation.waitnotify.WrongWaitDeadLock$WrongWaitStack)
         * 	at com.alphawang.concurrency.c2_cooperation.waitnotify.WrongWaitDeadLock.lambda$testPopPush$3(WrongWaitDeadLock.java:77)
         * 	at com.alphawang.concurrency.c2_cooperation.waitnotify.WrongWaitDeadLock$$Lambda$2/2084435065.run(Unknown Source)
         * 	at java.lang.Thread.run(Thread.java:745)
         *
         * "pop-1" #19 prio=5 os_prio=31 tid=0x00007fe061099800 nid=0x6203 in Object.wait() [0x000070000db2d000]
         *    java.lang.Thread.State: WAITING (on object monitor)
         * 	at java.lang.Object.wait(Native Method)
         * 	- waiting on <0x00000007157d3d60> (a com.alphawang.concurrency.c2_cooperation.waitnotify.WrongWaitDeadLock$WrongWaitStack)
         * 	at java.lang.Object.wait(Object.java:502)
         * 	at com.alphawang.concurrency.c2_cooperation.waitnotify.WrongWaitDeadLock$WrongWaitStack.pop(WrongWaitDeadLock.java:35)
         * 	- locked <0x00000007157d4330> (a java.util.LinkedList) >>>> TODO DEAD LOCK
         * 	- locked <0x00000007157d3d60> (a com.alphawang.concurrency.c2_cooperation.waitnotify.WrongWaitDeadLock$WrongWaitStack)
         * 	at com.alphawang.concurrency.c2_cooperation.waitnotify.WrongWaitDeadLock.lambda$testPopPush$2(WrongWaitDeadLock.java:68)
         * 	at com.alphawang.concurrency.c2_cooperation.waitnotify.WrongWaitDeadLock$$Lambda$1/933699219.run(Unknown Source)
         * 	at java.lang.Thread.run(Thread.java:745)
         */
        testPopPush(new WrongWaitStack());

    }
}