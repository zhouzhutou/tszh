package com.tszh.exception;

/**
 * Created by liuw on 16/11/20.
 *
 * 自定义异常
 */
public class CustomException extends RuntimeException {


    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * default constructor
     * @param msg 错误信息
     */
    public CustomException(String msg,int code) {
        super(msg);
        this.code=code;
    }
}
