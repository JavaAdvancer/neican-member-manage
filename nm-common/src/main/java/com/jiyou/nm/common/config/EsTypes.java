package com.jiyou.nm.common.config;

import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述： es数据类型
 * 作者： 张恒同
 * 时间： 2019-03-28   14:17
 */
public interface EsTypes {

    String TEXT = "text";
    String INTEGER = "integer";
    String LONG = "long";
    String SHORT = "short";
    String BYTE = "byte";
    String DOUBLE = "double";
    String FLOAT = "float";
    String DATE = "date";
    String BOOL = "boolean";
    String BINARY = "binary";
    String OBJECT = "object";
    String NESTED = "nested";
    String GEOPOINT = "geo_point";
    String GEOSHAPE = "geo_shape";
    String IP = "ip";
    String COMPLETION = "completion";
    String TOKENCOUNT = "token_count";
    String JOIN = "join";

    public static Map createTextKeyWord() {
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("type", EsTypes.TEXT);
        map.put("fields", Collections.singletonMap("keyword", Collections.singletonMap("type", "keyword")));
        return map;
    }

	public static Map createText() {
		HashMap<String, Object> map = Maps.newHashMap();
		map.put("type", EsTypes.TEXT);
		return map;
	}

	public static Map createGeoPoint() {
		HashMap<String, Object> map = Maps.newHashMap();
		map.put("type", EsTypes.GEOPOINT);
		return map;
	}

    /**
     * 自定义location mapping定义
     * @return
     */
	public static Map createLocationMapping() {
		Map<String, Object> propertiesMapping = new HashMap<>(10);
		propertiesMapping.put("province_text", EsTypes.createTextKeyWord());
		propertiesMapping.put("city_text", EsTypes.createTextKeyWord());
		propertiesMapping.put("district_text", EsTypes.createTextKeyWord());
		propertiesMapping.put("province_code", EsTypes.createTextKeyWord());
		propertiesMapping.put("city_code", EsTypes.createTextKeyWord());
		propertiesMapping.put("district_code", EsTypes.createTextKeyWord());
		propertiesMapping.put("code", EsTypes.createText());
		propertiesMapping.put("text", EsTypes.createText());
		propertiesMapping.put("value", EsTypes.createTextKeyWord());
		propertiesMapping.put("location", EsTypes.createGeoPoint());
		return Collections.singletonMap("properties", propertiesMapping);
	}
    
    /**
     * 时间类型创建默认mapping
     * <p>
     * year - 年份
     * month - 月份
     * day - 月天数
     * hour - 小时
     * dayOfWeek - 周几
     * monthLength - 当月的天数
     * isLastDayOfMonth - 是否是每月最后一天
     * value - 常规日期格式
     * timestamp - 时间戳
     */
	public static Map createTimeJSONObject() {
		HashMap<String, Object> map = Maps.newHashMap();
		map.put("dayOfWeek", Collections.singletonMap("type", EsTypes.INTEGER));
		map.put("month", Collections.singletonMap("type", EsTypes.INTEGER));
		map.put("hour", Collections.singletonMap("type", EsTypes.INTEGER));
		map.put("year", Collections.singletonMap("type", EsTypes.INTEGER));
		map.put("day", Collections.singletonMap("type", EsTypes.INTEGER));
		map.put("monthLength", Collections.singletonMap("type", EsTypes.INTEGER));
		map.put("isLastDayOfMonth", Collections.singletonMap("type", EsTypes.SHORT));
		map.put("value", Collections.singletonMap("type", EsTypes.DATE));
		map.put("timestamp", Collections.singletonMap("type", EsTypes.LONG));
		return Collections.singletonMap("properties", map);
	}

}
