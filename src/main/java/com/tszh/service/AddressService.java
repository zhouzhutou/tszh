package com.tszh.service;



import com.tszh.entity.Address;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/21 0021.
 */
public interface AddressService {
    /**
     * 保存地址
     * @param address
     * @return
     */
    public Serializable save(Address address);

    /**
     * 修改地址
     * @param address
     */
    public void update(Address address);
}
