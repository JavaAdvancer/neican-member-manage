package com.jiyou.nm.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author : cpc
 * @date : 2017-09-21
 **/
public class JsonUtil {

    private static ObjectMapper mapper;

    /**
     * 初始化
     */
    private static void init(){
        if(mapper==null){
            mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        }
    }

    /**
     * json转实体
     * @param jsonString json字符串
     * @param clazz 实体类
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        try {
            init();
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 实体转json
     * @param object 实体类
     * @return json字符串
     */
    public static String toJson(Object object) {
        try {
            init();
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
