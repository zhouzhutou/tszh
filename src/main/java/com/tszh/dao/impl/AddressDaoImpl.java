package com.tszh.dao.impl;


import com.tszh.dao.AddressDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018/4/21 0021.
 */
@Repository("addressDao")
public class AddressDaoImpl<Address> extends BaseDaoImpl<Address> implements AddressDao<Address> {
}
