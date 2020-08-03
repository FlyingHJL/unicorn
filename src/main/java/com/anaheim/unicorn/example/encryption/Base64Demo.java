package com.anaheim.unicorn.example.encryption;

import java.util.Base64;

/**
 * Base64加密
 */
public class Base64Demo {
    public static void main(String[] args) {
        String str = "123456789";
        //加密
        String encodeStr = Base64.getEncoder().encodeToString(str.getBytes());
        System.out.println(encodeStr);
        //解密
        byte[] decodeByte = Base64.getDecoder().decode(encodeStr);
        System.out.println(new String(decodeByte));
    }
}
