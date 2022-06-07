package com.app.shop.model;

import com.app.shop.sql.helper.GoodsDBHelper;
import com.app.shop.sql.table.Goods;
import com.app.shop.ui.framents.HomeFragment;
import com.app.shop.utils.SharedPreferencesUtils;

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
        boolean sign = SharedPreferencesUtils.getSignState(HomeFragment.context);
        List<Goods> goodsList = new ArrayList<>();
        if (sign) {
            GoodsDBHelper helper = GoodsDBHelper.getInstance(HomeFragment.context);
            helper.getReadableDatabase();
            goodsList = helper.queryAll();
        }else {
            for (int i = 0; i < 10; i++) {
                Goods good = new Goods("商品" + 1, 10.5, "试用商品", "不知道", "不知道", "不能吃");
                goodsList.add(good);
            }
        }
        return goodsList;
    }
}

