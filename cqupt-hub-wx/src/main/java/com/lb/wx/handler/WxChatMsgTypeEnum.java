package com.lb.wx.handler;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum WxChatMsgTypeEnum {
    SUBSCRIBE("event.subscribe", "用户关注事件"),
    TEXT_MSG("text", "接收用户文本消息");

    private final String msgType;

    private final String desc;

    WxChatMsgTypeEnum(String msgType, String desc) {
        this.msgType = msgType;
        this.desc = desc;
    }

    public static WxChatMsgTypeEnum getByMsgType(String msgType) {
        WxChatMsgTypeEnum[] values = WxChatMsgTypeEnum.values();
        for (WxChatMsgTypeEnum value : values) {
            if (value.getMsgType().equals(msgType)) {
                return value;
            }
        }
        return null;
    }

}
