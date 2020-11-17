package com.jiyou.nm.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * 告警規則-監控指標
 */
public enum MonitorIndex {

    VERIF_AREA_DIFF(1, "品鉴酒核销区域与经销商经营区域不一致"),
    CROSS_DEALER_VERIF(2, "跨经销商扫码核销"),
    REACH_VERIF_LIMIT(3, "核销数量大于规定次数上限");

    @Getter
    private Integer id;

    @Getter
    private String desc;

    MonitorIndex(Integer id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public static final Integer matchByIdRs(Integer id) {
        Optional<MonitorIndex> first = Arrays.stream(MonitorIndex.values()).filter(a -> a.getId().equals(id)).findFirst();
        if (first.isPresent()) {
            return first.get().getId();
        } else {
            return 0;
        }
    }

    public Boolean matchById(Integer id) {
        return id.equals(this.id);
    }
    public static final  String getDescByCode(Integer id){
        Optional<MonitorIndex> first = Arrays.stream(MonitorIndex.values()).filter(a -> a.getId().equals(id)).findFirst();
        if(first.isPresent()){
            return first.get().getDesc();
        }else{
            return "";
        }

    }
}
