package com.tszh.vo.responseVO;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Administrator on 2018/5/30 0030.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResGetMyAccountInfoVO {

    private int id;

    private String email;

    private String username;

    private byte sex;

    private String birthday;

    private String phone;

    private ResAddressVO address;

    private float deposit;

    public ResGetMyAccountInfoVO(){
    }

    public ResGetMyAccountInfoVO(int id, String email, String username, byte sex, String birthday, String phone, ResAddressVO address, float deposit) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.sex = sex;
        this.birthday = birthday;
        this.phone = phone;
        this.address = address;
        this.deposit = deposit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ResAddressVO getAddress() {
        return address;
    }

    public void setAddress(ResAddressVO address) {
        this.address = address;
    }

    public float getDeposit() {
        return deposit;
    }

    public void setDeposit(float deposit) {
        this.deposit = deposit;
    }
}
