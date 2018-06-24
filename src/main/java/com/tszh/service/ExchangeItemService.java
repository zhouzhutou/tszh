package com.tszh.service;

import com.tszh.entity.ExchangeItem;
import com.tszh.entity.User;
import com.tszh.vo.requestVO.BookReadVO;
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
     * 根据id查找置换记录
     * @param id
     * @return
     */
    public ExchangeItem getExchangeItemById(String id);


    /**
     * 根据id删除置换记录
     * @param id
     */
    public void deleteExchangeItemById(String id);

    /**
     * 根据id通过置换记录
     * @param id
     */
    public int approveExchangeItemById(String id);

    /**
     * 根据id过期置换记录
     * @param id
     */
    public void expireExchangeItemById(String id);

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

    /**
     * 根据图书id查询交换条目
     * @param bookId
     * @return
     */
    public ExchangeItem findNotExpiredExchangeItemByBookId(int bookId);

    /**
     * 根据图书id列表查询交换条目
     * @param bookIds
     * @return
     */
    public List<ExchangeItem> findNotExpiredExchangeItemsByBookIds(List<Integer> bookIds);

    /**
     * 查询过期或者未过期的交换条目的总数
     * @param bookHaveReadVO
     * @return
     */
    public long countExchangeItemWithWishBook(BookReadVO bookHaveReadVO,User user);

    /**
     * 查询过期或者未过期的交换条目
     * @param bookHaveReadVO
     * @return
     */
    public List<ExchangeItem> findExchangeItemWithWishBook(BookReadVO bookHaveReadVO,User user);


}
