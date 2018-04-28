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

}
