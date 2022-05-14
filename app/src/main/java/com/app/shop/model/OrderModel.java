package com.app.shop.model;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.app.shop.sql.helper.OrdersDBHelper;
import com.app.shop.sql.table.Orders;
import com.app.shop.ui.framents.OrderFragment;

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
        OrdersDBHelper helper=OrdersDBHelper.getInstance(OrderFragment.context);
        try {
            helper.openWriteLink();
        }catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }
        List<Orders> ordersList=helper.queryAll();
        return ordersList;
    }
}
