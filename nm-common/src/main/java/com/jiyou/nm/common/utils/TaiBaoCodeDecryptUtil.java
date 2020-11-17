package com.jiyou.nm.common.utils;

/**
 * 泰宝码解码工具
 *
 * @author 张恒同
 * @date 2020/3/20 8:56 上午
 */
public class TaiBaoCodeDecryptUtil {

    private static final String cipher = "0123456789abcdefghijklmnopqrstuvwxyz";

    private static final int baseLength = 36;

    private static final int coverLength = 0x10;

    public static String decrypt(String code){
        code = code.toLowerCase();
        Long num = 0L;
        for (int i = 0; i < code.length(); i ++){
            int index = cipher.indexOf(code.charAt(i));
            num += index * ((long)Math.pow((double)baseLength, (double)i));
        }
        String str = padLeft(String.valueOf(num), coverLength, '0');
        return ((str.length() == coverLength) ? str : null);
    }

    public static String padLeft(String src, int len, char ch) {
        int diff = len - src.length();
        if (diff <= 0) {
            return src;
        }

        char[] charr = new char[len];
        System.arraycopy(src.toCharArray(), 0, charr, 0, src.length());
        for (int i = src.length(); i < len; i++) {
            charr[i] = ch;
        }
        return new String(charr);
    }

}
