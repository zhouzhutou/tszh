package com.tszh.service;

import com.tszh.entity.ExchangeBook;
import com.tszh.entity.ExchangeItem;
import com.tszh.entity.User;
import com.tszh.vo.BootstrapTableResult;
import com.tszh.vo.requestVO.BookSearchVO;
import com.tszh.vo.requestVO.RegisterVO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/14 0014.
 */
public interface ExchangeBookService {

    /**
     * 保存用于置换的图书
     * @param exchangeBook
     * @return
     */
    public Serializable save(ExchangeBook exchangeBook);

    /**
     * 修改用于置换的图书
     * @param exchangeBook
     */
    public void update(ExchangeBook exchangeBook);
    /**
     * 根据id值获取置换的图书
     * @param id
     * @return
     */
    public ExchangeBook getExchangeBookById(int id);

    /**
     * 根据图书id删除图书
     * @param id
     */
    public void deleteExchangeBookById(int id);

    /**
     * 根据图书id列表删除图书
     * @param ids
     * @return
     */
    public int deleteExchangeBooksByIds(List<Integer> ids);

    /**
     * 按条件获取数据的总数
     * @param bookSearchVO
     * @param user
     * @return
     */
    public long countExchangeBook(BookSearchVO bookSearchVO,User user);

    /**
     * 按条件查询书籍并返回书籍列表
     * @param bookSearchVO
     * @param user
     * @return
     */
    public List<ExchangeBook> searchExchangeBook(BookSearchVO bookSearchVO,User user);

    /**
     * 根据图书名称和作者来查找置换书籍
     * @param bookName
     * @param author
     * @return
     */
    List<ExchangeBook> findByBookNameAndAuthor(String bookName,String author);

    /**
     * 根据图书ISBN查找置换书籍
     * @param isbn
     * @return
     */
    List<ExchangeBook> findByIsbn(String isbn);

    /**
     * 根据id判断图书可否置换
     * @param id
     * @return
     */
    ExchangeBook findCanExchangeBookById(int id);

    /**
     * 根据id查找可置换图书（fetch User）
     * @param id
     * @return
     */
    public ExchangeBook findCanExchangeBookFetchUserById(int id);

}
