package com.alphawang.netty;

import com.alphawang.netty.bytebuf.ByteBufHelper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * channelActive 会在客户端连接建立成功之后被调用
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf byteBuf = ctx.alloc().buffer();

        String msg = "Netty Msg ---- " + new Date();
        byte[] bytes = msg.getBytes(Charset.forName("utf-8"));
        byteBuf.writeBytes(bytes);
        System.out.println("...channelActive - 客户端写出: " + msg);

        ByteBufHelper.print("CLIENT channelActive ctx.alloc().buffer()", byteBuf);

        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        String string = byteBuf.toString(Charset.forName("utf-8"));
        System.out.println("... channelRead 客户端读到数据: " + string);

        ByteBufHelper.print("CLIENT channelRead param", byteBuf);
    }

}
