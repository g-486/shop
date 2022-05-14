package com.app.shop.model;

import com.app.shop.sql.helper.GoodsDBHelper;
import com.app.shop.sql.table.Goods;
import com.app.shop.ui.framents.HomeFragment;

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
        GoodsDBHelper helper = GoodsDBHelper.getInstance(HomeFragment.context);
        helper.getReadableDatabase();
        goodsList = helper.queryAll();
        return goodsList;
    }
}

