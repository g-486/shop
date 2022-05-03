package com.app.shop.ui.framents;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.app.shop.R;
import com.app.shop.base.BaseFragment;
import com.app.shop.databinding.FragmentMineBinding;
import com.app.shop.ui.activity.LoginActivity;
import com.app.shop.ui.activity.SettingActivity;

/**
 * create by 呵呵 on 2022/3/17.
 */
public class MineFragment extends Fragment implements View.OnClickListener {
    private FragmentMineBinding binding;

    public static FragmentActivity context;

    private Intent intent;

    public static MineFragment newInstance(){
        MineFragment fragment=new MineFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentMineBinding.inflate(inflater,container, false);
        context=getActivity();
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.mineText.setText("商店名");
        initEvent();
    }

    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    private void initEvent(){
        binding.mineHead.setOnClickListener(this::onClick);
        binding.mineSetting.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mine_head:
                intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_setting:
                intent=new Intent(context, SettingActivity.class);
                startActivity(intent);
                break;
        }
    }
}
