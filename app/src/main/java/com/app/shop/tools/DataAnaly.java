package com.app.shop.tools;

import android.content.Context;

import com.app.shop.sql.helper.GoodsDBHelper;
import com.app.shop.sql.helper.OrdersDBHelper;
import com.app.shop.sql.table.Goods;
import com.app.shop.sql.table.Orders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by 呵呵 on 2022/5/7.
 */
public class DataAnaly {
    public static Map<String,Integer> analy(Context context) {
        Map<String ,Integer> map=new HashMap<>();
        //1.获取商品列表
        GoodsDBHelper goodsDBHelper = new GoodsDBHelper(context);
        goodsDBHelper.openReadLink();
        List<Goods> goods = goodsDBHelper.queryAll();
        {
            int i = 0;
            while (i < goods.size()) {
                map.put(goods.get(i).getGname(), 0);
                i++;
            }
        }
        //2.获取订单列表。分解字段 foods
        OrdersDBHelper ordersDBHelper=new OrdersDBHelper(context);
        ordersDBHelper.openReadLink();
        List<Orders> orders=ordersDBHelper.queryAll();
        for (int i=0;i<orders.size();i++){
            String[] food=orders.get(i).getFoods().split(",");
            //3.根据商品名统计各商品的数量  根据时间统计订单数
            for (int j = 0; j < food.length; j++) {
                Integer integer=map.get(food[j]);
                map.put(food[j],++integer);
            }
        }
        return map;
    }


}
