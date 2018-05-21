package com.tszh.dao.impl;

import com.tszh.dao.BookTypeDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018/5/16 0016.
 */
@Repository("bookTypeDao")
public class BookTypeImpl<BookType> extends BaseDaoImpl<BookType> implements BookTypeDao<BookType>{
}
