package com.tszh.service;



import com.tszh.entity.Role;
import com.tszh.entity.User;
import com.tszh.vo.requestVO.RechargeAccountVO;

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
     * 根据id查找用户
     * @param id
     * @return
     */
    public User findUserById(int id);

    /**
     * 根据邮件地址查找用户信息
     * @param email
     * @return
     */
    public User findUserByEmail(String email);

    /**
     * 根据昵称查找用户
     * @param username
     * @return
     */
    public User findUserByUserName(String username);

    /**
     * 根据邮件地址查找用户的角色和权限信息
     * @param email
     * @return
     */
    public Role getRoleByEmail(String email);


    /**
     * 查看某个邮箱的用户是否存在
     * @param email
     * @return
     */
    public int countByEmail(String email);

    /**
     * 通过email获取User的全部信息
     * @param email
     * @return
     */
    public User findAllUserInfoByEmail(String email);

    /**
     * 充值保证金
     * @param rechargeAccountVO
     * @return
     */
    public Integer addDeposit(RechargeAccountVO rechargeAccountVO);

}
