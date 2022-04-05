package com.app.shop.ui.framents;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.shop.R;
import com.app.shop.adapter.GoodsAdapter;
import com.app.shop.base.BaseFragment;
import com.app.shop.presenter.GoodsPresenter;
import com.app.shop.sql.helper.GoodsDBHelper;
import com.app.shop.sql.table.Goods;
import com.app.shop.tools.CommenTips;
import com.app.shop.tools.RecyclerViewWithContextMenu;
import com.app.shop.ui.activity.AddGoodsActivity;
import com.app.shop.view.IGoodsView;

import java.util.List;

/**
 * create by 呵呵 on 2022/3/17.
 * V层  更新UI
 */
public class HomeFragment extends BaseFragment<GoodsPresenter, IGoodsView> implements IGoodsView {

    private View view;
    private RecyclerView recyclerView;
    private Context context;

    private int Load;  //登录状态

    private ImageView add;
    private LinearLayoutManager linearLayoutManagermanager;
    private GoodsAdapter goodsAdapter;
    private Goods good;

    public static HomeFragment NewInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(getLayoutId(), container, false);
        }
        recyclerView = view.findViewById(R.id.home_recycler);
        add = view.findViewById(R.id.home_add);
        context = getActivity();
        registerForContextMenu(recyclerView);
        return view;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override  //在 oncreate 中调用  最先执行
    protected GoodsPresenter createPresenter() {
        return new GoodsPresenter();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter.fatch();

        initevent();
        //未登录 点击事件无效
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddGoodsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initevent();
    }

    private void initevent() {

    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.goods_option,menu);
        RecyclerViewWithContextMenu.RecyclerViewContextInfo info=(RecyclerViewWithContextMenu.RecyclerViewContextInfo)menuInfo;
        if (info!=null&&info.getPosition()!=-1){
            goodsAdapter=(GoodsAdapter) recyclerView.getAdapter();
            good = goodsAdapter.getItem(info.getPosition());
        }
    }

    @Override   //长按事件  删除或修改商品
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        GoodsDBHelper helper=GoodsDBHelper.getInstance(getActivity(),1);
        helper.openWriteLink();
        switch (item.getItemId()){
            case R.id.good_reset:
                //修改操作
                CommenTips.shortTips(getActivity(), "reset。。。。。");
                break;
            case R.id.good_delete:
                helper.delete(good.getGname());
                CommenTips.shortTips(getActivity(), "delete。。。。。");
                break;
        }
        helper.closeLink();
        return super.onContextItemSelected(item);
    }



    @Override
    public void showGoodsView(List<Goods> goodsList) {
        linearLayoutManagermanager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);   //环境上下文,布局方向，倒序显示
        recyclerView.setLayoutManager(linearLayoutManagermanager);    /* recyclerView 为空  注意fragment的生命周期 oncreate -> oncreateView */
        goodsAdapter = new GoodsAdapter(goodsList, getActivity());
        recyclerView.setAdapter(goodsAdapter);
        //点击事件 单个商品 查看商品详情信息
        goodsAdapter.setOnGoodsItemClickListener(new GoodsAdapter.onGoodsItemClickListener() {
            @Override
            public void onItemClick(int position) {
                CommenTips.shortTips(getActivity(), "点击事件  商品" + position + "详情");
            }
        });
    }
}