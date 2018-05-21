package com.tszh.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/5/7 0007.
 */
@Component
public class DateUtil {

    public String formatDate(Date date,String format)
    {
        if(date==null)
            return null;
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public Date parse(String dateStr,String format)throws ParseException
    {
        if(StringUtils.isBlank(dateStr))
            return null;
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        return sdf.parse(dateStr);
    }

}
