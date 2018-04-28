package com.tszh.service;



import com.tszh.entity.Role;
import com.tszh.entity.User;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/20 0020.
 */
public interface UserService {

    /**
     * 保存用户信息
     * @param user
     * @return
     */
    public Serializable save(User user);

    /**
     * 更改用户信息
     * @param user
     */
    public void update(User user);

    /**
     * 根据邮件地址查找用户信息
     * @param email
     * @return
     */
    public User findUserByEmail(String email);

    /**
     * 根据邮件地址查找用户的角色和权限信息
     * @param email
     * @return
     */
    public Role getRoleByEmail(String email);

}
