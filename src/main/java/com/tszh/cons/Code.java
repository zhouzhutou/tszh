package com.tszh.cons;

/**
 * Created by Administrator on 2018/5/3 0003.
 */
public enum Code {

    //成功
    SUCCESS(2000),

    //邮箱或密码错误
    EMAIL_OR_PASSWORD_ERROR(2001),

    //邮箱已被注册
    EMAIL_EXIST_ERROR(2002),

    //邮箱已被注册
    USERNAME_EXIST_ERROR(2003),

    //注册异常错误
    REGISTER_ERROR(2004),

    //参数校验错误
    METHOD_ARGUMENT_ERROR(2005),

    //参数校验错误
    CONSTRAINT_VIOlATION_ERROR(2006),

    //数据绑定错误
    DATA_BIND_ERROR(2007),

    //缺少请求参数错误
    MISS_SERVLET_REQUEST_PARAMETER_ERROR(2008),

    //操作数据库出现错误:名称重复，外键关联
    DATA_INTEGRITY_VIOLATION_ERROR(2009),

    //拒绝访问
    ACCESS_DENIED_ERROR(2010),

    //没有可访问资源
    NOT_FOUND_ERROR(2011),

    //访问方法不支撑错误
    HTTP_REQUESTMETHOD_NOT_SUPPORTED_ERROR(2012),

    //MIME类型不支持
    HTTP_MEDIA_TYPE_NOT_SUPPORTED(2013),

    //服务器异常错误
    RUNTIME_ERROR(2014);


    private int code;

    Code(int code) {
        this.code = code;
    }

    public int getCode()
    {
        return code;
    }

    @Override
    public String toString() {
        return String.valueOf(code);
}
}
