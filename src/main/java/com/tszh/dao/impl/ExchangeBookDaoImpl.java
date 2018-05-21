package com.tszh.dao.impl;

import com.tszh.dao.ExchangeBookDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018/5/14 0014.
 */
@Repository("exchangeBookDao")
public class ExchangeBookDaoImpl<ExchangeBook> extends BaseDaoImpl<ExchangeBook> implements ExchangeBookDao<ExchangeBook>{
}
