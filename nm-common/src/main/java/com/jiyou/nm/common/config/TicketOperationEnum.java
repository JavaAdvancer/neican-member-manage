package com.jiyou.nm.common.config;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;

/**
 * @ClassName: TicketOperationEnum
 * @Description: 卡券操作enum
 * @author tanchao
 * @date 2019年9月17日
 *
 */
public enum TicketOperationEnum {

	GET_TICKET(1, "获得券"),
	VERIFIER_TICKET(2,"核销券");

	@Getter
	private int code;

	@Getter
	private String desc;

	TicketOperationEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static final String getDescByCode(int code) {
		TicketOperationEnum delFlagEnum = enumMap.get(code);
		if (null != delFlagEnum) {
			return delFlagEnum.getDesc();
		}
		return "";
	}

	public static final TicketOperationEnum getEnumByCode(int code) {
		return enumMap.get(code);
	}

	private static Map<Integer, TicketOperationEnum> enumMap = null;

	public static Map<Integer, TicketOperationEnum> getEnumMap() {
		return enumMap;
	}

	static {
		Map<Integer, TicketOperationEnum> tempMap = Maps.newHashMap();
		for (TicketOperationEnum item : TicketOperationEnum.values()) {
			tempMap.put(item.getCode(), item);
		}
		enumMap = Collections.unmodifiableMap(tempMap);
	}
}
