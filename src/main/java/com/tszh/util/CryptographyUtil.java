package com.tszh.util;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/4/21 0021.
 */
@Component
public class CryptographyUtil {


    //private static final String salt="tszh";

    public String md5(String string,String salt)
    {
        return new Md5Hash(string,salt).toString();
    }

}
