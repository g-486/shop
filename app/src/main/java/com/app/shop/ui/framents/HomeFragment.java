package com.app.shop.ui.framents;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.shop.R;
import com.app.shop.adapter.GoodsAdapter;
import com.app.shop.adapter.SayAdapter;
import com.app.shop.base.BaseFragment;
import com.app.shop.databinding.FragmentHomeBinding;
import com.app.shop.presenter.GoodsPresenter;
import com.app.shop.sql.helper.GoodsDBHelper;
import com.app.shop.sql.helper.SayDBHelper;
import com.app.shop.sql.table.Goods;
import com.app.shop.sql.table.Say;
import com.app.shop.ui.activity.AddGoodsActivity;
import com.app.shop.ui.activity.ResetGoodActivity;
import com.app.shop.utils.SharedPreferencesUtils;
import com.app.shop.utils.ToastUtils;
import com.app.shop.utils.UIutils;
import com.app.shop.view.IGoodsView;

import java.util.ArrayList;
import java.util.List;

/**
 * create by 呵呵 on 2022/3/17.
 * V层  更新UI
 */
public class HomeFragment extends BaseFragment<GoodsPresenter, IGoodsView> implements IGoodsView {
    private FragmentHomeBinding binding;
    private static boolean sign;
    public static FragmentActivity context;

    private LinearLayoutManager linearLayoutManagermanager;
    private GoodsAdapter goodsAdapter;
    private List<Goods> listgood;

    public static HomeFragment NewInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        context = getActivity();
        sign = SharedPreferencesUtils.getSignState(context);
        return binding.getRoot();
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
        refreshData();
        presenter.fatch();
        initevent();
        //未登录 点击事件无效
        binding.homeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sign) {
                    Intent intent = new Intent(context, AddGoodsActivity.class);
                    startActivity(intent);
                } else {
                    ToastUtils.shortToast(getActivity(), "请检查登录！");
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sign) {
            initevent();
            refreshData();
        }

    }

    private void initevent() {
//        boolean flag=DButil.test();
//        Log.e("test",""+flag);
    }

    @Override
    public void showGoodsView(List<Goods> goodsList) {
        linearLayoutManagermanager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);   //环境上下文,布局方向，倒序显示
        binding.homeRecycler.setLayoutManager(linearLayoutManagermanager);    /* recyclerView 为空  注意fragment的生命周期 oncreate -> oncreateView */
        if (sign) {
            goodsAdapter = new GoodsAdapter(goodsList, getActivity());
            binding.homeRecycler.setAdapter(goodsAdapter);
            //点击事件 单个商品 查看商品详情信息
            goodsAdapter.setOnGoodsItemClickListener(new GoodsAdapter.onGoodsItemClickListener() {
                @Override//点击事件 单个商品 查看商品详情信息
                public void onItemClick(View view, int position, Goods good) {
                    Log.e("tag", "点击事件  商品" + position + "详情");
                    View v = getLayoutInflater().inflate(R.layout.detail_good, null);
                    PopupWindow window = new PopupWindow(v, ViewGroup.LayoutParams.MATCH_PARENT, UIutils.dip2px(getActivity(), 500));
                    if (window.isShowing()) window.dismiss();
                    window.setBackgroundDrawable(new BitmapDrawable());
                    window.setFocusable(true);
                    window.setOutsideTouchable(true);
                    window.update();
                    window.showAtLocation(getActivity().findViewById(R.id.home_recycler), Gravity.BOTTOM, 0, UIutils.dip2px(getActivity(), 59));
                    TextView name = v.findViewById(R.id.tv_good_name);
                    name.setText(good.getGname());
                    ImageView img = v.findViewById(R.id.iv_good_img);
                    img.setImageBitmap(BitmapFactory.decodeFile(good.getImage()));
                    TextView num = v.findViewById(R.id.tv_good_salenum);
                    num.setText(good.getSalenum() + "");
                    TextView taste = v.findViewById(R.id.tv_good_taste);
                    taste.setText(good.getTaste());
                    TextView desc = v.findViewById(R.id.tv_good_desc);
                    desc.setText(good.getDesc());
                    RecyclerView rvSay = v.findViewById(R.id.rv_say);
                    //评论展示
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    SayDBHelper sayDBHelper = SayDBHelper.getInstance(getActivity());
                    sayDBHelper.openReadLink();
                    List<Say> says = sayDBHelper.findByGood(good.getGid());
                    if (says != null) {
                        Log.e("tag", "null");
                        rvSay.setLayoutManager(layoutManager);
                        rvSay.setAdapter(new SayAdapter(says, getActivity()));
                    }
                    v.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            window.dismiss();
                        }
                    });
                }

                @Override
                public void onItemLongClick(View view, int position, Goods goods) {
                    View v = getLayoutInflater().inflate(R.layout.good_option, null);
                    PopupWindow window = new PopupWindow(v, 300, ViewGroup.LayoutParams.WRAP_CONTENT);
                    if (window.isShowing()) window.dismiss();
                    window.setBackgroundDrawable(new BitmapDrawable());
                    window.setFocusable(true);
                    window.setOutsideTouchable(true);
                    window.update();
                    window.showAsDropDown(view, 500, -150);
                    v.findViewById(R.id.tv_op1).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), ResetGoodActivity.class);
                            startActivity(intent);
                            window.dismiss();
                        }
                    });
                    v.findViewById(R.id.tv_op2).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            GoodsDBHelper helper = GoodsDBHelper.getInstance(getActivity());
                            if (helper.delete(goods.getGname()))
                                ToastUtils.shortToast(getActivity(), "删除成功");
                            window.dismiss();
                            refreshData();
                        }
                    });
                }
            });
        } else {
            listgood = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Goods good = new Goods("商品" + 1, 10.5, "试用商品", "不知道", "不知道", "不能吃");
                listgood.add(good);
            }
            goodsAdapter = new GoodsAdapter(listgood, getActivity());
        }
    }

    private void refreshData() {
        List<Goods> list = new ArrayList<>();
        GoodsDBHelper helper = GoodsDBHelper.getInstance(context);
        helper.getReadableDatabase();
        list = helper.queryAll();
        if (list.size() == 0) {
            binding.tvNoData.setVisibility(View.VISIBLE);
        } else {
            binding.tvNoData.setVisibility(View.GONE);
        }
        if (goodsAdapter != null) {
            goodsAdapter.setData(list);
        }
    }



}