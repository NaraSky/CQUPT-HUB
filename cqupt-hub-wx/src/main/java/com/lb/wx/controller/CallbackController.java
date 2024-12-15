package com.lb.wx.controller;

import com.lb.wx.handler.WxChatMsgFactory;
import com.lb.wx.handler.WxChatMsgHandler;
import com.lb.wx.utils.MessageUtil;
import com.lb.wx.utils.SHA1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
public class CallbackController {

    private static final String token = "adwidhaidwoaid";

    @Resource
    private WxChatMsgFactory wxChatMsgFactory;

    @RequestMapping("/test")
    public String test() {
        return "hello world";
    }

    /**
     * 回调消息校验
     *
     * @param signature 微信服务器发送的签名
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param echostr   随机字符串
     * @return 如果签名验证通过，则返回echostr；否则返回"unknown"
     */
    @GetMapping("callback")
    public String callback(@RequestParam("signature") String signature,
                           @RequestParam("timestamp") String timestamp,
                           @RequestParam("nonce") String nonce,
                           @RequestParam("echostr") String echostr) {
        // 记录接收到的请求参数
        log.info("get验签请求参数：signature:{}，timestamp:{}，nonce:{}，echostr:{}", signature, timestamp, nonce, echostr);

        // 生成本地签名
        String shaStr = SHA1.getSHA1(token, timestamp, nonce, "");

        // 验证签名
        if (signature.equals(shaStr)) {
            // 签名验证通过，返回echostr
            return echostr;
        }

        // 签名验证失败，返回"unknown"
        return "unknown";
    }

    /**
     * 处理微信回调请求
     *
     * @param requestBody 请求体，包含微信消息的内容
     * @param signature   签名，用于验证请求的真实性
     * @param timestamp   时间戳，用于验证请求的新鲜度
     * @param nonce       随机数，用于验证请求的新鲜度
     * @param msgSignature 消息签名，可选参数，用于验证消息的真实性
     * @return 返回回复的XML格式的消息内容
     */
    @PostMapping(value = "callback", produces = "application/xml;charset=UTF-8")
    public String callback(
            @RequestBody String requestBody,
            @RequestParam("signature") String signature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam(value = "msg_signature", required = false) String msgSignature) {
        // 记录接收到的微信消息内容
        log.info("接收到微信消息：requestBody：{}", requestBody);

        // 解析XML格式的请求体，获取消息内容映射
        Map<String, String> messageMap = MessageUtil.parseXml(requestBody);

        // 获取消息类型
        String msgType = messageMap.get("MsgType");

        // 获取事件类型，如果事件类型不存在则设置为空字符串
        String event = messageMap.get("Event") == null ? "" : messageMap.get("Event");

        // 记录消息类型和事件类型
        log.info("msgType:{},event:{}", msgType, event);

        // 构建消息类型键
        StringBuilder sb = new StringBuilder();
        sb.append(msgType);

        // 如果事件类型不为空，则添加到消息类型键中
        if (!StringUtils.isEmpty(event)) {
            sb.append(".");
            sb.append(event);
        }

        // 获取构建好的消息类型键
        String msgTypeKey = sb.toString();

        // 根据消息类型键获取对应的消息处理器
        WxChatMsgHandler wxChatMsgHandler = wxChatMsgFactory.getHandlerByMsgType(msgTypeKey);

        // 如果获取不到消息处理器，则返回"unknown"
        if (Objects.isNull(wxChatMsgHandler)) {
            return "unknown";
        }

        // 调用消息处理器的dealMsg方法处理消息，并获取回复内容
        String replyContent = wxChatMsgHandler.dealMsg(messageMap);

        // 记录回复内容
        log.info("replyContent:{}", replyContent);

        // 返回回复内容
        return replyContent;
    }




}
