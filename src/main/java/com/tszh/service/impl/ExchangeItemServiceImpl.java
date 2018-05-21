package com.tszh.service.impl;

import com.tszh.dao.ExchangeItemDao;
import com.tszh.entity.ExchangeItem;
import com.tszh.entity.PageBean;
import com.tszh.entity.User;
import com.tszh.service.ExchangeItemService;
import com.tszh.util.DateUtil;
import com.tszh.vo.requestVO.ExchangeBookItemSearchVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.text.ParseException;
import java.util.*;

/**
 * Created by Administrator on 2018/5/17 0017.
 */
@Service("exchangeItemService")
public class ExchangeItemServiceImpl implements ExchangeItemService{

    @Autowired
    DateUtil dateUtil;

    @Autowired
    private ExchangeItemDao<ExchangeItem> exchangeItemDao;

    @Override
    public Serializable save(ExchangeItem exchangeItem) {
        return exchangeItemDao.save(exchangeItem);
    }

    @Override
    public long countExchangeItem(ExchangeBookItemSearchVO exchangeBookItemSearchVO,User user) {
        StringBuilder sb=new StringBuilder();
        List<Object> params=new ArrayList<>();
        sb.append("select count(*) from ExchangeItem ei");
        if(!StringUtils.isBlank(user.getEmail())){
            sb.append(" inner join ei.user u");
            //params.add(user.getEmail());
        }
        if(!StringUtils.isBlank(exchangeBookItemSearchVO.getBookName1())) {
            sb.append(" inner join ei.exchangeBook eb");
            //params.add('%'+exchangeBookItemSearchVO.getBookName1()+'%');
        }
        if(!StringUtils.isBlank(exchangeBookItemSearchVO.getBookName2())){
            sb.append(" inner join ei.wishBook wb");
            //params.add('%'+exchangeBookItemSearchVO.getBookName2()+'%');
        }
        if(!StringUtils.isBlank(user.getEmail())){
            sb.append(" and u.email=?");
            params.add(user.getEmail());
        }
        if(!StringUtils.isBlank(exchangeBookItemSearchVO.getBookName1())){
            sb.append(" and eb.bookName like ?");
            params.add('%'+exchangeBookItemSearchVO.getBookName1()+'%');
        }
        if(!StringUtils.isBlank(exchangeBookItemSearchVO.getBookName2())){
            sb.append(" and wb.bookName like ?");
            params.add('%'+exchangeBookItemSearchVO.getBookName2()+'%');
        }

        if(!StringUtils.isBlank(exchangeBookItemSearchVO.getId())){
            sb.append(" and ei.id like ?");
            params.add('%'+exchangeBookItemSearchVO.getId()+'%');
        }
        if(!StringUtils.isBlank(exchangeBookItemSearchVO.getApplicationDate())){
            Date begin=null;
            try {
                begin=dateUtil.parse(exchangeBookItemSearchVO.getApplicationDate(),"yyyy-MM-dd");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar calendar=new GregorianCalendar();
            calendar.setTime(begin);
            calendar.add(calendar.DATE,1);
            Date end=calendar.getTime();
            sb.append(" and ei.applicationDate>=? and ei.applicationDate<?");
            params.add(begin);
            params.add(end);
        }
        if(exchangeBookItemSearchVO.getStatus()!=null){
            sb.append(" and ei.status=?");
            params.add(exchangeBookItemSearchVO.getStatus());
        }
        sb.append(" and ei.deleted=false");
        return exchangeItemDao.count(sb.toString().replaceFirst("and","where"),params);
    }

    @Override
    public List<ExchangeItem> searchExchangeBookItem(ExchangeBookItemSearchVO exchangeBookItemSearchVO,User user) {
        StringBuilder sb=new StringBuilder();
        List<Object> params=new ArrayList<>();
        sb.append("select ei from ExchangeItem ei");
        if(!StringUtils.isBlank(user.getEmail())){
            sb.append(" inner join fetch ei.user u");
            //params.add(user.getEmail());
        }
        if(!StringUtils.isBlank(exchangeBookItemSearchVO.getBookName1())) {
            sb.append(" inner join fetch ei.exchangeBook eb");
            //params.add('%'+exchangeBookItemSearchVO.getBookName1()+'%');
        }
        if(!StringUtils.isBlank(exchangeBookItemSearchVO.getBookName2())){
            sb.append(" inner join fetch ei.wishBook wb");
            //params.add('%'+exchangeBookItemSearchVO.getBookName2()+'%');
        }
        if(!StringUtils.isBlank(user.getEmail())){
            sb.append(" and u.email=?");
            params.add(user.getEmail());
        }
        if(!StringUtils.isBlank(exchangeBookItemSearchVO.getBookName1())){
            sb.append(" and eb.bookName like ?");
            params.add('%'+exchangeBookItemSearchVO.getBookName1()+'%');
        }
        if(!StringUtils.isBlank(exchangeBookItemSearchVO.getBookName2())){
            sb.append(" and wb.bookName like ?");
            params.add('%'+exchangeBookItemSearchVO.getBookName2()+'%');
        }

        if(!StringUtils.isBlank(exchangeBookItemSearchVO.getId())){
            sb.append(" and ei.id like ?");
            params.add('%'+exchangeBookItemSearchVO.getId()+'%');
        }
        if(!StringUtils.isBlank(exchangeBookItemSearchVO.getApplicationDate())){
            Date begin=null;
            try {
                begin=dateUtil.parse(exchangeBookItemSearchVO.getApplicationDate(),"yyyy-MM-dd");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar calendar=new GregorianCalendar();
            calendar.setTime(begin);
            calendar.add(calendar.DATE,1);
            Date end=calendar.getTime();
            sb.append(" and ei.applicationDate>=? and ei.applicationDate<?");
            params.add(begin);
            params.add(end);
        }
        if(exchangeBookItemSearchVO.getStatus()!=null){
            sb.append(" and ei.status=?");
            params.add(exchangeBookItemSearchVO.getStatus());
        }
        sb.append(" and ei.deleted=false order by ei.applicationDate desc");
        PageBean pageBean=new PageBean(exchangeBookItemSearchVO.getPageNumber(),exchangeBookItemSearchVO.getPageSize());
        return exchangeItemDao.find(sb.toString().replaceFirst("and","where"),params,pageBean);
    }
}
