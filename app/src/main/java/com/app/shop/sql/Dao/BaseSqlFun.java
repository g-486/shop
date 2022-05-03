package com.app.shop.sql.Dao;

import java.util.List;

/**
 * create by 呵呵 on 2022/3/18.
 */
public interface BaseSqlFun<T> {
    abstract public boolean insert(T t);

    abstract public boolean delete(String condition);

    abstract public boolean update(T item,String name);

    abstract public List<T> queryAll();

    abstract public List<T> queryBy(String conditon);
}
