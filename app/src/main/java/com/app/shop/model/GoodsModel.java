package com.app.shop.model;

import com.app.shop.sql.helper.GoodsDBHelper;
import com.app.shop.sql.table.Goods;
import com.app.shop.ui.framents.OrderFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * create by 呵呵 on 2022/3/30.
 */
public class GoodsModel implements IGoodsModel {
    @Override
    public void showLoadView(onLoadListener onLoadListener) {
        onLoadListener.onSucces(getGoodsData());
    }

    //数据来源与网络或数据库
    private List<Goods> getGoodsData() {
        List<Goods> goodsList = new ArrayList<>();
//        GoodsDBHelper helper = GoodsDBHelper.getInstance(OrderFragment.context,1);
//        helper.getReadableDatabase();
//        goodsList = helper.queryAll();
        for (int i = 0; i < 20; i++) {
            Goods good = new Goods();
            good.Gname = "大苹果";
            good.price = 12.3;
            good.desc = "好吃的大苹果";
            good.salenum = 233;
            goodsList.add(good);
        }
        return goodsList;
    }
}
