package com.app.shop.sql.Dao;

import java.util.List;

/**
 * create by 呵呵 on 2022/3/18.
 */
public interface BaseSqlFun<T> {
    abstract public int delete(String condition);
    abstract public long insert(List<T> list);
    abstract public int update(T item,String name);
    abstract public List<T> query(String...condition);
    abstract public T queryBy(String conditon);
}
