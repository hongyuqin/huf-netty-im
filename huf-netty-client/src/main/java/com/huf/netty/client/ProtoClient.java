package com.huf.netty.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huf.netty.client.initializer.ProtoClientInitializer;
import com.huf.netty.common.proto.MessageProtos;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Slf4j
public class ProtoClient {
    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        log.info("starting client");
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ProtoClientInitializer());
            ChannelFuture channelFuture = bootstrap.connect("localhost",8899).sync();
            Channel channel = channelFuture.channel();
            log.info("waiting input");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            for(;;) {
                //转成proto格式
                String line = br.readLine();
                log.info("read line is :{}", line);
                JSONObject json = JSON.parseObject(line);
                channel.writeAndFlush(MessageProtos.Message.newBuilder()
                        .setMessage(json.getString("message"))
                        .setType(json.getIntValue("type"))
                        .setFromUserId(json.getString("fromUserId"))
                        .setToUserId(json.getString("toUserId")).build());
            }

        } catch (Exception e) {
            log.error("throw exception : ",e);
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}