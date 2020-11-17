package com.jiyou.nm.common.config;

/**
 * 描述： 系统模板属性枚举
 * 作者： 张恒同
 * 时间： 2019-03-19   18:06
 */
public enum  ModelAttrEnum {
    INPUT("input"),
    SELECT("select"),
    MULTIPLESELECT("multipleSelect"),
    DATEPICKER("datePicker"),
    DATETIMEPICKER("dateTimePicker"),
    ADDRESSPICKER("addressPicker"),
    NUMBER("number"),
    MODEL("model"),
    INNER("inner"),
    DICT("dict");

    private final String type;

    ModelAttrEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    /*
     * 匹配操作码
     * */
    public static ModelAttrEnum matchOpType(String type) {
        for (ModelAttrEnum attrEnum : ModelAttrEnum.values()) {
            if (attrEnum.getType().equals(type)) {
                return attrEnum;
            }
        }
        return null;
    }

}
