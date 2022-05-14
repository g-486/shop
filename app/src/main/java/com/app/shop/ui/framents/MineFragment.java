package com.app.shop.ui.framents;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.app.shop.R;
import com.app.shop.databinding.FragmentMineBinding;
import com.app.shop.ui.activity.AboutActivity;
import com.app.shop.ui.activity.AddGoodsActivity;
import com.app.shop.ui.activity.AddorderActivity;
import com.app.shop.ui.activity.AddsayActivity;
import com.app.shop.ui.activity.AdduserActivity;
import com.app.shop.ui.activity.DataAnalyActivity;
import com.app.shop.ui.activity.LoginActivity;
import com.app.shop.ui.activity.OtherSetActivity;
import com.app.shop.ui.activity.ReadGoodsActivity;
import com.app.shop.ui.activity.ReadOrderActivity;
import com.app.shop.ui.activity.ResetGoodActivity;

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
        //function
        binding.tvGoodAdd.setOnClickListener(this);
        binding.tvGoodRe.setOnClickListener(this);
        binding.tvGoodDel.setOnClickListener(this);
        binding.tvGoodLook.setOnClickListener(this);
        binding.tvOrderLook.setOnClickListener(this);
        binding.tvOrderChose.setOnClickListener(this);
        binding.tvDataEvent.setOnClickListener(this);
        binding.tvSetOther.setOnClickListener(this);
        binding.tvAbout.setOnClickListener(this);

        binding.addOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(getActivity(), AddorderActivity.class);
                startActivity(intent);
            }
        });
        binding.addSay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(getActivity(), AddsayActivity.class);
                startActivity(intent);
            }
        });
        binding.addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(getActivity(), AdduserActivity.class);
                startActivity(intent);
            }
        });
//        binding.tvOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferencesUtils.setSignState(getActivity(),false);
//
//            }
//        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mine_head:
                intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_setting:

                break;
            case R.id.tv_good_add:
                intent=new Intent(context, AddGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_good_del:
                intent=new Intent(context, ReadGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_good_re:
                intent=new Intent(context, ResetGoodActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_good_look:
                intent=new Intent(context, ReadGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_order_look:
            case R.id.tv_order_chose:
                intent=new Intent(context, ReadOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_data_event:
                intent=new Intent(context, DataAnalyActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_set_other:
                intent=new Intent(context, OtherSetActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_about:
                intent=new Intent(context, AboutActivity.class);
                startActivity(intent);
                break;
        }
    }
}
