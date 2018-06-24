package com.tszh.service.impl;

import com.tszh.cons.Code;
import com.tszh.dao.ExchangeBookDao;
import com.tszh.dao.ExchangeItemDao;
import com.tszh.entity.ExchangeBook;
import com.tszh.entity.ExchangeItem;
import com.tszh.entity.PageBean;
import com.tszh.entity.User;
import com.tszh.exception.CustomException;
import com.tszh.service.ExchangeItemService;
import com.tszh.util.DateUtil;
import com.tszh.vo.requestVO.BookReadVO;
import com.tszh.vo.requestVO.ExchangeBookItemSearchVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.text.ParseException;
import java.util.*;

/**
 * Created by Administrator on 2018/5/17 0017.
 */
@Service("exchangeItemService")
@Transactional
public class ExchangeItemServiceImpl implements ExchangeItemService{

    @Autowired
    DateUtil dateUtil;

    @Autowired
    private ExchangeBookDao<ExchangeBook> exchangeBookDao;

    @Autowired
    private ExchangeItemDao<ExchangeItem> exchangeItemDao;

    @Override
    public Serializable save(ExchangeItem exchangeItem) {
        ExchangeBook myOwnBook=exchangeItem.getMyOwnBook();
        myOwnBook.setCanExchange(false);
        ExchangeBook wishBook=exchangeItem.getWishBook();
        wishBook.setCanExchange(false);
        exchangeBookDao.saveOrUpdate(myOwnBook);
        exchangeBookDao.saveOrUpdate(wishBook);
        Serializable id=exchangeItemDao.save(exchangeItem);
        return id;
    }

    @Override
    public ExchangeItem getExchangeItemById(String id)
    {
        String hql="from ExchangeItem ei where ei.id=? and ei.deleted=false";
        List<Object> params=new ArrayList<>();
        params.add(id);
        return exchangeItemDao.get(hql,params);
    }

    @Override
    public void deleteExchangeItemById(String id)
    {
        ExchangeItem exchangeItem=getExchangeItemById(id);
        if(exchangeItem==null)
            throw new CustomException("id为"+id+"的置换记录不存在", Code.NOT_FOUND_ERROR.getCode());
        exchangeItem.setDeleted(true);
        ExchangeBook myOwnBook=exchangeItem.getMyOwnBook();
        myOwnBook.setCanExchange(true);
        ExchangeBook wishBook=exchangeItem.getWishBook();
        wishBook.setCanExchange(true);
        exchangeBookDao.saveOrUpdate(myOwnBook);
        exchangeBookDao.saveOrUpdate(wishBook);
        exchangeItemDao.update(exchangeItem);
    }

    @Override
    public int approveExchangeItemById(String id) {
        StringBuilder sb=new StringBuilder("update ExchangeItem ei set ei.status=1,ei.passedDate=? and ei.id=? and ei.deleted=false");
        List<Object> params=new ArrayList<>();
        params.add(new Date());
        params.add(id);
        return exchangeItemDao.executeHql(sb.toString().replaceFirst("and","where"),params);
    }

    @Override
    public void expireExchangeItemById(String id){
        ExchangeItem exchangeItem=getExchangeItemById(id);
        if(exchangeItem==null)
            throw new CustomException("id为"+id+"的置换记录不存在", Code.NOT_FOUND_ERROR.getCode());
        exchangeItem.setStatus((short) 2);
        exchangeItem.setExpiredDate(new Date());
        ExchangeBook myOwnBook=exchangeItem.getMyOwnBook();
        myOwnBook.setCanExchange(true);
        ExchangeBook wishBook=exchangeItem.getWishBook();
        wishBook.setCanExchange(true);
        exchangeBookDao.saveOrUpdate(myOwnBook);
        exchangeBookDao.saveOrUpdate(wishBook);
        exchangeItemDao.update(exchangeItem);
    }

    @Override
    public long countExchangeItem(ExchangeBookItemSearchVO exchangeBookItemSearchVO,User user) {
        StringBuilder sb=new StringBuilder();
        List<Object> params=new ArrayList<>();
        sb.append("select count(*) from ExchangeItem ei");
        if(!StringUtils.isBlank(user.getEmail())){
            sb.append(" inner join ei.applyUser au");
            //params.add(user.getEmail());
        }
        if(!StringUtils.isBlank(exchangeBookItemSearchVO.getBookName1())) {
            sb.append(" inner join ei.myOwnBook mob");
            //params.add('%'+exchangeBookItemSearchVO.getBookName1()+'%');
        }
        if(!StringUtils.isBlank(exchangeBookItemSearchVO.getBookName2())){
            sb.append(" inner join ei.wishBook wb");
            //params.add('%'+exchangeBookItemSearchVO.getBookName2()+'%');
        }
        if(!StringUtils.isBlank(user.getEmail())){
            sb.append(" and au.email=?");
            params.add(user.getEmail());
        }
        if(!StringUtils.isBlank(exchangeBookItemSearchVO.getBookName1())){
            sb.append(" and mob.bookName like ?");
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
        if(!StringUtils.isBlank(exchangeBookItemSearchVO.getStartDate())){
            Date startDate=null;
            try {
                startDate=dateUtil.parse(exchangeBookItemSearchVO.getStartDate(),"yyyy-MM-dd");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            sb.append(" and ei.applicationDate>=?");
            params.add(startDate);
        }
        if(!StringUtils.isBlank(exchangeBookItemSearchVO.getEndDate())){
            Date endDate=null;
            try {
                endDate=dateUtil.parse(exchangeBookItemSearchVO.getEndDate(),"yyyy-MM-dd");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar calendar=new GregorianCalendar();
            calendar.setTime(endDate);
            calendar.add(calendar.DATE,1);
            endDate=calendar.getTime();
            sb.append(" and ei.applicationDate<?");
            params.add(endDate);
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
            sb.append(" inner join fetch ei.applyUser au");
            //params.add(user.getEmail());
        }
        if(!StringUtils.isBlank(exchangeBookItemSearchVO.getBookName1())) {
            sb.append(" inner join fetch ei.myOwnBook mob");
            //params.add('%'+exchangeBookItemSearchVO.getBookName1()+'%');
        }
        if(!StringUtils.isBlank(exchangeBookItemSearchVO.getBookName2())){
            sb.append(" inner join fetch ei.wishBook wb");
            //params.add('%'+exchangeBookItemSearchVO.getBookName2()+'%');
        }
        if(!StringUtils.isBlank(user.getEmail())){
            sb.append(" and au.email=?");
            params.add(user.getEmail());
        }
        if(!StringUtils.isBlank(exchangeBookItemSearchVO.getBookName1())){
            sb.append(" and mob.bookName like ?");
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
        if(!StringUtils.isBlank(exchangeBookItemSearchVO.getStartDate())){
            Date startDate=null;
            try {
                startDate=dateUtil.parse(exchangeBookItemSearchVO.getStartDate(),"yyyy-MM-dd");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            /*Calendar calendar=new GregorianCalendar();
            calendar.setTime(begin);
            calendar.add(calendar.DATE,1);
            Date end=calendar.getTime();*/
            sb.append(" and ei.applicationDate>=?");
            params.add(startDate);
        }
        if(!StringUtils.isBlank(exchangeBookItemSearchVO.getEndDate())){
            Date endDate=null;
            try {
                endDate=dateUtil.parse(exchangeBookItemSearchVO.getEndDate(),"yyyy-MM-dd");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar calendar=new GregorianCalendar();
            calendar.setTime(endDate);
            calendar.add(calendar.DATE,1);
            endDate=calendar.getTime();
            sb.append(" and ei.applicationDate<?");
            params.add(endDate);
        }
        if(exchangeBookItemSearchVO.getStatus()!=null){
            sb.append(" and ei.status=?");
            params.add(exchangeBookItemSearchVO.getStatus());
        }
        sb.append(" and ei.deleted=false order by ei.applicationDate desc");
        PageBean pageBean=new PageBean(exchangeBookItemSearchVO.getPageNumber(),exchangeBookItemSearchVO.getPageSize());
        return exchangeItemDao.find(sb.toString().replaceFirst("and","where"),params,pageBean);
    }

    @Override
    public ExchangeItem findNotExpiredExchangeItemByBookId(int bookId) {
        String hql="from ExchangeItem ei inner join fetch ei.myOwnBook mob inner join fetch ei.wishBook wb " +
                "where (mob.id=? or wb.id=?) and ei.status!=2 and ei.deleted=false";
        List<Object> params=new ArrayList<>();
        params.add(bookId);
        params.add(bookId);
        return exchangeItemDao.get(hql,params);
    }

    @Override
    public List<ExchangeItem> findNotExpiredExchangeItemsByBookIds(List<Integer> bookIds)
    {
        if(bookIds==null || bookIds.size()==0)
            return new ArrayList<>();
        String hql="from ExchangeItem ei inner join fetch ei.myOwnBook mob inner join fetch ei.wishBook wb " +
                "where (mob.id in (:bookIds1) or wb.id in (:bookIds2)) and ei.status!=2 and ei.deleted=false";
        Map<String,List<?>> params=new HashMap<>();
        params.put("bookIds1",bookIds);
        params.put("bookIds2",bookIds);
        return exchangeItemDao.findIn(hql,params);
    }

    @Override
    public long countExchangeItemWithWishBook(BookReadVO bookReadVO,User user) {
        StringBuilder sb=new StringBuilder();
        List<Object> params=new ArrayList<>();
        sb.append("select count(*) from ExchangeItem ei inner join ei.wishBook wb");
        if(user!=null){
            sb.append(" inner join ei.applyUser au");
        }
        if(user !=null && !StringUtils.isBlank(user.getEmail())){
            sb.append(" and au.email=?");
            params.add(user.getEmail());
        }
        if(bookReadVO.getStatus()!=null) {
            sb.append(" and ei.status=?");
            params.add(bookReadVO.getStatus());
        }
        sb.append(" and ei.deleted=false");
        if(!StringUtils.isBlank(bookReadVO.getSortName()))
            sb.append(" order by ei."+bookReadVO.getSortName());
        if(!StringUtils.isBlank(bookReadVO.getSortOrder()))
            sb.append(" "+bookReadVO.getSortOrder());
        return exchangeItemDao.count(sb.toString().replaceFirst("and","where"),params);
    }

    @Override
    public List<ExchangeItem> findExchangeItemWithWishBook(BookReadVO bookReadVO,User user){
        StringBuilder sb=new StringBuilder();
        List<Object> params=new ArrayList<>();
        sb.append("from ExchangeItem ei inner join fetch ei.wishBook wb");
        if(user!=null){
            sb.append(" inner join fetch ei.applyUser au");
        }
        if(user !=null && !StringUtils.isBlank(user.getEmail())){
            sb.append(" and au.email=?");
            params.add(user.getEmail());
        }
        if(bookReadVO.getStatus()!=null) {
            sb.append(" and ei.status=?");
            params.add(bookReadVO.getStatus());
        }
        sb.append(" and ei.deleted=false");
        if(!StringUtils.isBlank(bookReadVO.getSortName()))
            sb.append(" order by ei."+bookReadVO.getSortName());
        if(!StringUtils.isBlank(bookReadVO.getSortOrder()))
            sb.append(" "+bookReadVO.getSortOrder());
        PageBean pageBean=new PageBean(bookReadVO.getPageNumber(),bookReadVO.getPageSize());
        return exchangeItemDao.find(sb.toString().replaceFirst("and","where"),params,pageBean);
    }

}
