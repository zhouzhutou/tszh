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
        String hql="from User u inner join fetch u.role r where u.email=? and u.deleted=false";
        List<Object> parameters=new ArrayList<>();
        parameters.add(email);
        return userDao.get(hql,parameters);
    }

    @Override
    public User findUserByUserName(String username) {
        String hql="from User u inner join fetch u.role r where u.username=? and u.deleted=false";
        List<Object> parameters=new ArrayList<>();
        parameters.add(username);
        return userDao.get(hql,parameters);
    }

    @Override
    public Role getRoleByEmail(String email) {
        String hql="from User u inner join fetch u.role r where u.email=? and u.deleted=false";
        List<Object> parameters=new ArrayList<>();
        parameters.add(email);
        User user=userDao.get(hql,parameters);
        return user.getRole();
    }

    @Override
    public int countByEmail(String email)
    {
        String hql="select count(*) from User u where u.email=? and u.deleted=false";
        List<Object> parameters=new ArrayList<>();
        parameters.add(email);
        return userDao.count(hql,parameters).intValue();
    }


}
