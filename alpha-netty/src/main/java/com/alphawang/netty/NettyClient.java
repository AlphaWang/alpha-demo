package com.alphawang.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NettyClient {

    public static void main(String[] args) {

        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        // ServerBootstrap --> Bootstrap
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
            // 线程模型
            .group(workerGroup)
            // IO模型
            .channel(NioSocketChannel.class)
            // IO处理逻辑
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override 
                protected void initChannel(SocketChannel ch) throws Exception {
                    System.out.println("===== client handler... " + ch);
                }
            });
        
        connect(bootstrap, "localhost", 8000);
    }
    
    private static void connect(Bootstrap bootstrap, String host, int port) {
        bootstrap
            .connect(host, port)
            .addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("连接成功 " + host + ":" + port);
                    } else {
                        System.err.println("连接失败, 重试...");
                        connect(bootstrap, host, port);
                    }
                }
            });
    }
}
