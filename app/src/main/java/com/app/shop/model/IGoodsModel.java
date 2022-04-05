package com.app.shop.model;

import com.app.shop.sql.table.Goods;

import java.util.List;

/**
 * create by 呵呵 on 2022/3/30.
 */
public interface IGoodsModel {
    void showLoadView(onLoadListener onLoadListener);

    interface onLoadListener {
        void onSucces(List<Goods> goods);

        void onError(String msg);
    }
}
