package com.alphawang.concurrency.c3_mutex.queue;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.nio.ByteBuffer;

/**
 * https://time.geekbang.org/column/article/98134
 * https://time.geekbang.org/column/article/79871
 * 
 * https://tech.meituan.com/2016/11/18/disruptor.html
 * https://www.baeldung.com/lmax-disruptor-concurrency
 * 
 * 1. 内存分配更加合理，使用 RingBuffer 数据结构，数组元素在初始化时一次性全部创建，提升缓存命中率；
 *    对象循环利用，避免频繁 GC。
 * 2. 能够避免伪共享，提升缓存利用率。
 * 3. 采用无锁算法，避免频繁加锁、解锁的性能消耗。
 * 4. 支持批量消费，消费者可以无锁方式消费多个消息。
 */
public class DisruptorDemo {

    public static void main(String[] args) throws InterruptedException {
        int bufferSize = 1024;

        /**
         * 创建 Disruptor:
         * - 同时即创建 RingBuffer，其元素也一并创建 （LongEvent::new），所以内存是连续的
         */
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(
            LongEvent::new,
            bufferSize,
            DaemonThreadFactory.INSTANCE);

        /**
         * Consumer:
         * 
         */
        
        disruptor.handleEventsWith((event, sequence, endOfBatch)
            -> System.out.println(
            "[event value]: " + event.value +
                " [sequence]: " + sequence +
                " [endOfBatch]: " + endOfBatch));

        RingBuffer<LongEvent> ringBuffer = disruptor.start();
//        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        /**
         * Producer:
         *  - publishEvent 时，并不创建新的Event，而是重用，event.set()
         *  - 避免频繁创建删除 导致的GC
         *  
         *  - 多线程写：每个线程获取不同的一段数组空间进行操作(CAS)；写入元素的同时设置available Buffer里面相应的位置
         *  - 多线程读：通过 availableBuffer 判断对应元素是否就绪
         */
        ByteBuffer bb = ByteBuffer.allocate(8);

        for (long index = 0; index < 32; index++) {
            bb.putLong(0, index);
            ringBuffer.publishEvent((event, sequence, buffer) ->
                event.set(buffer.getLong(0)), bb);

            Thread.sleep(100);
        }
    }

    static class LongEvent {
        private long value;

        public void set(long value) {
            this.value = value;
        }
    }
}
