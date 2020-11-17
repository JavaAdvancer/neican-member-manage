package com.jiyou.nm.common.entity;


/**
 * 描述：
 * 作者： 张恒同
 * 时间： 2019-06-26   13:13
 */
public class AMapRegoInfo {

    //地理位置es存储格式
    private EsAddressData esAddressData;

    private String  code;
    private String  province;
    private String  city;
    private String  district;
    private String  address;
    /**
     * 精度
     * */
    private Double  lon;
    /**
     * 纬度
     * */
    private Double  lat;

    public EsAddressData getEsAddressData() {
        return esAddressData;
    }

    public void setEsAddressData(EsAddressData esAddressData) {
        this.esAddressData = esAddressData;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}
