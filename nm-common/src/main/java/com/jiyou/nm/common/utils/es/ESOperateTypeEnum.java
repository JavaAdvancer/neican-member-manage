package com.jiyou.nm.common.utils.es;

/**
 * 描述： Es操作类型
 * 作者： 张恒同
 * 时间： 2019-03-31   11:14
 */
public enum ESOperateTypeEnum {
    //大于
    GT("gt"),
    //大于等于
    GTE("gte"),
    //小于
    LT("lt"),
    //小于等于
    LTE("lte"),
    //区间两端都不闭合
    GTL("gtl"),
    //区间右边闭合
    GTLE("gtle"),
    //区间左边闭合
    GTEL("gtel"),
    //区间两端都闭合
    GTELE("gtele"),

    //等于
    EQUAL("equal"),
    //不等于
    NOT_EQUAL("not_equal"),
    //包含
    CONTAIN("contain"),
    //不包含
    NOT_CONTAIN("not_contain"),
    //为空
    EMPTY("empty"),
    //不为空
    NOT_EMPTY("not_empty"),
    //在范围内
    IN("in"),
    //不在范围内
    NOT_IN("not_in"),
    //至少一次
    AT_LEAST_ONCE("at_least_once"),
    //一次没有
    NOT_ONCE("not_once"),

    //limitTimeOperator时间限制时间
    YESTEDAY("yestday"),
    TODAY("today"),
    LASTWEEK("lastweek"),
    THISWEEK("thivweek"),
    LASTMONTH("lastmonth"),
    THISMONTH("thismonth"),
    LASTYEAR("lastyear"),
    THISYEAR("thisyear"),
    PAST_7DAY("past7day"),
    PAST_30DAY("past30day"),
    ONELINE2TODAY("online2today"),

    //指标操作类
    MAX("max"),
    MIN("min"),
    AVG("avg"),
    SUM("sum"),

    NULL("");

    private String type;

    ESOperateTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    /**
     * 匹配操作类型
     *
     * @param type
     * */
    public static ESOperateTypeEnum matchType(String type){
        for (ESOperateTypeEnum typeEnum : ESOperateTypeEnum.values()) {
            if (typeEnum.getType().equals(type)) {
                return typeEnum;
            }
        }
        return null;
    }

}
