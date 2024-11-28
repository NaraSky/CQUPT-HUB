package com.lb.subject.infra.basic.utils;

import com.alibaba.druid.filter.config.ConfigTools;

/**
 * @author lb
 * @date 2024/11/28 10:37
 * @description druid加密工具类
 */
public class DruidEncryptUtil {
    private static String publicKey;

    private static String privateKey;

   static {
       try {
           String[] keyPair = ConfigTools.genKeyPair(512);
           publicKey = keyPair[1];
           System.out.println("公钥：" + publicKey);
           privateKey = keyPair[0];
           System.out.println("私钥：" + privateKey);
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

   public static String encrypt(String plainText) {
       try {
           return ConfigTools.encrypt(privateKey, plainText);
       } catch (Exception e) {
           throw new RuntimeException("加密失败");
       }
   }

   public static String decrypt(String cipherText) {
       try {
           return ConfigTools.decrypt(publicKey, cipherText);
       } catch (Exception e) {
           throw new RuntimeException("解密失败");
       }
   }

    public static void main(String[] args) {
        String encrypt = encrypt("mysql_1120");
        System.out.println("encrypt = " + encrypt);
        String decrypt = decrypt(encrypt);
        System.out.println("decrypt = " + decrypt);
    }
}
