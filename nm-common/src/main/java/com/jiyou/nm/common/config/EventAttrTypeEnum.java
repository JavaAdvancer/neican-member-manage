package com.jiyou.nm.common.config;

/**
 * 描述： 事件属性类型
 * 作者： 张恒同
 * 时间： 2019-10-22   14:54
 */
public enum  EventAttrTypeEnum {

    MONEY("money"),
    NUMBER("number"),
    TXT("txt"),
    DICTIONARY("dictionary"),
    LOCATION("location");

    private final String type;

    EventAttrTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    /*
     * 匹配类型码
     * */
    public static EventAttrTypeEnum matchOpType(String type) {
        for (EventAttrTypeEnum attrEnum : EventAttrTypeEnum.values()) {
            if (attrEnum.getType().equals(type)) {
                return attrEnum;
            }
        }
        return null;
    }

}
