package com.app.shop.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * create by 呵呵 on 2022/3/17.
 */
public class TabPagerAdapter extends FragmentStateAdapter {
    private List<Fragment> pagers=new ArrayList<>();

    public TabPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<Fragment> pagers) {
        super(fragmentManager, lifecycle);
        this.pagers=pagers;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return pagers.get(position);
    }

    @Override
    public int getItemCount() {
        return pagers.size();
    }
}
