package com.tszh.dao.impl;


import com.tszh.dao.PermissionDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018/4/21 0021.
 */
@Repository("permissionDao")
public class PermissionDaoImpl<Permission> extends BaseDaoImpl<Permission> implements PermissionDao<Permission> {

}
