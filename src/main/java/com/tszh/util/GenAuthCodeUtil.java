package com.tszh.util;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by Administrator on 2018/5/7 0007.
 */
@Component
public class GenAuthCodeUtil {

    private static final String CHARACTERS="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * 生成随机数
     * @param c 随机数的位数
     * @return
     */
    public String getAuthCode(int c)
    {
        Random random = new Random(System.currentTimeMillis());
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<c;i++)
        {
            char ch=CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
            sb.append(ch);
        }
        return sb.toString();
    }
}
