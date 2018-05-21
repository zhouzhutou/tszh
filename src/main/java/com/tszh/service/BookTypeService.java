package com.tszh.service;

import com.tszh.entity.BookType;

import java.util.List;

/**
 * Created by Administrator on 2018/5/16 0016.
 */
public interface BookTypeService {

    /**
     * 获取所有的图书类别
     * @return
     */
    public List<BookType> getBookTypes();

    /**
     * 根据图书类型名获得图书类型实体
     * @param bookTypes
     * @return
     */
    public List<BookType> getBookTypes(List<String> bookTypes);
}
