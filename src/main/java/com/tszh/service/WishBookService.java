package com.tszh.service;

import com.tszh.entity.ExchangeBook;
import com.tszh.entity.WishBook;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/17 0017.
 */
public interface WishBookService {

    /**
     * 保存一本期望图书
     * @param wishBook
     * @return
     */
    public Serializable save(WishBook wishBook);
}
