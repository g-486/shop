package com.app.shop.model;

import com.app.shop.sql.table.Orders;

import java.util.List;

/**
 * create by 呵呵 on 2022/3/31.
 */
public interface IOrderModel {
    void showLoadView(onLoadListener onLoadListener);
    interface onLoadListener{
        void onSuccess(List<Orders> orders);
        void onError(String msg);
    }
}
