package com.tszh.service.impl;


import com.tszh.dao.UserDao;
import com.tszh.entity.Role;
import com.tszh.entity.User;
import com.tszh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/20 0020.
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao<User> userDao;

    @Override
    public Serializable save(User user) {
        return userDao.save(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public User findUserByEmail(String email) {
        String hql="from User u inner join fetch u.role r inner join fetch r.permissions where u.email=?";
        List<Object> parameters=new ArrayList<>();
        parameters.add(email);
        return userDao.get(hql,parameters);
    }

    @Override
    public Role getRoleByEmail(String email) {
        String hql="from User u inner join fetch u.role r inner join fetch r.permissions p where u.email=?";
        List<Object> parameters=new ArrayList<>();
        parameters.add(email);
        User user=userDao.get(hql,parameters);
        return user.getRole();
    }
}
