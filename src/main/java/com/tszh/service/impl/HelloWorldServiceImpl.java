package com.tszh.service.impl;

import com.tszh.dao.HelloWolrdDao;
import com.tszh.entity.HelloWorld;
import com.tszh.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/16 0016.
 */
@Service("helloWorldService")
@Transactional
public class HelloWorldServiceImpl implements HelloWorldService {

    @Autowired
    private HelloWolrdDao<HelloWorld> helloWolrdDao;

    @Override
    public Serializable addHelloWord(HelloWorld helloWorld) {
        return helloWolrdDao.save(helloWorld);
    }
}
