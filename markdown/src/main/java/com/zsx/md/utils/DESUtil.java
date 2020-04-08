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

        byte[] encrypt = encrypt(data.getBytes(), key);
        System.out.println("加密结果为： " + bytesToHex(encrypt));

        byte[] decrypt = decrypt(encrypt, key);
        System.out.println("解密结果： " + new String(decrypt));

        /*
        System.out.println("DES KEY : " + BytesToHex.fromBytesToHex(desKey));
        byte[] desResult = DESUtil.encrypt(DATA.getBytes(), desKey);
        System.out.println(DATA + ">>>DES 加密结果>>>" + BytesToHex.fromBytesToHex(desResult));

        byte[] desPlain = DESUtil.decrypt(desResult, desKey);
        System.out.println(DATA + ">>>DES 解密结果>>>" + new String(desPlain));
    }
         */

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

}
