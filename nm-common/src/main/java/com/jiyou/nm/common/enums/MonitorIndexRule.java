package com.jiyou.nm.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * 告警規則-监控指标-规则
 */
public enum MonitorIndexRule {

    VERIF_AREA_DIFF("gt", "大于"),
    CROSS_DEALER_VERIF("gte", "大于等于");

    @Getter
    private String code;

    @Getter
    private String desc;

    MonitorIndexRule(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static final  String getDescByCode(String code){
        Optional<MonitorIndexRule> first = Arrays.stream(MonitorIndexRule.values()).filter(a -> a.getCode().equals(code)).findFirst();
        if(first.isPresent()){
            return first.get().getDesc();
        }else{
            return "";
        }
    }
}
