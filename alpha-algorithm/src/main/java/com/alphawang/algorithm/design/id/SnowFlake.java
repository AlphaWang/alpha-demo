package com.alphawang.algorithm.design.id;

/**
 * 64-bit:
 * 
 *  0 - 41位时间戳 - 5位数据中心标识 - 5位机器标识 - 12位序列号
 *  
 *  优化： MachineId 可有zk生成 (美团Leaf)
 */
public class SnowFlake {
    
    private final static long START_STAMP = 1480166465631L;
    
    private final static int BIT_SEQ = 12;
    private final static int BIT_MACHINE = 5;
    private final static int BIT_DATACENTER = 5;
    private final static int BIT_STAMP = 41;
    
    // 最大值
    private final static long MAX_DATACENTER = -1L ^ (-1L << BIT_DATACENTER);
    private final static long MAX_MACHINE = -1L ^ (-1L << BIT_MACHINE);
    private final static long MAX_SEQ = -1L ^ (-1L << BIT_SEQ);
    
    // 每一部分向左的位移？
    private final static long MACHINE_LEFT = BIT_SEQ;
    private final static long DATACENTER_LEFT = BIT_SEQ + BIT_MACHINE;
    private final static long STAMP_LEFT = BIT_SEQ + BIT_MACHINE + BIT_DATACENTER;
    
    private long datacenterId;
    private long machineId;
    private long seq = 0L;
    
    private long lastStamp = -1L;
    
    public SnowFlake(long datacenterId, long machineId) {
        if (datacenterId < 0 || datacenterId > MAX_DATACENTER) {
            throw new IllegalArgumentException(String.format("illegal datacenterId = %s.  should be [%s, %s]", datacenterId, 0, MAX_DATACENTER));
        }
        if (machineId < 0 || machineId > MAX_MACHINE) {
            throw new IllegalArgumentException(String.format("illegal machineId = %s.  should be [%s, %s]", machineId, 0, MAX_MACHINE));
        }
        
        this.datacenterId = datacenterId;
        this.machineId = machineId;

        printInfo();
    }
    
    public synchronized long nextId() {
        long currStamp = now();
        if (currStamp < lastStamp) {
            throw new IllegalStateException(String.format("Clock moved backwards. %s < %s", currStamp, lastStamp));
        }
        
        // 如果是相同的毫秒，则增加序列号
        if (currStamp == lastStamp) {
            seq = (seq + 1) & MAX_SEQ;
            // 序列号用完，
            if (seq == 0L) {
                currStamp = nextMill();
            }
        } 
        // 新的毫秒，序列号 = 0
        else {
            seq = 0L;
        }
        
        lastStamp = currStamp;
        long stampDelta = currStamp - START_STAMP;
        
        return stampDelta << STAMP_LEFT
          | datacenterId << DATACENTER_LEFT
          | machineId << MACHINE_LEFT
          | seq;
    }

    private long now() {
        return System.currentTimeMillis();
    }

    private long nextMill() {
        long curr = now();
        while (curr <= lastStamp) {
            curr = now();
        }

        return curr;
    }
    
    private void printInfo() {
        System.out.println("BIT_MACHINE = " + BIT_MACHINE);
        long tmp = (-1L << BIT_MACHINE);
        System.out.println("-1L << BIT_MACHINE = " + tmp + " - bit: " + Long.toBinaryString(tmp));
        System.out.println("MAX_MACHINE (-1L ^ (-1L << BIT_MACHINE)) = " + MAX_MACHINE + " - bit: " + Long.toBinaryString(MAX_MACHINE));
        System.out.println("MACHINE_LEFT = " + MACHINE_LEFT);
    }

    public static void main(String[] args) {
        SnowFlake snowFlake = new SnowFlake(2, 3);
        
        for (int i = 0; i < 1000; i++) {
            System.out.println(snowFlake.nextId());
        }
    }
    

}
