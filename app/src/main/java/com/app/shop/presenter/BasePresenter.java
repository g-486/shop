package com.app.shop.presenter;

import com.app.shop.view.IBaseView;

import java.lang.ref.WeakReference;

/**
 * create by 呵呵 on 2022/3/30.
 */
public class BasePresenter<T extends IBaseView> {
    WeakReference<T> Iview;

    //绑定
    public void attachView(T view){
        this.Iview =new WeakReference<>(view);
    }
    //解绑
    public void detachView(){
        if (Iview !=null){
            Iview.clear();
            Iview =null;
        }
    }
}