package com.jiyou.nm.common.config;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;

/**
 * @ClassName: TicketTypeEnum
 * @Description: 卡券类型
 * @author tanchao
 * @date 2019年9月19日
 *
 */
public enum TicketTypeEnum {

	COMMODITY_CONVERSION(1, "实物兑换券"),
	ACTIVITY(2,"活动券"),
	CASH_COUPON(3,"代金券");

	@Getter
	private Integer type;
	@Getter
	private String desc;

	TicketTypeEnum(Integer type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public static final String getDescByCode(int type) {
		TicketTypeEnum delFlagEnum = enumMap.get(type);
		if (null != delFlagEnum) {
			return delFlagEnum.getDesc();
		}
		return "";
	}

	public static final TicketTypeEnum getEnumByType(int type) {
		return enumMap.get(type);
	}

	private static Map<Integer, TicketTypeEnum> enumMap = null;

	public static Map<Integer, TicketTypeEnum> getEnumMap() {
		return enumMap;
	}

	static {
		Map<Integer, TicketTypeEnum> tempMap = Maps.newHashMap();
		for (TicketTypeEnum item : TicketTypeEnum.values()) {
			tempMap.put(item.getType(), item);
		}
		enumMap = Collections.unmodifiableMap(tempMap);
	}
}
