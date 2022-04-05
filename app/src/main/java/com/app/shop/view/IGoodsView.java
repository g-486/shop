package com.app.shop.view;

import com.app.shop.sql.table.Goods;

import java.util.List;

/**
 * create by 呵呵 on 2022/3/30.
 */
public interface IGoodsView extends IBaseView {
    //显示图片，文字，进度条，动画。。。
    void showGoodsView(List<Goods> goods);

    @Override
    default void showErorMsg(String msg) {

    }
}
