package com.tszh.service.impl;


import com.tszh.dao.RoleDao;
import com.tszh.entity.Role;
import com.tszh.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Role getRoleByName(String roleName) {
        String hgl="from Role r where r.roleName=? and r.deleted=false";
        List<Object> parameters=new ArrayList<>();
        parameters.add(roleName);
        return roleDao.get(hgl,parameters);
    }
}
