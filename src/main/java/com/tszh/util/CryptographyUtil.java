package com.tszh.util;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/4/21 0021.
 */
@Component
public class CryptographyUtil {


    //private static final String salt="tszh";

    public String md5(String string, ByteSource salt, int iter)
    {
        return new SimpleHash("MD5",string,salt,iter).toHex();
    }

}
