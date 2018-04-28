package com.tszh.dao.impl;


import com.tszh.dao.HelloWolrdDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018/4/16 0016.
 */
@Repository("helloWorldDao")
public class HelloWorldDaoImpl<HelloWorld> extends BaseDaoImpl<HelloWorld> implements HelloWolrdDao<HelloWorld> {

}
