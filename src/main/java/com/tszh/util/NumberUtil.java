package com.tszh.util;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/5/18 0018.
 */
@Component
public class NumberUtil {

    private static final String POS_NUM_REGEX="^[1-9][0-9]*$";

    public boolean isPosNum(String str)
    {
        Pattern pattern=Pattern.compile(POS_NUM_REGEX);
        return pattern.matcher(str).matches();
    }
}

