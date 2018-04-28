package com.tszh.service.impl;

import com.tszh.dao.PermissionDao;
import com.tszh.entity.Permission;
import com.tszh.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/21 0021.
 */
@Service("permissionService")
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao<Permission> permissionDao;

    @Override
    public Serializable save(Permission permission) {
        return permissionDao.save(permission);
    }
}
