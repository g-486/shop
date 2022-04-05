package com.app.shop.ui.framents;

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

import com.app.shop.R;
import com.app.shop.base.BaseFragment;
import com.app.shop.ui.activity.LoginActivity;

/**
 * create by 呵呵 on 2022/3/17.
 */
public class MineFragment extends Fragment implements View.OnClickListener {
    private View view;

    private ImageView head;

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
        if (view==null){
            view=inflater.inflate(getLayoutId(),container,false);
        }
        head=view.findViewById(R.id.mine_head);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initEvent();
    }

    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    private void initEvent(){
        head.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mine_head:
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
