package com.tszh.vo.responseVO;


import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Administrator on 2018/5/16 0016.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResGetBookTypesVO {

    private int id;

    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
