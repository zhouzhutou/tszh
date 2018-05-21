package com.tszh.custom_validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2018/5/17 0017.
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateV1Impl.class)
public @interface DateV1 {

    String message() default "日期格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
