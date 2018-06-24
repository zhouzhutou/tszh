package com.tszh.service.impl;

import com.tszh.dao.AddressDao;
import com.tszh.entity.Address;
import com.tszh.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/21 0021.
 */
@Service("addressService")
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDao<Address> addressDao;

    @Override
    public Serializable save(Address address) {
        return addressDao.save(address);
    }

    @Override
    public void update(Address address){
        addressDao.update(address);
    }
}
