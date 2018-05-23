package com.tszh.service.impl;

import com.tszh.dao.BookTypeDao;
import com.tszh.entity.BookType;
import com.tszh.service.BookTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/16 0016.
 */
@Service("bookTypeService")
public class BookTypeServiceImpl implements BookTypeService{

    @Autowired
    BookTypeDao<BookType> bookTypeDao;

    @Override
    public List<BookType> getBookTypes() {
        String hql="from BookType";
        List<BookType> bookTypes=bookTypeDao.find(hql);
        return bookTypes;
    }

    @Override
    public List<BookType> getBookTypes(List<String> bookTypes) {
        //String[] bts=bookTypes.split(",");
        if(bookTypes==null || bookTypes.size()==0)
            return new ArrayList<>();
        StringBuilder sb=new StringBuilder("from BookType bt");
        Map<String,List<?>> params=new HashMap<>();
        if(bookTypes!=null && bookTypes.size()>0){
            sb.append(" and bt.type in (:types)");
            params.put("types",bookTypes);
        }
        return bookTypeDao.findIn(sb.toString().replaceFirst("and","where"),params);
    }
}
