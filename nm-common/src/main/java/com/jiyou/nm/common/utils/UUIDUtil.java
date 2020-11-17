package com.jiyou.nm.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * @author MYM
 */
public class UUIDUtil {

    /**
     * author:ma_yming
     * dis:产生一个32位的UUUID
     * */
    public static String UUIDGenerators(){
        String s = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return s;
    }

    /**
     * author:ma_yming
     * dis:产生一个中间带有中横线的32位UUUID
     * */
    public static String UUIDGenerLine(){
        String s = UUID.randomUUID().toString().trim();
        return s;
    }

    public static String randrom(){
        return StringUtils.remove(UUID.randomUUID().toString().toUpperCase(),'-');
    }
}
