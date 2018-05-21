package com.tszh.dao.impl;

import com.tszh.dao.ExchangeItemDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018/5/17 0017.
 */
@Repository("exchangeItemDao")
public class ExchangeItemDaoImpl<ExchangeItem> extends BaseDaoImpl<ExchangeItem> implements ExchangeItemDao<ExchangeItem> {
}
