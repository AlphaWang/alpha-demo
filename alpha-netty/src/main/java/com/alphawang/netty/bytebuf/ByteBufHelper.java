package com.alphawang.netty.bytebuf;

import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

public class ByteBufHelper {
    
    public static void inspectByteBuf(ByteBuf byteBuf) {
        Map info = new HashMap() {
            {

                put("capacity", byteBuf.capacity());
                put("maxCapacity", byteBuf.maxCapacity());

                put("writerIndex", byteBuf.writerIndex());
                put("readerIndex", byteBuf.readerIndex());

                put("readableBytes", byteBuf.readableBytes()); //readableBytes() 表示 ByteBuf 当前可读的字节数，它的值等于 writerIndex-readerIndex
                put("isReadable", byteBuf.isReadable());

                put("writableBytes", byteBuf.writableBytes()); //writableBytes() 表示 ByteBuf 当前可写的字节数，它的值等于 capacity-writerIndex
                put("isWritable", byteBuf.isWritable());

            }
        };

        System.out.println("ByteBuf: " + info);
    }
}
