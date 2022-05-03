package com.app.shop.model;

import com.app.shop.sql.table.Orders;

import java.util.ArrayList;
import java.util.List;

/**
 * create by 呵呵 on 2022/3/31.
 */
public class OrderModel implements IOrderModel{

    @Override
    public void showLoadView(onLoadListener onLoadListener) {
        onLoadListener.onSuccess(getData());
    }

    private List<Orders> getData(){
        List<Orders> ordersList=new ArrayList<>();
        for (int i = 20; i > 0; i--) {
            Orders order=new Orders();
            order.date="yyyy-MM-dd hh:mm";
            order.foods="ncksjbvkabs";
            order.level=4;
            order.sumPrice=1234.445;
            ordersList.add(order);
        }
        return ordersList;
    }
}
