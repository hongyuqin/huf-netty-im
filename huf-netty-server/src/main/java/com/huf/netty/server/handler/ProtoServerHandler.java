package com.huf.netty.server.handler;

import com.huf.netty.common.MessageType;
import com.huf.netty.common.proto.MessageProtos;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: Huf.Hong
 * @date: 2020/4/7  5:08 下午
 **/
@Slf4j
public class ProtoServerHandler  extends SimpleChannelInboundHandler<MessageProtos.Message> {
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtos.Message msg) throws Exception {
        //1.先打印出来
        log.info("from : {}",msg.getFromUserId());
        log.info("message :{}",msg.getMessage());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info("{} 上线",channel.remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info("{} 下线",channel.remoteAddress());
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        MessageProtos.Message message = MessageProtos.Message.newBuilder()
                .setType(MessageType.SYSTEM_CHAT.getCode())
                .setMessage("[服务器] - " + channel.remoteAddress() + "加入")
                .setFromUserId("system")
                .setToUserId("all")
                .build();
        channelGroup.writeAndFlush(message);
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        MessageProtos.Message message = MessageProtos.Message.newBuilder()
                .setType(MessageType.SYSTEM_CHAT.getCode())
                .setMessage("[服务器] - " + channel.remoteAddress() + "离开")
                .setFromUserId("system")
                .setToUserId("all")
                .build();
        channelGroup.writeAndFlush(message);
        log.info("group size : {}",channelGroup.size());
    }
}