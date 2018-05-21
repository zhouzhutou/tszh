package com.tszh.service.impl;

import com.tszh.dao.WishBookDao;
import com.tszh.entity.WishBook;
import com.tszh.service.WishBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/17 0017.
 */
@Service("wishBookService")
public class WishBookServiceImpl implements WishBookService{

    @Autowired
    private WishBookDao<WishBook> wishBookDao;

    @Override
    public Serializable save(WishBook wishBook) {
        return wishBookDao.save(wishBook);
    }
}
