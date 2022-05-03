package com.app.shop.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.app.shop.R;

/**
 * 获取需修改商品的信息
 * 根据输入信息修改商品信息
 * 更新到数据库
 */
public class ResetGoodActivity extends BarActivity {
    private final String GOOD_NAME = "good_name";

    private Intent intent=getIntent();//获取需要修改的商品名称

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods);
        setToolbar();
        setTitle("修改商品");
    }
    private void initView(){

    }
    private void initEvent(){
        String good_name=intent.getStringExtra(GOOD_NAME);
    }

}