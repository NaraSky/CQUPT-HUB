package com.lb.wx.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class SubscribeMsgHandler implements WxChatMsgHandler {

    @Override
    public WxChatMsgTypeEnum getMsgType() {
        return WxChatMsgTypeEnum.SUBSCRIBE;
    }

    /**
     * 处理用户消息
     *
     * @param messageMap 包含用户消息内容的映射
     * @return 返回构建好的XML格式的回复消息
     */
    @Override
    public String dealMsg(Map<String, String> messageMap) {
        // 记录日志，提示用户关注事件被触发
        log.info("触发用户关注事件！");

        // 从消息映射中获取发送者的用户名
        String fromUserName = messageMap.get("FromUserName");
        // 从消息映射中获取接收者的用户名
        String toUserName = messageMap.get("ToUserName");

        // 定义欢迎消息的内容
        String subscribeContent = "感谢您的关注，我是知雨";

        // 构建XML格式的回复消息
        String content = "<xml>\n" +
                "  <ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>\n" +
                "  <FromUserName><![CDATA[" + toUserName + "]]></FromUserName>\n" +
                "  <CreateTime>12345678</CreateTime>\n" +
                "  <MsgType><![CDATA[text]]></MsgType>\n" +
                "  <Content><![CDATA[" + subscribeContent + "]]></Content>\n" +
                "</xml>";

        // 返回构建的回复消息
        return content;
    }


}
