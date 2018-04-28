package com.tszh.service.impl;


import com.tszh.dao.RoleDao;
import com.tszh.entity.Role;
import com.tszh.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/21 0021.
 */
@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao<Role> roleDao;

    @Override
    public Serializable save(Role role) {
        return roleDao.save(role);
    }
}
