package com.app.shop.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.shop.databinding.ActivityDataAnalyBinding;
import com.app.shop.sql.helper.GoodsDBHelper;
import com.app.shop.sql.helper.OrdersDBHelper;
import com.app.shop.sql.table.Goods;
import com.app.shop.sql.table.Orders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by 呵呵 on 2022/5/8.
 */
public class DataAnalyActivity extends BarActivity {
    private ActivityDataAnalyBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataAnalyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setToolbar();
        setTitle("经营状况");
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initEvent();
    }

    private void initView() {

    }

    private void initEvent() {
        //折线数据  商品名+销售量
        Map<String, Integer> map = new HashMap<>();
        //横坐标  商品名称
        List<String> goodsName = new ArrayList<>();
        //纵坐标
        List<Integer> yValue = new ArrayList<>();
        //获取商品列表
        GoodsDBHelper goodsDBHelper = new GoodsDBHelper(this);
        goodsDBHelper.openReadLink();
        List<Goods> goods = goodsDBHelper.queryAll();
        int max = 0;
        String gname="";
        for (int i = 0; i < goods.size(); i++) {
            Goods good=goods.get(i);
            map.put(good.getGname(),good.getSalenum() );
            goodsName.add(goods.get(i).getGname());
//            goodsName.add("水果 "+i);
            if (max<=good.getSalenum()){
                max=good.getSalenum();
                gname=good.getGname();
            }
        }

        yValue.add(0);
        yValue.add(max/2);
        yValue.add(max);
        binding.saleupnaMe.setText(gname);
//        map.put("1",0);
//        map.put("2",2);
//        map.put("3",3);
//        map.put("4",2);
//        map.put("5",1);

//        goodsName.add("1");
//        goodsName.add("2");
//        goodsName.add("3");
//        goodsName.add("4");
//        goodsName.add("5");

//        yValue.add(0);
//        yValue.add(1);
//        yValue.add(2);
//        yValue.add(3);
//        yValue.add(4);
        binding.chartview.setValue(map, goodsName, yValue);
    }
}
