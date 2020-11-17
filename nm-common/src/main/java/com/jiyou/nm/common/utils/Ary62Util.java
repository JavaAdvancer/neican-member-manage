package com.jiyou.nm.common.utils;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 描述： 自定义62进制
 * 作者： 张恒同
 * 时间： 2019-01-26   17:05
 */
public class Ary62Util {

    private static final char[] array = "JVgNXCpRHdYWwInPc43o97tqaUzKOhFDs6rleQk1T5vEmAGLMi08bfSZjuB2xy".toCharArray();

    private static final String index = "ssssVg";

    /**
     * 转十进制
     *
     * @param val 6位62进制
     * */
    private static Long toLong(String val){
        Long result = 0L;

        char[] ch = val.toCharArray();
        Long base = 1L;
        ArrayUtils.reverse(ch);
        for (char c : ch){
            int index = search(array, c);
            result += index * base;
            base *= 62;
        }

        return result;
    }

    /**
     * 数字转为62进制
     *
     * @param val
     * @param length 返回长度，不足补进制第一个字符
     * */
    private static String toSTHex(Long val, int length){
        StringBuilder sb = new StringBuilder();
        while (true){
            Long m = val / 62;
            Long k = val % 62;
            if (k < 62){
                sb.append(array[k.intValue()]);
            }
            if (m == 0){break;}
            val = m;
        }
        String value = sb.reverse().toString();
        return StringUtils.leftPad(value, length, array[0]);
    }

    public static String increpment(String val){
        Long aLong = toLong(val);
        return toSTHex(aLong + 1, 6);
    }

    public static List<String> batchIncrepment(String val, int size){
        List<String> vals = new ArrayList<>();

        String tempVal = val;
        for (int i = 0; i < size; i ++){
            tempVal = increpment(tempVal);
            vals.add(tempVal);
        }

        return vals;
    }

    /**
     * 根据二维码倒推出年份和企业信息
     *
     * @param qrCode
     * @return
     * */
    public static int[] decodeQrCode(String qrCode){
        if (StringUtils.isBlank(qrCode)){return null;}
        String code = qrCode;
        if (qrCode.contains("/")){
            code = StringUtils.split(qrCode, "/")[1];
        }

        int[] arr = new int[2];
        String company = StringUtils.substring(code, 0, 2);
        String year = StringUtils.substring(code, 2, 3);
        arr[0] = toLong(company).intValue();
        arr[1] = toLong(year).intValue();

        return arr;
    }

    /**
     * 二维码初始值
     *
     * 针对企业某一年份第一次获取二维码值使用
     * */
    public static final String initQrCode(int companyId, int year){
        String company = toSTHex(Long.valueOf(companyId), 2);
        String y = toSTHex(Long.valueOf(year), 1);

        return new StringBuffer().append(company).append(y).append(index).toString();
    }

    public static void main(String[] args) {
        String val = "sn";
//        System.out.println("r - " + toLong("507973435184381952"));

        System.out.println("s - " + toSTHex(1310478124273631276L, 13));

        //System.out.println("t - " + increpment(val));

        //System.out.println(JSON.toJSON(batchIncrepment(val, 100)));

        decodeQrCode(val);

//        System.out.println("L - " + initQrCode(19, 30));

        decodeQrCode(initQrCode(19, 30));

        Long id = 13537086546263552L;
//        System.out.println("id - " + toSTHex(id, 10));

    }

    private static int search(char[] array, char c){
        for (int i = 0; i < array.length; i ++){
            if (array[i] == c){
                return i;
            }
        }
        return -1000;
    }


}
