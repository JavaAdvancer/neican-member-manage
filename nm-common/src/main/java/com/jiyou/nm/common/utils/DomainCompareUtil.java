package com.jiyou.nm.common.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiyou.nm.common.annotation.LogShow;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DomainCompareUtil {


    /**
     * 比较两个BEAN对象的值，返回差异结果
     * 只比较标注了@Equails注解的字段
     * 盖该注解的value值代表差异结果中字段描述
     * 例如：
     * 品牌名称:[测试1->测试1改]、备注:[备注1->备注1改]
     *
     * @param source
     * @param target
     * @return
     */
    public static String compare(Object source, Object target) {
        String equalsRs="";

        if (source != null || target != null) {
            equalsRs=classOfSrc(source, target);
        }
        return equalsRs;
    }

    /**
     * 源目标为MAP类型时
     *
     * @param source
     * @param target
     * @param rv
     * @return
     */
    private static boolean mapOfSrc(Object source, Object target, boolean rv) {
        HashMap<String, String> map=new HashMap<String, String>();
        map=(HashMap) source;
        for (String key : map.keySet()) {
            if (target instanceof Map) {
                HashMap<String, String> tarMap=new HashMap<String, String>();
                tarMap=(HashMap) target;
                if (tarMap.get(key) == null) {
                    rv=false;
                    break;
                }
                if (!map.get(key).equals(tarMap.get(key))) {
                    rv=false;
                    break;
                }
            } else {
                String tarValue=getClassValue(target, key) == null ? "" : getClassValue(target, key).toString();
                if (!tarValue.equals(map.get(key))) {
                    rv=false;
                    break;
                }
            }
        }
        return rv;
    }

    /**
     * 源目标为非MAP类型时
     *
     * @param source
     * @param target
     * @return
     */
    private static String classOfSrc(Object source, Object target) {
        StringBuffer equalsRs=new StringBuffer("");
        Class<?> srcClass=source.getClass();
        Field[] fields=srcClass.getDeclaredFields();
        for (Field field : fields) {
            String nameKey=field.getName();
            //跳过不参与比较的属性
            LogShow annotation=field.getAnnotation(LogShow.class);
            if (annotation == null) {
                continue;
            }
            //获取字典map
            HashMap<String, String> dictMap=new HashMap<>();
            LogShow.Dict[] dicts=annotation.dicts();
            for (int i=0; i < dicts.length; i++) {
                dictMap.put(dicts[i].code(), dicts[i].text());
            }
            Object srcValue=getClassValue(source, nameKey) == null ? "" : getClassValue(source, nameKey).toString();
            Object tarValue=getClassValue(target, nameKey) == null ? "" : getClassValue(target, nameKey).toString();
            if (!srcValue.equals(tarValue)) {
                String srcValueStr=srcValue.toString();
                String tarValueStr=tarValue.toString();
                if (!dictMap.isEmpty()) {
                    srcValueStr=dictMap.get(srcValueStr);
                    tarValueStr=dictMap.get(tarValueStr);
                }
                equalsRs.append(String.format("%s:[%s->%s]、", annotation.value(), srcValueStr, tarValueStr));
            }
          /*  if (srcValue instanceof String) {

            } else if(srcValue instanceof Number) {
                if (srcValue != tarValue) {
                    equalsRs.append(String.format("%s:[%s->%s]、", annotation.value(), srcValue.toString(), tarValue.toString()));
                }
            }*/
        }
        if (StringUtils.isNotBlank(equalsRs)) {
            return equalsRs.substring(0, equalsRs.length() - 1);
        } else {
            return "";
        }
    }


    /**
     * 获取传入对象值描述
     * 例如：姓名:张三;性别:男
     *
     * @param target
     * @return
     */
    public static String getObjectValueDes(Object target) {
        StringBuffer equalsRs=new StringBuffer("");
        Class<?> srcClass=target.getClass();
        Field[] fields=srcClass.getDeclaredFields();
        for (Field field : fields) {
            String nameKey=field.getName();
            //跳过不参与比较的属性
            LogShow logShowAnno=field.getAnnotation(LogShow.class);
            if (logShowAnno == null) {
                continue;
            }
            JsonFormat jsonFormatAnno=field.getAnnotation(JsonFormat.class);//日期格式化标志
            Object tarValue=getClassValue(target, nameKey) == null ? "" : getClassValue(target, nameKey);
            String tarValueFormat = tarValue.toString();
            if (tarValue instanceof Date) {
                //包含日期序列化格式
                if (jsonFormatAnno != null && StringUtils.isNotBlank(jsonFormatAnno.pattern())) {
                    SimpleDateFormat dateYmdFormat=new SimpleDateFormat("yyyy-MM-dd");
                    tarValueFormat =  dateYmdFormat.format((Date)tarValue);
                } else {
                    tarValueFormat = tarValue.toString();
                }
            }
            equalsRs.append(String.format("%s:%s;", logShowAnno.value(), tarValueFormat));
        }
        return equalsRs.substring(0, equalsRs.length() - 1);
    }

    /**
     * 根据字段名称取值
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getClassValue(Object obj, String fieldName) {
        if (obj == null) {
            return null;
        }
        try {
            Class beanClass=obj.getClass();
            Method[] ms=beanClass.getMethods();
            for (int i=0; i < ms.length; i++) {
                // 非get方法不取
                if (!ms[i].getName().startsWith("get")) {
                    continue;
                }
                Object objValue=null;
                try {
                    objValue=ms[i].invoke(obj, new Object[]{});
                } catch (Exception e) {
                    continue;
                }
                if (objValue == null) {
                    continue;
                }
                if (ms[i].getName().toUpperCase().equals(fieldName.toUpperCase()) || ms[i].getName().substring(3).toUpperCase().equals(fieldName.toUpperCase())) {
                    return objValue;
                } else if (fieldName.toUpperCase().equals("SID") && (ms[i].getName().toUpperCase().equals("ID") || ms[i].getName().substring(3).toUpperCase().equals("ID"))) {
                    return objValue;
                }
            }
        } catch (Exception e) {

        }
        return null;
    }


}
