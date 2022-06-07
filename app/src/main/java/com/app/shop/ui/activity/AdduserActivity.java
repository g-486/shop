package com.app.shop.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.app.shop.databinding.ActivityAdduserBinding;
import com.app.shop.sql.helper.UserDBHelper;
import com.app.shop.sql.table.User;
import com.app.shop.utils.ToastUtils;

public class AdduserActivity extends AppCompatActivity {
    private ActivityAdduserBinding binding;
    private UserDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdduserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.userAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user=new User();
                user.setUname(binding.userName.getText().toString());
                helper = UserDBHelper.getInstance(AdduserActivity.this);
                boolean b=helper.insert(user);
                if (b) ToastUtils.shortToast(AdduserActivity.this,"添加成功");
                else ToastUtils.shortToast(AdduserActivity.this,"添加shibai");
            }
        });

    }
}