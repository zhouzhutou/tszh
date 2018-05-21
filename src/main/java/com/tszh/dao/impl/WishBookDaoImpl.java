package com.tszh.dao.impl;

import com.tszh.dao.ExchangeBookDao;
import com.tszh.dao.WishBookDao;
import com.tszh.entity.WishBook;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018/5/14 0014.
 */
@Repository("wishBookDao")
public class WishBookDaoImpl<WishBook> extends BaseDaoImpl<WishBook> implements WishBookDao<WishBook> {
}
