package com.lb.wx.utils;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.util.Arrays;

@Slf4j
public class SHA1 {

    /**
     * 用SHA1算法生成安全签名
     *
     * @param token     票据，用于生成签名的安全密钥
     * @param timestamp 时间戳，用于确保消息的新鲜性
     * @param nonce     随机字符串，用于增加签名的不可预测性
     * @param encrypt   密文，待签名的数据
     * @return 安全签名，用于验证消息的完整性和真实性
     */
    public static String getSHA1(String token, String timestamp, String nonce, String encrypt) {
        try {
            // 将票据、时间戳、随机字符串和密文放入数组
            String[] array = new String[]{token, timestamp, nonce, encrypt};
            StringBuffer sb = new StringBuffer();

            // 字符串排序
            Arrays.sort(array);
            // 拼接排序后的字符串
            for (int i = 0; i < 4; i++) {
                sb.append(array[i]);
            }
            String str = sb.toString();

            // SHA1签名生成
            // 获取SHA-1算法的MessageDigest实例
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            // 更新要签名的数据
            md.update(str.getBytes());
            // 计算消息的摘要（即签名）
            byte[] digest = md.digest();

            // 将字节数组转换为十六进制字符串
            StringBuffer hexStr = new StringBuffer();
            String shaHex = "";
            for (int i = 0; i < digest.length; i++) {
                // 将字节转换为十六进制数
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                // 如果转换后的十六进制数长度小于2，则在前面补0
                if (shaHex.length() < 2) {
                    hexStr.append(0);
                }
                // 将十六进制数追加到结果字符串中
                hexStr.append(shaHex);
            }
            // 返回生成的十六进制签名
            return hexStr.toString();
        } catch (Exception e) {
            // 如果出现异常，则记录错误日志并返回null
            log.error("sha加密生成签名失败:", e);
            return null;
        }
    }

}
