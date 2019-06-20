package com.alphawang.concurrency.c3_mutex.queue;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.nio.ByteBuffer;

public class DisruptorDemo {

    public static void main(String[] args) throws InterruptedException {
        int bufferSize = 1024;

        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(
            LongEvent::new,
            bufferSize,
            DaemonThreadFactory.INSTANCE);

        // Consumer
        disruptor.handleEventsWith((event, sequence, endOfBatch)
            -> System.out.println(
            "[event value]: " + event.value +
                " [sequence]: " + sequence +
                " [endOfBatch]: " + endOfBatch));

        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        // Producer
        ByteBuffer bb = ByteBuffer.allocate(8);

        for (long index = 0; true; index++) {
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
