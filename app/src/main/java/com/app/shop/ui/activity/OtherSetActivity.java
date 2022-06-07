package com.app.shop.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.app.shop.databinding.ActivityOtherSetBinding;
import com.app.shop.utils.SharedPreferencesUtils;
import com.app.shop.utils.ToastUtils;
import com.app.shop.weight.EditDialog;

public class OtherSetActivity extends BarActivity {
    private ActivityOtherSetBinding binding;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOtherSetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setToolbar();
        setTitle("其他设置");
        initData();
        initView();
        initEvent();
    }
    private void initData(){

    }
    private void initView(){

    }
    private void initEvent(){
        binding.loginIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(OtherSetActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        binding.loginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(OtherSetActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        binding.setShopName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(OtherSetActivity.this,AppNameActivity.class);
                startActivity(intent);
            }
        });
        binding.setStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        binding.outApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        binding.setOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}