package com.jiyou.nm.common.utils;


import java.util.Collection;
import java.util.Map;

/**
 * Created by liuyang on 17/9/14.
 */
public class ObjectUtil {

    /**
     * 判读对象是否为null
     *
     * @param object
     * @return boolean
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * 判读对象数组中是否有null
     *
     * @param objects
     * @return boolean
     */
    public static boolean hasNull(Object... objects) {
        if (isNull(objects)) {
            return true;
        }

        for (int i = 0; i < objects.length; i++) {
            if (isNull(objects[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判读对象数组中是否都为null
     *
     * @param objects
     * @return boolean
     */
    public static boolean allNull(Object... objects) {
        if (isNull(objects)) {
            return true;
        }

        for (Object object : objects) {
            if (isNotNull(object)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判读对象是否不为null
     *
     * @param object
     * @return boolean
     */
    public static boolean isNotNull(Object object) {
        return object != null;
    }

    /**
     * 判读对象是否为空 (string map list set 等包含是否为空)
     *
     * @param object
     * @return boolean
     */
    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        }

        if (object instanceof String) {
            if ("".equals(object)) {
                return true;
            }
        } else if (object instanceof Map) {
            if (((Map<?, ?>) object).isEmpty()) {
                return true;
            }
        } else if (object instanceof Collection && (((Collection<?>) object).isEmpty())) {
            return true;
        }
        return false;
    }

    /**
     * 判读对象是否不为空 (string map list set 等包含是否不为空)
     *
     * @param object 数据
     * @return boolean
     */
    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }

    /**
     * 判读对象数组中是否有empty
     *
     * @param objects 数据
     * @return boolean
     */
    public static boolean hasEmpty(Object... objects) {
        if (isNull(objects)) {
            return true;
        }

        for (int i = 0; i < objects.length; i++) {
            if (isEmpty(objects[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判读对象数组是否是empty
     *
     * @param ts 数据
     * @return boolean
     */
    public static <T> boolean isArrayEmpty(T[] ts) {
        return isNull(ts) || ts.length == 0;
    }

    /**
     * 对象toString
     *
     * @param object 传入的对象可为String,List,Map,对象(必须实现toString方法)
     * @return
     */
    public static String objectToString(Object object){
        if(isEmpty(object)){
            return null;
        }
        return object.toString();
    }

    /**
     * 组装数组为String
     *
     * @param params
     * @return
     */
    public static String pToString(Object ...params){
        if(params==null){
            return null;
        }
        StringBuffer res = new StringBuffer();
        for(int i=0;i<params.length;i++){
            String param = objectToString(params[i]);
            if(ObjectUtil.isEmpty(param)){
                param = "-";
            }
            if(i==params.length-1){
                res.append(param);
            }else{
                res.append(param).append("|");
            }
        }
        return res.toString();
    }
}
