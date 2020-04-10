package com.zsx.md.utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class DESUtil {


    private static final String DES = "DES";


    public static void main(String[] args) throws Exception {
        String data = "shuxue";
        byte[] key = initKey();
        System.out.println("key: " + bytesToHex(key));

//        key = hexToBytes("zhao");

//        byte[] encrypt = encrypt(data.getBytes(), key);
//        System.out.println("加密结果为： " + bytesToHex(encrypt));
//
//        byte[] decrypt = decrypt(encrypt, key);
//        System.out.println("解密结果： " + new String(decrypt));

//        key: fb9ea762a8d9d662
//        加密结果为： 94fa4a587740d7c8
//        解密结果： shuxue

        byte[] decrypt = decrypt(hexToBytes("aa7e378005bf3ae3"), hexToBytes("70ea987c57c20eb0"));
        System.out.println("解密结果： " + new String(decrypt));


    }

    /**
     * 生成密钥
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static byte[] initKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(DES);
        keyGenerator.init(56);
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    /**
     * DES 加密
     *
     * @param data
     * @param key
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec secretKey = new SecretKeySpec(key, DES);
        Cipher cipher = Cipher.getInstance(DES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] bytes = cipher.doFinal(data);
        return bytes;
    }

    /**
     * DES 解密
     *
     * @param data
     * @param key
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec secretKey = new SecretKeySpec(key, DES);
        Cipher cipher = Cipher.getInstance(DES);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] bytes = cipher.doFinal(data);
        return bytes;
    }


    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }


    public static byte[] hexToBytes(String hex) {
        hex = hex.length() % 2 != 0 ? "0" + hex : hex;

        byte[] b = new byte[hex.length() / 2];
        for (int i = 0; i < b.length; i++) {
            int index = i * 2;
            int v = Integer.parseInt(hex.substring(index, index + 2), 16);
            b[i] = (byte) v;
        }
        return b;
    }

}
