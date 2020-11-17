package com.jiyou.nm.common.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 描述： es 地理位置信息格式
 * 作者： 张恒同
 * 时间： 2019-10-21   15:55
 */
@Data
public class EsAddressData {

    /**
     * 具体存储es格式如下
     * ps: location mapping为geo_point类型
     * <p>
     * {
     * "city_text":"济南",
     * "district_text":"历下区",
     * "code":[
     * "370000",
     * "370100",
     * "370101"
     * ],
     * "province_text":"山东",
     * "district_code":"370101",
     * "city_code":"370100",
     * "text":[
     * "山东",
     * "济南",
     * "历下区"
     * ],
     * "province_code":"370000",
     * "value":"山东省济南市历下区",
     * "location":{
     * "lon":119.033625,
     * "lat":36.713915
     * }
     * }
     */

    private String province_text;
    private String province_code;
    private String city_code;
    private String city_text;
    private String district_text;
    private String district_code;
    private List<String> code;
    private List<String> text;
    private String value;
    private Location location;

    @Data
    @NoArgsConstructor
    public static class Location {
        private Double lon;
        private Double lat;

        public Location(Double lon, Double lat) {
            this.lon = lon;
            this.lat = lat;
        }
    }

}
