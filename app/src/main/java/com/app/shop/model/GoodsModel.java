package com.app.shop.model;

import com.app.shop.sql.table.Goods;

import java.util.ArrayList;
import java.util.List;

/**
 * create by 呵呵 on 2022/3/30.
 */
public class GoodsModel implements IGoodsModel{
    @Override
    public void showLoadView(onLoadListener onLoadListener) {
        onLoadListener.onSucces(getGoodsData());
    }

    //数据来源与网络或数据库
    private List<Goods> getGoodsData() {
        List<Goods> goodsList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Goods good = new Goods();
            good.setGname("大苹果");
            good.setPrice(12.3);
            good.setDesc("大苹果");
            good.setSalenum(233);
            goodsList.add(good);
        }
        return goodsList;
    }
}
