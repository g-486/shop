package com.app.shop.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.metrics.Event;
import android.os.Bundle;
import android.view.View;

import com.app.shop.databinding.ActivityAddsayBinding;
import com.app.shop.sql.helper.SayDBHelper;
import com.app.shop.sql.table.Say;
import com.app.shop.utils.ToastUtils;

public class AddsayActivity extends AppCompatActivity {
    private ActivityAddsayBinding binding;
    private SayDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddsayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.sayAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper = new SayDBHelper(AddsayActivity.this);
                Say say=new Say();
                say.setUid(Integer.parseInt(binding.sayUid.getText().toString()));
                say.setGid(Integer.parseInt(binding.sayGid.getText().toString()));
                say.setOid(Integer.parseInt(binding.sayOid.getText().toString()));
                say.setDatetime(binding.sayTime.getText().toString());
                say.setStr(binding.sayStr.getText().toString());
                if (helper.insert(say)) ToastUtils.shortToast(AddsayActivity.this,"添加成功");
            }
        });

    }
}