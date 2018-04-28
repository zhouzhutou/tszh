package com.tszh.dao.impl;


import com.tszh.dao.UserDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018/4/20 0020.
 */
@Repository("userDao")
public class UserDaoImpl<User> extends BaseDaoImpl<User> implements UserDao<User> {
}

