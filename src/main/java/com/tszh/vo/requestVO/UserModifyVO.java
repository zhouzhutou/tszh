package com.tszh.vo.requestVO;

import com.tszh.custom_validator.DateV1;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by Administrator on 2018/5/31 0031.
 */
public class UserModifyVO {

    @NotNull(message = "{UserModifyVO.id.not.null}")
    private Integer id;

    @NotBlank(message = "{UserModifyVO.email.not.blank}")
    private String email;

    @NotBlank(message = "{UserModifyVO.username.not.blank}")
    @Length(max = 32,message = "{UserModifyVO.username.max.length}")
    private String username;

    @NotNull(message = "{UserModifyVO.sex.not.null}")
    private Byte sex;

    @DateV1(message = "{UserModifyVO.birthday.illegal.format}")
    private String birthday;

    @Pattern(regexp = "^((0\\d{2,3}-\\d{7,8})|(1[3584]\\d{9})|\\s*)$",message = "{UserModifyVO.phone.illegal.format}")
    private String phone;

    @Valid
    private AddressVO addressVO;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
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

    public AddressVO getAddressVO() {
        return addressVO;
    }

    public void setAddressVO(AddressVO addressVO) {
        this.addressVO = addressVO;
    }
}
