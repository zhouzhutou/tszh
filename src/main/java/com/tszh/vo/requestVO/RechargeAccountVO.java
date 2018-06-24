package com.tszh.vo.requestVO;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by Administrator on 2018/6/1 0001.
 */
public class RechargeAccountVO {

    @NotNull(message = "{RechargeAccountVO.id.not.null}")
    private Integer id;

    @Digits(integer = 10,fraction = 2,message = "{RechargeAccountVO.rechargeDeposit.illegal.format}")
    private Float rechargeDeposit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getRechargeDeposit() {
        return rechargeDeposit;
    }

    public void setRechargeDeposit(Float rechargeDeposit) {
        this.rechargeDeposit = rechargeDeposit;
    }
}
