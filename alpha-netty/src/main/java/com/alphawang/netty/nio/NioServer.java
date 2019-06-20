package com.alphawang.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * 可用BioClient与之通信
 */
public class NioServer {

    public static void main(String[] args) throws IOException {

        // serverSelector负责轮询是否有新的连接
        Selector serverSelector = Selector.open();
        // clientSelector负责轮询连接是否有数据可读
        Selector clientSelector = Selector.open();

        new Thread(() -> {
            try {

                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.socket().bind(new InetSocketAddress(8000));
                serverSocketChannel.configureBlocking(false);

                serverSocketChannel.register(serverSelector, SelectionKey.OP_ACCEPT);

                while (true) {
                    // 监测是否有新的连接，这里的1指的是阻塞的时间为 1ms
                    if (serverSelector.select(1) > 0) {

                        Set<SelectionKey> selectionKeys = serverSelector.selectedKeys();
                        Iterator<SelectionKey> iterator = selectionKeys.iterator();

                        while (iterator.hasNext()) {
                            SelectionKey selectionKey = iterator.next();

                            if (selectionKey.isAcceptable()) {
                                try {
                                    // (1) 每来一个新连接，不需要创建一个线程，而是直接注册到clientSelector
                                    SocketChannel clientChannel = ((ServerSocketChannel) selectionKey.channel()).accept();
                                    clientChannel.configureBlocking(false);

                                    clientChannel.register(clientSelector, SelectionKey.OP_READ);
                                } finally {
                                    // TODO why?
                                    iterator.remove();
                                }

                            }

                        }

                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    // (2) 批量轮询是否有哪些连接有数据可读，这里的1指的是阻塞的时间为 1ms
                    if (clientSelector.select(1) > 0) {
                        Set<SelectionKey> selectionKeys = clientSelector.selectedKeys();
                        Iterator<SelectionKey> iterator = selectionKeys.iterator();

                        while (iterator.hasNext()) {
                            SelectionKey selectionKey = iterator.next();

                            if (selectionKey.isReadable()) {
                                try {
                                    SocketChannel clientChannel = (SocketChannel) selectionKey.channel();
                                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                                    // (3) 面向 Buffer
                                    clientChannel.read(byteBuffer);
                                    byteBuffer.flip();

                                    System.out.println(Thread.currentThread().getName()
                                        + "READ: " + Charset.defaultCharset().newDecoder().decode(byteBuffer).toString());
                                } finally {
                                    iterator.remove();
                                    selectionKey.interestOps(SelectionKey.OP_READ);
                                }

                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
