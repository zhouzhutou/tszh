package com.tszh.service.impl;

import com.tszh.cons.Code;
import com.tszh.dao.ExchangeBookDao;
import com.tszh.entity.ExchangeBook;
import com.tszh.entity.PageBean;
import com.tszh.entity.User;
import com.tszh.exception.CustomException;
import com.tszh.service.ExchangeBookService;
import com.tszh.vo.requestVO.BookSearchVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/14 0014.
 */
@Service("exchangeBookService")
@Transactional
public class ExchangeBookServiceImpl implements ExchangeBookService{

    @Autowired
    ExchangeBookDao<ExchangeBook> exchangeBookDao;

    @Override
    public Serializable save(ExchangeBook exchangeBook) {
        return exchangeBookDao.save(exchangeBook);
    }

    @Override
    public void update(ExchangeBook exchangeBook) {
        exchangeBookDao.update(exchangeBook);
    }

    @Override
    public ExchangeBook getExchangeBookById(int id) {
        String hql="from ExchangeBook eb where eb.id=? and eb.deleted=false";
        List<Object> params=new ArrayList<>();
        params.add(id);
        return exchangeBookDao.get(hql,params);
        //return exchangeBookDao.get(ExchangeBook.class,id);
    }

    @Override
    public void deleteExchangeBookById(int id) {
        ExchangeBook eb=getExchangeBookById(id);
        if(eb==null)
            throw new CustomException("id为"+id+"的图书不存在", Code.NOT_FOUND_ERROR.getCode());
        eb.setDeleted(true);
        exchangeBookDao.update(eb);
    }

    @Override
    public int deleteExchangeBooksByIds(List<Integer> ids) {
        if(ids==null || ids.size()==0)
            return 0;
        StringBuilder sb=new StringBuilder("update ExchangeBook eb set eb.deleted=true and eb.id in (:ids)");
        Map<String,List<?>> params=new HashMap<>();
        params.put("ids",ids);
        return exchangeBookDao.updateIn(sb.toString().replaceFirst("and","where"),params);
    }

    @Override
    public long countExchangeBook(BookSearchVO bookSearchVO,User user) {
        StringBuilder sb=new StringBuilder();
        List<Object> params=new ArrayList<>();
        sb.append("select count(*) from ExchangeBook eb");
        sb.append(" inner join eb.user u");
        if(!StringUtils.isBlank(bookSearchVO.getBookType())) {
            sb.append(" inner join eb.bookTypes ts");
        }
        if(user!=null && !StringUtils.isBlank(user.getEmail())){
            sb.append(" and u.email=?");
            params.add(user.getEmail());
        }
        if(!StringUtils.isBlank(bookSearchVO.getBookOwner())){
            sb.append(" and u.username like ?");
            params.add('%'+bookSearchVO.getBookOwner()+'%');
        }
        if(!StringUtils.isBlank(bookSearchVO.getBookType())){
            sb.append(" and ts.type like ?");
            params.add('%'+bookSearchVO.getBookType()+'%');
        }
        if(!StringUtils.isBlank(bookSearchVO.getBookName())) {
            sb.append(" and eb.bookName like ?");
            params.add('%'+bookSearchVO.getBookName()+'%');
        }
        if(!StringUtils.isBlank(bookSearchVO.getAuthor())) {
            sb.append(" and eb.author like ?");
            params.add('%'+bookSearchVO.getAuthor()+'%');
        }
        if(!StringUtils.isBlank(bookSearchVO.getIsbn())) {
            sb.append(" and eb.ISBN like ?");
            params.add('%'+bookSearchVO.getIsbn()+'%');
        }
        if(!StringUtils.isBlank(bookSearchVO.getPress())) {
            sb.append(" and eb.press like ?");
            params.add('%'+bookSearchVO.getPress()+'%');
        }
        if(bookSearchVO.getCanExchange()!=null){
            sb.append(" and eb.canExchange=?");
            params.add(bookSearchVO.getCanExchange());
        }
        sb.append(" and eb.deleted=false");
        PageBean pageBean=new PageBean(bookSearchVO.getPageNumber(),bookSearchVO.getPageSize());
        return exchangeBookDao.count(sb.toString().replaceFirst("and","where"),params);
    }

    @Override
    public List<ExchangeBook> searchExchangeBook(BookSearchVO bookSearchVO,User user) {
        StringBuilder sb=new StringBuilder();
        List<Object> params=new ArrayList<>();
        sb.append("select eb from ExchangeBook eb");
        sb.append(" inner join fetch eb.user u");
        if(!StringUtils.isBlank(bookSearchVO.getBookType())) {
            sb.append(" inner join fetch eb.bookTypes ts");
        }
        if(user!=null && !StringUtils.isBlank(user.getEmail())){
            sb.append(" and u.email=?");
            params.add(user.getEmail());
        }
        if(!StringUtils.isBlank(bookSearchVO.getBookOwner())){
            sb.append(" and u.username like ?");
            params.add('%'+bookSearchVO.getBookOwner()+'%');
        }
        if(!StringUtils.isBlank(bookSearchVO.getBookType())){
            sb.append(" and ts.type like ?");
            params.add('%'+bookSearchVO.getBookType()+'%');
        }
        if(!StringUtils.isBlank(bookSearchVO.getBookName())) {
            sb.append(" and eb.bookName like ?");
            params.add('%'+bookSearchVO.getBookName()+'%');
        }
        if(!StringUtils.isBlank(bookSearchVO.getAuthor())) {
            sb.append(" and eb.author like ?");
            params.add('%'+bookSearchVO.getAuthor()+'%');
        }
        if(!StringUtils.isBlank(bookSearchVO.getIsbn())) {
            sb.append(" and eb.ISBN like ?");
            params.add('%'+bookSearchVO.getIsbn()+'%');
        }
        if(!StringUtils.isBlank(bookSearchVO.getPress())) {
            sb.append(" and eb.press like ?");
            params.add('%'+bookSearchVO.getPress()+'%');
        }
        if(bookSearchVO.getCanExchange()!=null){
            sb.append(" and eb.canExchange=?");
            params.add(bookSearchVO.getCanExchange());
        }
        sb.append(" and eb.deleted=false order by eb.releaseDate desc");
        PageBean pageBean=new PageBean(bookSearchVO.getPageNumber(),bookSearchVO.getPageSize());
        return exchangeBookDao.find(sb.toString().replaceFirst("and","where"),params,pageBean);
    }

    @Override
    public List<ExchangeBook> findByBookNameAndAuthor(String bookName, String author) {
        String hql="from ExchangeBook eb where eb.bookName=? and eb.author=? and eb.deleted=false";
        List<Object> params=new ArrayList<>();
        params.add(bookName);
        params.add(author);
        return exchangeBookDao.find(hql,params);
    }

    @Override
    public List<ExchangeBook> findByIsbn(String isbn) {
       String hql="from ExchangeBook eb where eb.ISBN=? and eb.deleted=false";
       List<Object> params=new ArrayList<>();
       params.add(isbn);
       return exchangeBookDao.find(hql,params);
    }

    @Override
    public ExchangeBook findCanExchangeBookById(int id) {
        String hql="from ExchangeBook eb where eb.id=? and eb.canExchange=true and eb.deleted=false";
        List<Object> params=new ArrayList<>();
        params.add(id);
        return exchangeBookDao.get(hql,params);
    }

    @Override
    public ExchangeBook findCanExchangeBookFetchUserById(int id){
        String hql="from ExchangeBook eb inner join fetch eb.user u where eb.id=? and eb.canExchange=true and eb.deleted=false";
        List<Object> params=new ArrayList<>();
        params.add(id);
        return exchangeBookDao.get(hql,params);
    }
}
