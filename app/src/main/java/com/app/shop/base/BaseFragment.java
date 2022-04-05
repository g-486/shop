package com.app.shop.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.shop.presenter.BasePresenter;
import com.app.shop.view.IBaseView;

/**
 * create by 呵呵 on 2022/3/29.
 */
public abstract class BaseFragment<T extends BasePresenter,V extends IBaseView> extends Fragment implements IBaseView{

    protected T presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter=createPresenter();
        presenter.attachView((V)this);
    }

    protected abstract int getLayoutId();

    protected abstract T createPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void showErorMsg(String msg) {

    }
}
