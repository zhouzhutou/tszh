package com.tszh.dao;



import com.tszh.entity.PageBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 基础数据库操作
 * Created by Administrator on 2018/4/16 0016.
 */
public interface BaseDao<T> {

    /**
     * 保存一个对象
     *
     * @param o
     * @return
     */
    public Serializable save(T o);

    /**
     * 删除一个对象
     *
     * @param o
     */
    public void delete(T o);

    /**
     * 更新一个对象
     *
     * @param o
     */
    public void update(T o);

    /**
     * 批量更新
     * @param hql
     * @param params
     * @return
     */
    public int updateIn(String hql, Map<String,List<?>> params);

    /**
     * 合并一个对象
     *
     * @param o
     */
    public void merge(T o);

    /**
     * 根据id获得对象
     *
     * @param c
     * @param id
     * @return
     */
    public T get(Class<T> c, Serializable id);

    /**
     * 保存或修改一个对象
     * @param o
     */
    public void saveOrUpdate(T o);
    /**
     * 查询集合
     *
     * @param hql
     * @return
     */
    public List<T> find(String hql);


    /**
     * 查询集合
     * @param hql
     * @param pageBean
     * @return
     */
    public List<T> find(String hql, PageBean pageBean);

    /**
     * 查询集合
     *
     * @param hql
     * @param params
     * @return
     */
    public List<T> find(String hql, Object[] params);

    /**
     * 查询集合
     *
     * @param hql
     * @param params
     * @return
     */
    public List<T> find(String hql, List<Object> params);

    /**
     * 查询集合（分页）
     *
     * @param hql
     * @param params
     * @param pageBean
     * @return
     */
    public List<T> find(String hql, Object[] params, PageBean pageBean);

    /**
     * 查询集合（分页）
     *
     * @param hql
     * @param params
     * @param pageBean
     * @return
     */
    public List<T> find(String hql, List<Object> params, PageBean pageBean);

    /**
     * in查询
     * @param hql
     * @param params
     * @return
     */
    public List<T> findIn(String hql,Map<String,List<?>> params);


    /**
     * 获得一个对象
     *
     * @param hql
     * @param params
     * @return
     */
    public T get(String hql, Object[] params);

    /**
     * 获得一个对象
     *
     * @param hql
     * @param params
     * @return
     */
    public T get(String hql, List<Object> params);

    /**
     * 计数
     *
     * @param hql
     * @return
     */
    public Long count(String hql);

    /**
     * 计数
     *
     * @param hql
     * @param params
     * @return
     */
    public Long count(String hql, Object[] params);

    /**
     * 计数
     * @param hql
     * @param params
     * @return
     */
    public Long count(String hql, List<Object> params);

    /**
     * 执行HQL语句
     * @param hql
     * @return
     */
    public Integer executeHql(String hql);

    /**
     * 执行HQL语句
     * @param hql
     * @param params
     * @return
     */
    public Integer executeHql(String hql, Object[] params);

    /**
     * 执行HQL语句
     * @param hql
     * @param params
     * @return
     */
    public Integer executeHql(String hql, List<Object> params);

    /**
     * 执行SQL语句
     * @param sql
     * @return
     */
    public Integer executeSql(String sql);

}