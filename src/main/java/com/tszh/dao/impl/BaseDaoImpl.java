package com.tszh.dao.impl;



import com.tszh.dao.BaseDao;
import com.tszh.entity.PageBean;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/4/16 0016.
 */
@Repository("baseDao")
public class BaseDaoImpl<T> implements BaseDao<T> {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession()
    {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public Serializable save(T o) {
        return this.getCurrentSession().save(o);
        //return this.sessionFactory.openSession().save(0);
    }


    @Override
    public void delete(T o) {
        this.getCurrentSession().delete(o);
    }

    @Override
    public void update(T o) {
        this.getCurrentSession().update(o);
    }

    @Override
    public void merge(T o) {
        this.getCurrentSession().merge(o);
    }

    @Override
    public void saveOrUpdate(T o) {
        this.getCurrentSession().saveOrUpdate(o);
    }

    @Override
    public T get(Class<T> c, Serializable id) {
        return (T)this.getCurrentSession().get(c,id);
    }

    @Override
    public List<T> find(String hql) {
        return this.getCurrentSession().createQuery(hql).list();
    }

    @Override
    public List<T> find(String hql,PageBean pageBean)
    {
        Query query=this.getCurrentSession().createQuery(hql);
        return query.setFirstResult(pageBean.getStart())
                .setMaxResults(pageBean.getPageSize()).list();

    }

    @Override
    public List<T> find(String hql, Object[] params) {
        Query query=this.getCurrentSession().createQuery(hql);
        if(params!=null && params.length>0)
        {
            for(int i=0;i<params.length;i++)
            {
                query.setParameter(i,params[i]);
            }
        }
        return query.list();
    }

    @Override
    public List<T> find(String hql, List<Object> params) {
        Query query=this.getCurrentSession().createQuery(hql);
        if(params!=null && params.size()>0)
        {
            for(int i=0;i<params.size();i++)
            {
                query.setParameter(i,params.get(i));
            }
        }
        return query.list();
    }

    @Override
    public List<T> find(String hql, Object[] params, PageBean pageBean) {
        Query query=this.getCurrentSession().createQuery(hql);
        if(params!=null && params.length>0)
        {
            for(int i=0;i<params.length;i++)
            {
                query.setParameter(i,params[i]);
            }
        }
        return query.setFirstResult(pageBean.getStart())
                .setMaxResults(pageBean.getPageSize()).list();
}

    @Override
    public List<T> find(String hql, List<Object> params, PageBean pageBean) {
        Query query=this.getCurrentSession().createQuery(hql);
        if(params!=null && params.size()>0)
        {
            for(int i=0;i<params.size();i++)
            {
                query.setParameter(i,params.get(i));
            }
        }
        return query.setFirstResult(pageBean.getStart()).
                setMaxResults(pageBean.getPageSize()).list();
    }

    @Override
    public T get(String hql, Object[] params) {
        List<T> list=this.find(hql,params);
        if(list!=null && list.size()>0)
            return list.get(0);
        else
            return null;
    }

    @Override
    public T get(String hql, List<Object> params) {
        List<T> list=this.find(hql,params);
        if(list!=null && list.size()>0)
            return list.get(0);
        else
            return null;
    }

    @Override
    public Long count(String hql) {
        return (Long)this.getCurrentSession().createQuery(hql).uniqueResult();
    }

    @Override
    public Long count(String hql, Object[] params) {
        Query query=this.getCurrentSession().createQuery(hql);
        if(params!=null && params.length>0)
        {
            for(int i=0;i<params.length;i++)
            {
                query.setParameter(i,params[i]);
            }
        }
        return (Long)query.uniqueResult();
    }

    @Override
    public Long count(String hql, List<Object> params) {
        Query query=this.getCurrentSession().createQuery(hql);
        if(params!=null && params.size()>0)
        {
            for(int i=0;i<params.size();i++)
            {
                query.setParameter(i,params.get(i));
            }
        }
        return (Long)query.uniqueResult();
    }

    @Override
    public Integer executeHql(String hql) {
        return this.getCurrentSession().createQuery(hql).executeUpdate();
    }

    @Override
    public Integer executeHql(String hql, Object[] params) {
        Query query = this.getCurrentSession().createQuery(hql);
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i, params[i]);
            }
        }
        return query.executeUpdate();

    }

    @Override
    public Integer executeHql(String hql, List<Object> params) {
        Query query=this.getCurrentSession().createQuery(hql);
        if(params!=null && params.size()>0)
        {
            for(int i=0;i<params.size();i++)
            {
                query.setParameter(i,params.get(i));
            }
        }
        return query.executeUpdate();
    }

    @Override
    public Integer executeSql(String sql) {
        Query query = this.getCurrentSession().createSQLQuery(sql);
        return query.executeUpdate();

    }
}
