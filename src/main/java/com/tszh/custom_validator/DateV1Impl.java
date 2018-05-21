package com.tszh.custom_validator;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2018/5/17 0017.
 */
public class DateV1Impl  implements ConstraintValidator<DateV1, String> {


    public DateV1Impl() {
        super();
    }

    @Override
    public void initialize(DateV1 dateV1) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        if(StringUtils.isBlank(s))
            return true;
        try {
            sdf.parse(s);
        }catch (ParseException e)
        {
            return false;
        }
        return true;
    }
}
