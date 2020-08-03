package com.anaheim.unicorn.example.encryption;

import com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * md5加密(信息摘要算法)
 */
public class MD5Demo {
    public static void main(String[] args) {
        String str="123456789";
        str = str + getSalt();
        try {
            System.out.println(md5_3(str));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取盐值
     * @return
     */
    public static String getSalt(){
        Random random = new Random();
        StringBuilder sbuilder = new StringBuilder(16);
        sbuilder.append(random.nextInt(99999999)).append(random.nextInt(99999999));
        if(sbuilder.length() < 16){
            for (int i = 0; i < 16 - sbuilder.length(); i++) {
                sbuilder.append("0");
            }
        }
        return sbuilder.toString();
    }

    public static String md5_1(String str){ return DigestUtils.md5Hex(str); }

    public static String md5_2(String str){
        return org.springframework.util.DigestUtils.md5DigestAsHex(str.getBytes());
    }

    public static String md5_3(String str) throws NoSuchAlgorithmException {
        MessageDigest mdigest = MessageDigest.getInstance("MD5");
        byte[] bytes = mdigest.digest(str.getBytes());
        StringBuilder sbuilder = new StringBuilder(32);
        for (int b : bytes) {
            if(b < 0) b += 256;
            if(b < 16) sbuilder.append("0");
            sbuilder.append(Integer.toHexString(b));
        }
        return sbuilder.toString();
    }
}
