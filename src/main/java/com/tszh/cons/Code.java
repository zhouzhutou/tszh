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

    //邮箱未被注册
    EMAIL_NOT_EXIST_ERROR(2003),

    //用户名已被注册
    USERNAME_EXIST_ERROR(2004),

    //验证码已过期
    VERIFY_CODE_EXPIRED_ERROR(2005),

    //验证码不匹配
    VERIFY_CODE_NOT_MATCH_ERROR(2006),

    //注册异常错误
    REGISTER_ERROR(2007),

    //参数校验错误
    METHOD_ARGUMENT_ERROR(2008),

    //参数校验错误
    CONSTRAINT_VIOlATION_ERROR(2009),

    //数据绑定错误
    DATA_BIND_ERROR(2010),

    //缺少请求参数错误
    MISS_SERVLET_REQUEST_PARAMETER_ERROR(2011),

    //操作数据库出现错误:名称重复，外键关联
    DATA_INTEGRITY_VIOLATION_ERROR(2012),

    //拒绝访问
    ACCESS_DENIED_ERROR(2013),

    //没有可访问资源
    NOT_FOUND_ERROR(2014),

    //访问方法不支撑错误
    HTTP_REQUESTMETHOD_NOT_SUPPORTED_ERROR(2015),

    //MIME类型不支持
    HTTP_MEDIA_TYPE_NOT_SUPPORTED(2016),

    //服务器异常错误
    RUNTIME_ERROR(2017),

    //数据库访问错误
    DATABASE_ACCESS_ERROR(2018),

    //数据库拒绝访问
    DATABASE_ACCESS_DENIED_ERROR(2019);

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
