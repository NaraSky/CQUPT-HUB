package com.lb.wx.handler;

import com.lb.wx.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class ReceiveTextMsgHandler implements WxChatMsgHandler {

    private static final String KEY_WORD = "验证码";

    @Resource
    private RedisUtil redisUtil;

    @Override
    public WxChatMsgTypeEnum getMsgType() {
        return WxChatMsgTypeEnum.TEXT_MSG;
    }

    /**
     * 处理接收到的文本消息事件
     *
     * @param messageMap 包含消息内容的映射
     * @return 返回回复的XML格式的消息内容
     */
    @Override
    public String dealMsg(Map<String, String> messageMap) {
        // 记录接收到文本消息事件
        log.info("接收到文本消息事件");

        // 从消息映射中获取消息内容
        String content = messageMap.get("Content");

        // 判断消息内容是否等于预设的关键词
        if (!KEY_WORD.equals(content)) {
            // 如果不等于，返回空字符串
            return "";
        }

        // 从消息映射中获取发送者用户名
        String fromUserName = messageMap.get("FromUserName");
        // 从消息映射中获取接收者用户名
        String toUserName = messageMap.get("ToUserName");

        // 创建一个随机数生成器实例
        Random random = new Random();
        // 生成一个0到999之间的随机数
        int num = random.nextInt(1000);

        // 使用Redis工具类生成一个包含发送者用户名和随机数的键
        String numKey = redisUtil.buildKey(fromUserName, String.valueOf(num));

        // 使用Redis工具类将生成的键设置为一个临时键值对，过期时间为5分钟
        redisUtil.setNx(numKey, "1", 5L, TimeUnit.MINUTES);

        // 构建验证码消息内容
        String numContent = "您当前的验证码是：" + num + "5分钟内有效";

        // 构建回复的XML格式的消息内容
        String replyContent = "<xml>\n" +
                "  <ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>\n" +
                "  <FromUserName><![CDATA[" + toUserName + "]]></FromUserName>\n" +
                "  <CreateTime>12345678</CreateTime>\n" +
                "  <MsgType><![CDATA[text]]></MsgType>\n" +
                "  <Content><![CDATA[" + numContent + "]]></Content>\n" +
                "</xml>";

        // 返回回复的消息内容
        return replyContent;
    }


}
