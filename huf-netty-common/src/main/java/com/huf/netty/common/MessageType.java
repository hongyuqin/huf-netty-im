package com.huf.netty.common;

/**
 * @author: Huf.Hong
 * @date: 2020/4/10  7:14 下午
 **/
public enum MessageType {
    GROUP_CHAT(1,"群聊"),
    SINGLE_CHAT(2,"私聊"),
    SYSTEM_CHAT(3,"系统消息");
    private int code;
    private String desc;

    MessageType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
