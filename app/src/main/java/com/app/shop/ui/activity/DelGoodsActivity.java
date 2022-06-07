package com.app.shop.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.shop.adapter.GoodsAdapter;
import com.app.shop.databinding.ActivityReadGoodsBinding;
import com.app.shop.sql.helper.GoodsDBHelper;
import com.app.shop.sql.table.Goods;

import java.util.List;

/**
 * create by 呵呵 on 2022/5/8.
 */
public class DelGoodsActivity extends BarActivity {
    private ActivityReadGoodsBinding binding;
    private GoodsAdapter adapter;
    private List<Goods> list;
    private String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityReadGoodsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initEvent();
    }

    private void initView(){
        name = binding.goodDelName.getText().toString();
    }

    private void initEvent(){
        GoodsDBHelper helper=new GoodsDBHelper(this);
        list = helper.queryAll();
        LinearLayoutManager manager=new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        adapter = new GoodsAdapter(list,this);
        binding.readGoods.setLayoutManager(manager);
        binding.readGoods.setAdapter(adapter);

        binding.goodDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.delete(name);
                refreshData();
            }
        });
    }
    private void refreshData() {
        GoodsDBHelper helper = GoodsDBHelper.getInstance(this);
        helper.getReadableDatabase();
        list = helper.queryAll();
        if ( adapter!= null) {
            adapter.setData(list);
        }
    }

}
