package com.jiyou.nm.common.enums;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;

public enum RelationFieldEnum {
    BOTTLE_INNER_CODE("bottle_inner_code","瓶内码","一级内码"),
    BOTTLE_OUTER_CODE("bottle_outer_code","瓶外码","一级外码"),
    BOX_OUTER_CODE("box_outer_code","盒外码","二级外码"),
    BOX_INNER_CODE("box_inner_code","盒内码","二级内码"),
    PACKAGE_CODE("package_code","外箱码","三级外码"),
    PACKAGE_INNER_CODE("package_inner_code","内箱码","三级内码"),
    BIGPK_OUTER_CODE("bigpk_outer_code","大箱外码","四级外码"),
    BIGPK_INNER_CODE("bigpk_inner_code","大箱内码","四级内码"),
    STACK_CODE("stack_code","跺码",""),
    REAL_STACK_CODE("real_stack_code","实体跺码",""),
    ;
	@Getter
    private  String code;
	@Getter
    private  String Message;
	@Getter
    private String remark;
	
    RelationFieldEnum(String code, String message,String remark){
        this.code = code;
        this.Message = message;
        this.remark = remark;
    }
    
    
    public static final Boolean isLevelCodeTypeEnum(String code) {
		if (code != null) {
			return enumMap.get(code) != null;
		}
		return Boolean.FALSE;
	}

	public static final String getRemarkByCode(String code) {
		if (code != null) {
			RelationFieldEnum delFlagEnum = enumMap.get(code);
			if (null != delFlagEnum) {
				return delFlagEnum.getRemark();
			}
		}
		return "";
	}

	public static final RelationFieldEnum getEnumByCode(String code) {
		return enumMap.get(code);
	}

	private static Map<String, RelationFieldEnum> enumMap = null;

	public static Map<String, RelationFieldEnum> getEnumMap() {
		return enumMap;
	}

	static {
		Map<String, RelationFieldEnum> tempMap = Maps.newHashMap();
		for (RelationFieldEnum item : RelationFieldEnum.values()) {
			tempMap.put(item.getCode(), item);
		}
		enumMap = Collections.unmodifiableMap(tempMap);
	}
}
