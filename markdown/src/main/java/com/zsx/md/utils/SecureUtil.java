package com.zsx.md.utils;

import org.jasypt.util.text.BasicTextEncryptor;

public class SecureUtil {


    private static final String password = "shuxue";

    /**
     * 加密
     *
     * @param data
     * @return
     */
    public static String encrypt(String data) {
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPassword(password);
        String encrypt = encryptor.encrypt(data);
        return encrypt;
    }

    /**
     * 解密
     *
     * @param data
     * @return
     */
    public static String decrypt(String data) {
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPassword(password);
        String decrypt = encryptor.decrypt(data);
        return decrypt;
    }
}
