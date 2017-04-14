package com.wusiq.weixin.utils;

/**
 * Created by wicker on 2017/4/1.
 */
public class StringTools {
    /**
     * 字节数组转换成字符串
     */
    public static String byteToStr(byte[] digest) {
        // TODO Auto-generated method stub
        String strDigest = "";
        for (int i = 0; i < digest.length; i++) {
            strDigest += byteToHexStr(digest[i]);
        }
        return strDigest;
    }

    /**
     * 字符串转换成字节数组
     */
    public static String byteToHexStr(byte b) {

        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(b >>> 4) & 0X0F];
        tempArr[1] = Digit[b & 0X0F];

        String s = new String(tempArr);
        return s;
    }
}
