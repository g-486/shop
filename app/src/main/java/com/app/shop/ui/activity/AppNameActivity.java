package com.app.shop.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.app.shop.databinding.ActivityAppNameBinding;
import com.app.shop.utils.SharedPreferencesUtils;
import com.app.shop.utils.ToastUtils;

/**
 * create by 呵呵 on 2022/5/18.
 */
public class AppNameActivity extends BarActivity{
    private ActivityAppNameBinding binding;
    private String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAppNameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        event();
    }
    private void event(){
        binding.naOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = binding.appNa.getText().toString().trim();
                SharedPreferencesUtils.setAppName(AppNameActivity.this,name);
                ToastUtils.shortToast(AppNameActivity.this,"命名成功  ^ _ ^  ");
            }
        });
        binding.naDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 binding.appNa.setText("");
            }
        });
    }
}
