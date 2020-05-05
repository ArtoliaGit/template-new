package com.bsoft.template.util.encrypt;

import com.bsoft.template.common.Constant;
import com.bsoft.template.util.GsonUtil;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * AES加密工具类
 */
public class AESEncryptUtil {

    private static final String KEY = Constant.AES_KEY;

    private static final String AES_IV = Constant.AES_IV;

    private static final String ALGORITHMSTR = Constant.AES_ALGORITHMSTR;

    /**
     * 加密方法
     * @param content 加密字符串
     * @param encryptKey key值
     * @return 加密后的密文
     */
    public static String encrypt(String content, String encryptKey, String encryptIv) throws Exception {
        MessageDigest dig = MessageDigest.getInstance("MD5");
        byte[] key = dig.digest(encryptKey.getBytes(StandardCharsets.UTF_8));
        byte[] aesIv = dig.digest(encryptIv.getBytes(StandardCharsets.UTF_8));

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        IvParameterSpec iv = new IvParameterSpec(aesIv);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), iv);
        byte[] bytes = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 解密方法
     * @param encryptContent 密文
     * @param decryptKey key值
     * @return 明文
     */
    public static String decrypt(String encryptContent, String decryptKey, String decryptIv) throws Exception {
        MessageDigest dig = MessageDigest.getInstance("MD5");
        byte[] key = dig.digest(decryptKey.getBytes(StandardCharsets.UTF_8));
        byte[] aesIv = dig.digest(decryptIv.getBytes(StandardCharsets.UTF_8));

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        IvParameterSpec iv = new IvParameterSpec(aesIv);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), iv);
        byte[] encryptBytes = Base64.getDecoder().decode(encryptContent);
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes, StandardCharsets.UTF_8);
    }

    /**
     * 加密方法，默认KEY和IV
     * @param content 加密的字符串
     * @return 密文
     */
    public static String encrypt(String content) throws Exception {
        return encrypt(content, KEY, AES_IV);
    }

    /**
     * 加密方法，默认KEY
     * @param content 加密字符串
     * @param encryptIv 偏移量
     * @return 密文
     */
    public static String encrypt(String content, String encryptIv) throws Exception {
        return encrypt(content, KEY, encryptIv);
    }

    /**
     * 解密方法，默认KEY和IV
     * @param encryptContent 密文
     * @return 明文
     */
    public static String decrypt(String encryptContent) throws Exception {
        return decrypt(encryptContent, KEY, AES_IV);
    }

    /**
     * 解密方法，默认KEY
     * @param encryptContent 密文
     * @param decryptIv 偏移量
     * @return 明文
     */
    public static String decrypt(String encryptContent, String decryptIv) throws Exception {
        return decrypt(encryptContent, KEY, decryptIv);
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("username", "admin");
        map.put("password", "12345");
        String content = GsonUtil.objectToJson(map);
        System.out.println("加密前 =====> " + content);

        String encryptContent = encrypt(content);
        System.out.println("加密后 =====> " + encryptContent);

        String decryptContent = decrypt(encryptContent);
        System.out.println("解密后 =====> " + decryptContent);

    }
}
