package com.app.shop.view;

import com.app.shop.sql.table.Orders;

import java.util.List;

/**
 * create by 呵呵 on 2022/3/31.
 */
public interface IOrderView extends IBaseView{
    @Override
    default void showErorMsg(String msg) {
    }

    void showOrderData(List<Orders> orders);
}
