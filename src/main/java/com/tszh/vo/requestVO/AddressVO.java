package com.tszh.vo.requestVO;

import org.hibernate.validator.constraints.Length;

/**
 * Created by Administrator on 2018/5/31 0031.
 */
public class AddressVO {

    @Length(max = 128,message = "{AddressVO.province.max.length}")
    private String province;

    @Length(max = 128,message = "{AddressVO.city.max.length}")
    private String city;

    @Length(max = 128,message = "{AddressVO.county.max.length}")
    private String county;

    @Length(max = 128,message = "{AddressVO.street.max.length}")
    private String street;

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
