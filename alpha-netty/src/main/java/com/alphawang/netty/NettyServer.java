package com.alphawang.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NettyServer {

    public static void main(String[] args) {
        // bossGroup表示监听端口，accept 新连接的线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // workerGroup表示处理每一条连接的数据读写的线程组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        // 引导类 ServerBootstrap, 将引导我们进行服务端的启动工作
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
            // 线程模型
            .group(bossGroup, workerGroup)
            // IO模型
            .channel(NioServerSocketChannel.class)
            // 连接读写处理逻辑: 定义后续每条连接的数据读写，业务处理逻辑
            .childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override 
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    System.out.println("===== childHandler 业务处理: " + ch);
                }
            })
            // 指定在服务端启动过程中的一些逻辑
            .handler(new ChannelInitializer<NioServerSocketChannel>() {
                @Override 
                protected void initChannel(NioServerSocketChannel ch) throws Exception {
                    System.out.println("===== handler 服务器启动中... " + ch);
                }
            })
            // 给NioServerSocketChannel指定一些自定义属性
            .attr(AttributeKey.newInstance("serverName"), "alphaNettyServer")
            .childAttr(AttributeKey.newInstance("clientKey"), "clientValue");
        
        serverBootstrap
            .bind(8000)
            .addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override 
                public void operationComplete(Future<? super Void> future) throws Exception {
                   if (future.isSuccess()) {
                       System.out.println("端口绑定成功");
                   } else {
                       System.err.println("端口绑定失败");
                   }
                }
            });
            
    }
    
}
