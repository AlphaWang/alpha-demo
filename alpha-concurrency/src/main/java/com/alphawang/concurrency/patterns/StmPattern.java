package com.alphawang.concurrency.patterns;

import org.multiverse.api.StmUtils;
import org.multiverse.api.references.TxnLong;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 软件事务内存  Software Transactional Memory
 *
 * https://github.com/pveentjer/Multiverse
 */
public class StmPattern {

    public static void main(String[] args) throws InterruptedException {
        Account a = new Account(500);
        Account b = new Account(500);

        ExecutorService es = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 4; i++) {
            es.execute(() -> {
                for (int j = 0; j < 10; j++) {
                    a.transfer(b, 10);
                    System.out.println(a.balance.get());
                    System.out.println(b.balance.get());
                }
            });
        }

        Thread.sleep(10000);

    }

    static class Account {
        private TxnLong balance;

        public Account(long balance) {
            this.balance = StmUtils.newTxnLong(balance);
        }

        public void transfer(Account to, int amt) {
            StmUtils.atomic(() -> {
                if (this.balance.get() > amt) {
                    this.balance.decrement(amt);
                    to.balance.increment(amt);
                }
            });
        }
    }
}
