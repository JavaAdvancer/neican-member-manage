package com.jiyou.nm.common.config;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;

public enum TicketPeriodValidityEnum {

	DAYS(0,"天数限定"),
	SCOPE(1, "范围"),
	LONG_TIME(2,"长期有效");

	@Getter
	private Integer type;
	@Getter
	private String desc;

	TicketPeriodValidityEnum(Integer type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public static final String getDescByCode(int type) {
		TicketPeriodValidityEnum delFlagEnum = enumMap.get(type);
		if (null != delFlagEnum) {
			return delFlagEnum.getDesc();
		}
		return "";
	}

	public static final TicketPeriodValidityEnum getEnumByType(int type) {
		return enumMap.get(type);
	}

	private static Map<Integer, TicketPeriodValidityEnum> enumMap = null;

	public static Map<Integer, TicketPeriodValidityEnum> getEnumMap() {
		return enumMap;
	}

	static {
		Map<Integer, TicketPeriodValidityEnum> tempMap = Maps.newHashMap();
		for (TicketPeriodValidityEnum item : TicketPeriodValidityEnum.values()) {
			tempMap.put(item.getType(), item);
		}
		enumMap = Collections.unmodifiableMap(tempMap);
	}


}
