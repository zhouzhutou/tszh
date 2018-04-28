package com.tszh.dao.impl;


import com.tszh.dao.RoleDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018/4/21 0021.
 */
@Repository("roleDao")
public class RoleDaoImpl<Role> extends BaseDaoImpl<Role> implements RoleDao<Role> {
}
