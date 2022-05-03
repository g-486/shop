package com.app.shop.ui.framents;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.shop.R;
import com.app.shop.adapter.OrderAdapter;
import com.app.shop.adapter.SaleupAdapter;
import com.app.shop.base.BaseFragment;
import com.app.shop.databinding.FragmentOrderBinding;
import com.app.shop.presenter.OrderPresenter;
import com.app.shop.sql.helper.GoodsDBHelper;
import com.app.shop.sql.helper.SayDBHelper;
import com.app.shop.sql.table.Goods;
import com.app.shop.sql.table.Orders;
import com.app.shop.sql.table.Say;
import com.app.shop.utils.SharedPreferencesUtils;
import com.app.shop.utils.ToastUtils;
import com.app.shop.utils.UIutils;
import com.app.shop.view.IOrderView;

import java.util.ArrayList;
import java.util.List;

/**
 * create by 呵呵 on 2022/3/17.
 * <p>
 * 图表统计
 * 订单显示
 */
public class OrderFragment extends BaseFragment<OrderPresenter, IOrderView> implements IOrderView, View.OnClickListener {
    private FragmentOrderBinding binding;
    private static boolean sign;

    private View view;
    public static FragmentActivity context;

    private List<Goods> saleup = new ArrayList<>();  //按salenum 排序
    private LinearLayoutManager orderManager;
    private OrderAdapter orderAdapter;

    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOrderBinding.inflate(inflater, container, false);
        context = getActivity();
        sign = SharedPreferencesUtils.getSignState(context);
        return binding.getRoot();
    }

    //@Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected OrderPresenter createPresenter() {
        return new OrderPresenter();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter.fatch();
    }

    @Override
    public void onStart() {
        super.onStart();
        initevent();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.fatch();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {

    }

    private void initevent() {
        if (sign) {
            getData();
            LinearLayoutManager saleUpManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
            SaleupAdapter saleupAdapter = new SaleupAdapter(saleup, context);
            binding.goodSaleup.setLayoutManager(saleUpManager);
            binding.goodSaleup.setAdapter(saleupAdapter);
            saleupAdapter.setOnGoodsItemClickListener(new SaleupAdapter.onGoodsItemClickListener() {
                @Override
                public void onItemClick(View view, int position, Goods good) {
                    View v = getLayoutInflater().inflate(R.layout.detail_good, null);
                    PopupWindow window = new PopupWindow(v, ViewGroup.LayoutParams.MATCH_PARENT, UIutils.dip2px(getActivity(), 500));
                    if (window.isShowing()) window.dismiss();
                    window.setBackgroundDrawable(new BitmapDrawable());
                    window.setFocusable(true);
                    window.setOutsideTouchable(true);
                    window.update();
                    window.showAtLocation(binding.orderRecycler, Gravity.BOTTOM, 0, UIutils.dip2px(getActivity(), 59));
                    TextView name = v.findViewById(R.id.tv_good_name);
                    name.setText(good.Gname);
                    ImageView img = v.findViewById(R.id.iv_good_img);
                    img.setImageBitmap(BitmapFactory.decodeFile(good.image));
                    TextView num = v.findViewById(R.id.tv_good_salenum);
                    num.setText(good.salenum + "");
                    TextView taste = v.findViewById(R.id.tv_good_taste);
                    taste.setText(good.taste);
                    TextView desc = v.findViewById(R.id.tv_good_desc);
                    desc.setText(good.desc);
                    RecyclerView rvSay = v.findViewById(R.id.rv_say);
                    //评论展示
                    SayDBHelper sayDBHelper = SayDBHelper.getInstance(getActivity(), 1);
                    List<Say> says = new ArrayList<>();
//                if (sayDBHelper.findByGood(good.Gid)!=null){
//                    Log.e("tag","null");
//                    rvSay.setAdapter(new SayAdapter(says,getActivity()));
//                }
                    v.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            window.dismiss();
                        }
                    });
                }
            });
        } else ToastUtils.shortToast(context, "请检查是否成功登录");
    }

    private void getData() {
        GoodsDBHelper helper = GoodsDBHelper.getInstance(getActivity(), 1);
        saleup = helper.queryAll();
        helper.closeLink();
    }

    @Override
    public void showOrderData(List<Orders> orders) {
        if (sign){
            orderManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
            orderAdapter = new OrderAdapter(context, orders);
            binding.orderRecycler.setLayoutManager(orderManager);
            binding.orderRecycler.setAdapter(orderAdapter);
            //分割线  默认灰色
            binding.orderRecycler.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
            //点击事件
            orderAdapter.setOnOrdersItemClickListener(new OrderAdapter.OnOrdersItemClickListener() {
                @Override
                public void onItemClick(View view, int position, Orders order) {
                    View v = getLayoutInflater().inflate(R.layout.detail_order, null);
                    PopupWindow window = new PopupWindow(v, ViewGroup.LayoutParams.MATCH_PARENT, UIutils.dip2px(getActivity(), 300));
                    if (window.isShowing()) window.dismiss();
                    window.setBackgroundDrawable(new BitmapDrawable());
                    window.setFocusable(true);
                    window.setOutsideTouchable(true);
                    window.update();
                    window.showAtLocation(binding.orderRecycler, Gravity.BOTTOM, 0, UIutils.dip2px(getActivity(), 59));
                }
            });
        }else {

            ToastUtils.shortToast(context, "请检查是否成功登录");
        }
    }
}
