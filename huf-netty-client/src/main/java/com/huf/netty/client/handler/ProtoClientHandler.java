package com.huf.netty.client.handler;

import com.huf.netty.common.proto.MessageProtos;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: Huf.Hong
 * @date: 2020/4/7  5:23 下午
 **/
@Slf4j
public class ProtoClientHandler  extends SimpleChannelInboundHandler<MessageProtos.Message> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtos.Message msg) {
        log.info("from :{}",msg.getFromUserId());
        log.info("message : {}",msg.getMessage());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("caught exception :{}", cause.getMessage());
    }

}