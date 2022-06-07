package com.app.shop.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.app.shop.R;
import com.app.shop.databinding.ActivityAboutBinding;

/**
 * create by 呵呵 on 2022/5/4.
 */
public class AboutActivity extends BarActivity{
    private ActivityAboutBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setToolbar();
        setTitle("关于");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
