package com.tszh.service;

import com.tszh.entity.Permission;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/20 0020.
 */
public interface PermissionService {

    /**
     * 保存一个权限
     * @param permission
     * @return
     */
    public Serializable save(Permission permission);

}
