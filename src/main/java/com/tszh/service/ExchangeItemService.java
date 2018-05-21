package com.tszh.service;

import com.tszh.entity.ExchangeItem;
import com.tszh.entity.User;
import com.tszh.vo.requestVO.ExchangeBookItemSearchVO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/17 0017.
 */
public interface ExchangeItemService {

    /**
     * 保存一个置换申请
     * @param exchangeItem
     * @return
     */
    public Serializable save(ExchangeItem exchangeItem);

    /**
     * 按条件获取数据的总数
     * @param exchangeBookItemSearchVO
     * @param user
     * @return
     */
    public long countExchangeItem(ExchangeBookItemSearchVO exchangeBookItemSearchVO,User user);

    /**
     * 按条件查询申请条目并返回申请条目列表
     * @param exchangeBookItemSearchVO
     * @param user
     * @return
     */
    public List<ExchangeItem> searchExchangeBookItem(ExchangeBookItemSearchVO exchangeBookItemSearchVO,User user);
}
