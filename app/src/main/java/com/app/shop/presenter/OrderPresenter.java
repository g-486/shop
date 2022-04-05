package com.app.shop.presenter;

import com.app.shop.model.IOrderModel;
import com.app.shop.model.OrderModel;
import com.app.shop.sql.table.Orders;
import com.app.shop.view.IOrderView;

import java.util.List;

/**
 * create by 呵呵 on 2022/3/31.
 */
public class OrderPresenter<T extends IOrderView> extends BasePresenter{
    OrderModel orderModel=new OrderModel();

    public void fatch(){
        if (Iview!=null&&orderModel!=null){
            orderModel.showLoadView(new IOrderModel.onLoadListener() {
                @Override
                public void onSuccess(List<Orders> orders) {
                    ((IOrderView)Iview.get()).showOrderData(orders);
                }

                @Override
                public void onError(String msg) {

                }
            });
        }
    }
}
