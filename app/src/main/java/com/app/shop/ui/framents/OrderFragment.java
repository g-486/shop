package com.app.shop.ui.framents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.app.shop.R;
import com.app.shop.adapter.OrderAdapter;
import com.app.shop.adapter.OrderShowAdapter;
import com.app.shop.adapter.SaleupAdapter;
import com.app.shop.base.BaseFragment;
import com.app.shop.presenter.OrderPresenter;
import com.app.shop.sql.helper.GoodsDBHelper;
import com.app.shop.sql.table.Goods;
import com.app.shop.sql.table.Orders;
import com.app.shop.tools.CommenTips;
import com.app.shop.view.IOrderView;

import java.util.ArrayList;
import java.util.List;

/**
 * create by 呵呵 on 2022/3/17.
 *
 * 图表统计
 * 订单显示
 *
 */
public class OrderFragment extends BaseFragment<OrderPresenter, IOrderView> implements IOrderView {
    private View view;
    private RecyclerView orderReycler;
    private RecyclerView saleup;
    private FragmentActivity context;

    private List<Goods> Saleup;  //只放三个
    private LinearLayoutManager orderManager;
    private OrderAdapter orderAdapter;
    private ViewPager2 orderPager;

    private Switch aSwitch;

    public static OrderFragment newInstance(){
        OrderFragment fragment= new OrderFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view==null){
            view=inflater.inflate(getLayoutId(),container,false);
        }
        orderReycler = view.findViewById(R.id.order_recycler);
        saleup=view.findViewById(R.id.home_saleup);
        orderPager=view.findViewById(R.id.order_show);
        aSwitch=view.findViewById(R.id.switch_);
        context = getActivity();
        return view;
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

    private void initevent(){
        getData();
        LinearLayoutManager saleUpManager=new LinearLayoutManager(context,RecyclerView.HORIZONTAL,true);
        SaleupAdapter saleupAdapter=new SaleupAdapter(Saleup,context);
        saleup.setLayoutManager(saleUpManager);
        saleup.setAdapter(saleupAdapter);

        //订单显示方式
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    orderPager.setCurrentItem(1);
                }else {
                    orderPager.setCurrentItem(0);
                }
            }
        });
    }

    private void getData(){
        Saleup=new ArrayList<>();
        GoodsDBHelper helper=GoodsDBHelper.getInstance(getActivity(),1);
        Saleup=helper.queryOrder4();
//        for (int i=0;i<8;i++){
//            Goods good=new Goods();
//            good.setGname("apple");
//            good.setSalenum(1223);
//            Saleup.add(good);
//        }
    }

    private void showOrder(){
        List<Fragment> showOrderPages=new ArrayList<>();
        showOrderPages.add(new ListOrderFragment());
        showOrderPages.add(new TableOrderFragment());

        OrderShowAdapter orderShowAdapter=new OrderShowAdapter(getActivity().getSupportFragmentManager(), getLifecycle(),showOrderPages);
        orderPager = new ViewPager2(getActivity());
        orderPager.setAdapter(orderShowAdapter);
        orderPager.setCurrentItem(0);
        orderPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                orderPager.setCurrentItem(position);
            }
        });
    }

    @Override
    public void showOrderData(List<Orders> orders) {
        orderManager = new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        orderAdapter = new OrderAdapter(context,orders);
        orderReycler.setLayoutManager(orderManager);
        orderReycler.setAdapter(orderAdapter);
        //分割线  默认灰色
        orderReycler.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        //点击事件
        orderAdapter.setOnOrdersItemClickListener(new OrderAdapter.onOrdersItemClickListener() {
            @Override
            public void onItemClick(int position) {
                CommenTips.shortTips(context,"点击事件  查看订单详情");
            }
        });
    }
}
