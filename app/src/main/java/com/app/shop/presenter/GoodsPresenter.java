package com.app.shop.presenter;

import com.app.shop.model.GoodsModel;
import com.app.shop.model.IGoodsModel;
import com.app.shop.sql.table.Goods;
import com.app.shop.view.IGoodsView;

import java.util.List;

/**
 * create by 呵呵 on 2022/3/30.
 */
public class GoodsPresenter<T extends IGoodsView> extends BasePresenter {

    GoodsModel goodsModel = new GoodsModel();

    //业务逻辑
    public void fatch() {
        if (goodsModel != null && Iview.get() != null) {
            goodsModel.showLoadView(new IGoodsModel.onLoadListener() {
                @Override
                public void onSucces(List<Goods> goods) {
                    ((T) Iview.get()).showGoodsView(goods);
                }

                @Override
                public void onError(String msg) {

                }
            });
        }
    }
}
