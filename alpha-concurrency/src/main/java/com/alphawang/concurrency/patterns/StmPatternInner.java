package com.alphawang.concurrency.patterns;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class StmPatternInner {
    

    class Account {
        private TxnRef<Long> balance;
        
        public Account(long balance) {
            this.balance = new TxnRef<>(balance);
        }
        
        public void transfer(Account target, long amt) {
            STM.atomic((txn -> {
                long from = this.balance.getValue(txn);
                this.balance.setValue(txn,from - amt);
                
                long to = target.balance.getValue(txn);
                target.balance.setValue(txn, to + amt);
            }));
        }
    }
}

/**
 * 带版本号的对象引用
 */
final class VersionedRef<T> {
    final T value;
    final long version; //版本号

    public VersionedRef(T value, long version) {
        this.value = value;
        this.version = version;
    }
}

/**
 * 支持事务的引用
 */
final class TxnRef<T> {
    volatile VersionedRef curRef;

    public TxnRef(T value) {
        this.curRef = new VersionedRef(value, 0L);
    }

    public T getValue(Txn txn) {
        return txn.get(this);
    }

    public void setValue(Txn txn, T value) {
        txn.set(this, value);
    }
}

/**
 * 事务接口
 */
interface Txn{
    <T> T get(TxnRef<T> ref);
    <T> void set(TxnRef<T> ref, T value);
}

/**
 * 事务实现类
 */
final class StmTxn implements Txn {

    private static AtomicLong txnSeq = new AtomicLong(0);
    //当前事务所有相关数据
    private Map<TxnRef, VersionedRef> inTxnMap = new HashMap<>();
    //当前事务所有需要修改的数据
    private Map<TxnRef, Object> writeMap = new HashMap<>();
    //当前事务ID
    private long txnId;

    public StmTxn() {
        txnId = txnSeq.incrementAndGet();
    }

    @Override
    public <T> T get(TxnRef<T> ref) {
        if (!inTxnMap.containsKey(ref)) {
            inTxnMap.put(ref, ref.curRef);
        }

        return (T) inTxnMap.get(ref).value;
    }

    @Override
    public <T> void set(TxnRef<T> ref, T value) {
        if (!inTxnMap.containsKey(ref)) {
            inTxnMap.put(ref, ref.curRef);
        }

        writeMap.put(ref, value);
    }

    public boolean commit() {
        synchronized (STM.commitLock) {
            boolean isValid = true;
            
            // 校验所有读过的数据是否发生过变化
            for (Map.Entry<TxnRef, VersionedRef> entry : inTxnMap.entrySet()) {
                 VersionedRef curRef = entry.getKey().curRef;
                 VersionedRef readRef = entry.getValue();
                 
                 if (curRef.version != readRef.version) {
                     isValid = false;
                     break;
                 }
            }
            
            // 若无变化，则更改生效
            if (isValid) {
                writeMap.forEach((k, v) -> {
                    k.curRef = new VersionedRef(v, txnId);
                });
            }
            
            return isValid;
        }
    }

}

@FunctionalInterface 
interface TxnRunnable {
    void run(Txn txn);
}

/**
 * atomic操作：类似CAS
 */
final class STM {
    private STM() {
        
    }
    
    static final Object commitLock = new Object();
    
    public static void atomic(TxnRunnable action) {
        boolean committed = false;
        while (!committed) {
            StmTxn txn = new StmTxn(); //每次执行，版本号+1
            action.run(txn);
            
            committed = txn.commit();
        }
    }
}
