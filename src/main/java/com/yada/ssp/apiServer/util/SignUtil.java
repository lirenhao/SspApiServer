package com.yada.ssp.apiServer.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SignUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String objToJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    public static byte[] SHA256(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(data);
    }

    public static String SHA256(String data) throws NoSuchAlgorithmException {
        return Hex.encodeHexString(SHA256(data.getBytes()));
    }

    /**
     * 签名
     *
     * @param data       签名数据
     * @param privateKey RSA私钥
     * @return 签名结果 签名失败返回null
     */
    public static String sign(String data, String privateKey) {
        String sign = null;
        try {
            String dataHash = SHA256(data);
            byte[] signData = RSAUtil.encrypt(dataHash, privateKey);
            sign = Base64.getEncoder().encodeToString(signData);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return sign;
    }

    /**
     * 签名
     *
     * @param obj        签名对象
     * @param privateKey RSA私钥
     * @return 签名结果 签名失败返回null
     */
    public static String sign(Object obj, String privateKey) {
        String sign = null;
        try {
            sign = sign(objToJson(obj), privateKey);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return sign;
    }

    /**
     * 验签
     *
     * @param data      明文数据
     * @param sign      密文数据
     * @param publicKey RSA公钥
     * @return 验签结果 验签失败返回false
     */
    public static boolean verify(String data, String sign, String publicKey) {
        boolean result = false;
        if (data != null && sign != null) {
            try {
                String dataHash = SHA256(data);
                String signHash = RSAUtil.decrypt(sign, publicKey);
                result = dataHash.equals(signHash);
            } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                    IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 验签
     *
     * @param obj       明文数据对象
     * @param sign      密文数据
     * @param publicKey RSA公钥
     * @return 验签结果 验签失败返回false
     */
    public static boolean verify(Object obj, String sign, String publicKey) {
        boolean result = false;
        try {
            result = verify(objToJson(obj), sign, publicKey);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
