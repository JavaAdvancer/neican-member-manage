package com.jiyou.nm.common.utils;


/**
 * 描述：
 * 作者： 张恒同
 * 时间： 2019-04-01   09:22
 */
public class CommonUtil {

    public static String getIndexNameByModel(String prefix, Integer userId, String modelId){
        //if (StringUtils.isBlank(prefix)){
        //    return "customer_" + userId + "_" + modelId;
        //}
        //return prefix + "_" + userId + "_" + modelId;
        return "customer_" + userId + "_" + modelId;
    }

    public static String getIndexNameByModel(Integer userId, String modelId){
        return "customer_" + userId + "_" + modelId;
    }

}
