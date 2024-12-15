package com.lb.wx.utils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageUtil {

    /**
     * 解析微信发来的请求（XML格式）并转换为Map对象。
     *
     * @param msg 从微信服务器接收到的XML格式的消息内容
     * @return 将XML解析后的内容存储在Map对象中，Map的键为XML标签名，值为对应的标签内容
     */
    public static Map<String, String> parseXml(final String msg) {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();

        // 从request中取得输入流
        try (InputStream inputStream = new ByteArrayInputStream(msg.getBytes(StandardCharsets.UTF_8.name()))) {
            // 读取输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);

            // 得到xml根元素
            Element root = document.getRootElement();
            // 得到根元素的所有子节点
            List<Element> elementList = root.elements();

            // 遍历所有子节点
            for (Element e : elementList) {
                // 将子节点的名称和文本内容存储到Map中
                map.put(e.getName(), e.getText());
            }
        } catch (Exception e) {
            // 异常处理，打印异常堆栈信息
            e.printStackTrace();
        }

        // 返回解析后的Map对象
        return map;
    }
}
