package com.anaheim.unicorn.example.encryption;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA加密（非对称加密）
 */
public class RSADemo {
    private static final String PUBLICKEY = "PublicKey";
    private static final String PRIVATEKEY = "PrivateKey";
    private static final String CHARSET = "UTF-8";
    public static void main(String[] args) {
        String str ="哦咧哇刚大木";
        System.out.println("原文："+ str);
        try {
            Map<String,String> keyMap = getKeyPair();
            String publicKey =  keyMap.get(PUBLICKEY);
            String privateKey =  keyMap.get(PRIVATEKEY);
            System.out.println("公钥：" + publicKey);
            System.out.println("私钥：" + privateKey);
            String encryptStr = encrypt(str,publicKey);
            System.out.println("加密："+ encryptStr);
            String decryptStr = decrypt(encryptStr,privateKey);
            System.out.println("解密："+ decryptStr);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成秘钥对
     * @return
     */
    public static Map<String,String> getKeyPair() throws NoSuchAlgorithmException{
        Map<String,String> keyMap = new HashMap<String, String>();
        //获取秘钥生成器
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        //初始化秘钥生成器
        SecureRandom sRandom = new SecureRandom();
        keyGenerator.initialize(1024,sRandom);
        //获取秘钥对
        KeyPair keyPair = keyGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
        //Base64加密
        String publicKeyStr = Base64.encodeBase64String(publicKey.getEncoded());
        String privateKeyStr = Base64.encodeBase64String(privateKey.getEncoded());
        keyMap.put(PUBLICKEY,publicKeyStr);
        keyMap.put(PRIVATEKEY,privateKeyStr);
        return keyMap;
    }

    /**
     * 公钥加密
     */
    public static String encrypt(String encryptedStr,String publicKey) throws Exception {
        //Base64解密公钥
        byte[] publicKeyBt = Base64.decodeBase64(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKey rsaPublicKey = (RSAPublicKey)keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBt));
        //进行加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE,rsaPublicKey);
        byte[] encryptdBt =  cipher.doFinal(encryptedStr.getBytes(CHARSET));
        return Base64.encodeBase64String(encryptdBt);
    }

    /**
     * 私钥解密
     * @param privateKey
     */
    public static String decrypt(String decryptedStr,String privateKey) throws Exception {
        //Base64解密私钥
        byte[] privateKeyBt = Base64.decodeBase64(privateKey);
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privateKeyBt));
        //Base64解密密文
        byte[] decodedBt = Base64.decodeBase64(decryptedStr);
        //RSA解密密文
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE,rsaPrivateKey);
        byte[] strByte = cipher.doFinal(decodedBt);
        return new String(strByte);
    }
}
