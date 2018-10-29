package com.alphawang.netty;

import com.alphawang.netty.bytebuf.ByteBufHelper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String received = receiveMsg( msg);

        TimeUnit.SECONDS.sleep(1);
        
        sendMsg(ctx, received);
    }

    private String receiveMsg(Object msgObj) {
        // TODO 为什么这里不直接是 ByteBuf? 而要强转？
        ByteBuf byteBuf = (ByteBuf) msgObj;
        String msg = byteBuf.toString(Charset.forName("utf-8"));
        System.out.println("... channelRead 服务端读到数据: " + msg);

        ByteBufHelper.inspectByteBuf(byteBuf);
        
        return msg;
    }

    private void sendMsg(ChannelHandlerContext ctx, String originMsg) {
        String tobeSent = "服务器确认已收到 " + originMsg;
        
        byte[] bytes = tobeSent.getBytes(Charset.forName("utf-8"));
        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeBytes(bytes);
        System.out.println("... channelRead 服务端发送数据: " + tobeSent);

        ByteBufHelper.inspectByteBuf(byteBuf);
        
        ctx.channel().writeAndFlush(byteBuf);
    }
}
