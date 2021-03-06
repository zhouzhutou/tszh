package com.tszh.service;


import com.tszh.entity.Role;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/20 0020.
 */
public interface RoleService {

    /**
     * 保存一个角色
     * @param role
     * @return
     */
    public Serializable save(Role role);

    /**
     * 根据权限名获取权限实体
     * @param role
     * @return
     */
    public Role getRoleByName(String role);
}
