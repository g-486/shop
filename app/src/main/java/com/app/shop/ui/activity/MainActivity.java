package com.app.shop.ui.activity;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.shop.R;
import com.app.shop.adapter.TabPagerAdapter;
import com.app.shop.base.BaseActivity;
import com.app.shop.ui.framents.HomeFragment;
import com.app.shop.ui.framents.MineFragment;
import com.app.shop.ui.framents.OrderFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager2 viewPager;
    private LinearLayout llhome, llorder, llme;
    private ImageView ivhome, ivorder, ivme, ivCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        initPager();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private void initView() {

        //tab
        viewPager = findViewById(R.id.viewpager);
        llhome = findViewById(R.id.ll_home);
        llhome.setOnClickListener(MainActivity.this);
        llorder = findViewById(R.id.ll_order);
        llorder.setOnClickListener(MainActivity.this);
        llme = findViewById(R.id.ll_me);
        llme.setOnClickListener(MainActivity.this);
        ivhome = findViewById(R.id.iv_home);
        ivorder = findViewById(R.id.iv_order);
        ivme = findViewById(R.id.iv_me);
        ivCurrent = ivhome;
    }

    private void initPager() {
        List<Fragment> fragments=new ArrayList<>();
        fragments.add(HomeFragment.NewInstance());
        fragments.add(OrderFragment.newInstance());
        fragments.add(MineFragment.newInstance());

        TabPagerAdapter tabPagerAdapter=new TabPagerAdapter(getSupportFragmentManager(),getLifecycle(),fragments);
        viewPager.setAdapter(tabPagerAdapter);
        //滑动切换
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    private void changeTab(int position) {
        ivCurrent.setSelected(false);
        switch (position) {
            case R.id.ll_home:
                viewPager.setCurrentItem(0);
            case 0:
                ivhome.setSelected(true);
                ivCurrent = ivhome;
                break;
            case R.id.ll_order:
                viewPager.setCurrentItem(1);
            case 1:
                ivorder.setSelected(true);
                ivCurrent = ivorder;
                break;
            case R.id.ll_me:
                viewPager.setCurrentItem(2);
            case 2:
                ivme.setSelected(true);
                ivCurrent = ivme;
                break;
        }
    }

    @Override   //点击切换
    public void onClick(View view) {
        changeTab(view.getId());
    }
}