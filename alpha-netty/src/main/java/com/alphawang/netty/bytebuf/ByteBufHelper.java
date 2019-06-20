package com.alphawang.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class ByteBufHelper {

    public static void main(String[] args) {
        /**
         * after ===========CREATE buffer(9, 100)============
         * capacity(): 9
         * maxCapacity(): 100
         * readerIndex(): 0
         * readableBytes(): 0
         * isReadable(): false
         * writerIndex(): 0
         * writableBytes(): 9
         * isWritable(): true
         * maxWritableBytes(): 100
         */
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);
        print("CREATE buffer(9, 100)", buffer);

        /**
         * **** writeBytes: 改变writerIndex
         *
         * after ===========writeBytes(1, 2, 3 ,4)============
         * capacity(): 9
         * maxCapacity(): 100
         * readerIndex(): 0
         * readableBytes(): 4   // 0 -> 4
         * isReadable(): true
         * writerIndex(): 4     // 0 -> 4
         * writableBytes(): 5   // 9 -> 5
         * isWritable(): true
         * maxWritableBytes(): 96  // 100 -> 96
         */
        buffer.writeBytes(new byte[] { 1, 2, 3, 4 });
        print("writeBytes(1, 2, 3 ,4)", buffer);

        /**
         * **** writerInt: 改变writerIndex + 4 
         *
         * after ===========writeInt(12)============
         * capacity(): 9
         * maxCapacity(): 100
         * readerIndex(): 0
         * readableBytes(): 8   // 4 -> 8
         * isReadable(): true
         * writerIndex(): 8     // 4 -> 8
         * writableBytes(): 1   // 5 -> 1
         * isWritable(): true
         * maxWritableBytes(): 92  // 96 -> 92
         */
        buffer.writeInt(12);
        print("writeInt(12)", buffer);

        /**
         * **** writeBytes: 再占一位
         *
         * after ===========writeBytes(5)============
         * capacity(): 9
         * maxCapacity(): 100
         * readerIndex(): 0
         * readableBytes(): 9    // 8 -> 9
         * isReadable(): true
         * writerIndex(): 9      // 8 -> 9
         * writableBytes(): 0    // 1 -> 0
         * isWritable(): false   // true -> false
         * maxWritableBytes(): 91   // 92 -> 91
         */
        buffer.writeBytes(new byte[] { 5 });
        print("writeBytes(5)", buffer);

        /**
         * **** writeBytes: buffer不可写时，出发扩容
         *
         * after ===========writeBytes(6)============
         * capacity(): 64        // 9 -> 64
         * maxCapacity(): 100
         * readerIndex(): 0
         * readableBytes(): 10   // 9 -> 10
         * isReadable(): true
         * writerIndex(): 10
         * writableBytes(): 54   // 0 -> 64
         * isWritable(): true    // false -> true
         * maxWritableBytes(): 90  // 91 -> 90
         */
        buffer.writeBytes(new byte[] { 6 });
        print("writeBytes(6)", buffer);

        /**
         * **** get 方法不改变读写指针
         *
         * after ===========getByte()============
         * capacity(): 64
         * maxCapacity(): 100
         * readerIndex(): 0
         * readableBytes(): 10
         * isReadable(): true
         * writerIndex(): 10
         * writableBytes(): 54
         * isWritable(): true
         * maxWritableBytes(): 90
         */
        // get 方法不改变读写指针
        System.out.println("getByte(3) return: " + buffer.getByte(3));
        System.out.println("getShort(3) return: " + buffer.getShort(3));
        System.out.println("getInt(3) return: " + buffer.getInt(3));
        print("getByte()", buffer);

        // set 方法不改变读写指针
        buffer.setByte(buffer.readableBytes() + 1, 0);
        print("setByte()", buffer);

        /**
         * after ===========readBytes(10)============
         * capacity(): 64
         * maxCapacity(): 100
         * readerIndex(): 10     // 0 -> 10
         * readableBytes(): 0    // 10 -> 0
         * isReadable(): false   // true -> false
         * writerIndex(): 10
         * writableBytes(): 54
         * isWritable(): true
         * maxWritableBytes(): 90
         */
        // read 方法改变读指针
        byte[] dst = new byte[buffer.readableBytes()];
        buffer.readBytes(dst);
        print("readBytes(" + dst.length + ")", buffer);

    }

    public static void print(String action, ByteBuf buffer) {
        System.out.println("after ===========" + action + "============");
        System.out.println("capacity(): " + buffer.capacity());
        System.out.println("maxCapacity(): " + buffer.maxCapacity());
        System.out.println("readerIndex(): " + buffer.readerIndex());
        System.out.println("readableBytes(): " + buffer.readableBytes());
        System.out.println("isReadable(): " + buffer.isReadable());
        System.out.println("writerIndex(): " + buffer.writerIndex());
        System.out.println("writableBytes(): " + buffer.writableBytes());
        System.out.println("isWritable(): " + buffer.isWritable());
        System.out.println("maxWritableBytes(): " + buffer.maxWritableBytes());
        System.out.println();
    }

}
