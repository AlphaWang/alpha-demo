package com.alphawang.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.concurrent.TimeUnit;

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
        
        connect(bootstrap, "localhost", 8000, MAX_RETRY);
    }
    
    private static final int MAX_RETRY = 10;
    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap
            .connect(host, port)
            .addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("连接成功 " + host + ":" + port);
                    } else if (retry == 0) {
                        System.out.println("重试次数已用完，放弃连接");
                    } else {
                        // 重试次数
                        int time = (MAX_RETRY - retry) + 1;
                        // 本次重连的间隔
                        int delay = 1 << time;
                        System.err.println("连接失败, 第 " + time + " 次重试... ");
                        
                        bootstrap.config().group().schedule(new Runnable() {
                            @Override public void run() {
                                connect(bootstrap, host, port, retry - 1);
                            }
                        }, delay, TimeUnit.SECONDS);
                    }
                }
            });
    }
}
