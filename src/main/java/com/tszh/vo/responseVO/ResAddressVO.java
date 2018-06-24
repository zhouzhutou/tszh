package com.tszh.vo.responseVO;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Administrator on 2018/5/30 0030.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResAddressVO {

    private String province;

    private String city;

    private String county;

    private String street;

    public ResAddressVO() {
    }

    public ResAddressVO(String province, String city, String county, String street) {
        this.province = province;
        this.city = city;
        this.county = county;
        this.street = street;
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

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
