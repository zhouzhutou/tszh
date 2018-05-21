package com.tszh.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by Administrator on 2018/5/3 0003.
 */

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseTemplate<T> {

    private int code;

    private String message;

    private List<String> errors;

    private T data;



    public ResponseTemplate(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseTemplate(int code, List<String> errors){
        this.code=code;
        this.errors=errors;
    }

    public ResponseTemplate(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseTemplate(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
