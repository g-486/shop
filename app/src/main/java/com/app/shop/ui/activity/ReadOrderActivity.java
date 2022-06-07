package com.app.shop.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.shop.adapter.OrderAdapter;
import com.app.shop.databinding.ActivityReadOrderBinding;
import com.app.shop.sql.helper.OrdersDBHelper;
import com.app.shop.sql.helper.UserDBHelper;
import com.app.shop.sql.table.Orders;

import java.util.ArrayList;
import java.util.List;

public class ReadOrderActivity extends BarActivity {
    private ActivityReadOrderBinding binding;
    private String date;
    private String name;
    private List<Orders> ordersList;
    private OrderAdapter adapter;
    private OrdersDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReadOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setToolbar();
        setTitle("订单");
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initEvent();
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        helper = OrdersDBHelper.getInstance(this);
        ordersList = helper.queryAll();
        adapter = new OrderAdapter(this, ordersList);
        binding.readOrders.setLayoutManager(manager);
        binding.readOrders.setAdapter(adapter);
    }

    private void initEvent() {
        //通过用户时间查询  1，uid  2,date  3,uid+date
        binding.orderFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = binding.orderWhere1.getText().toString() + "";
                date = binding.orderWhere2.getText().toString() + "";
                UserDBHelper userDBHelper = UserDBHelper.getInstance(getApplicationContext());
                int Id = userDBHelper.findByName(name).getUid();
                List<Orders> list = new ArrayList<>();
                Orders order = new Orders();
                if (name.equals("") && date.equals("")) {
                    refreshData(ordersList);
                } else if ((!name.equals("")) && date.equals("")) {
                    for (int i = 0; i < ordersList.size(); i++) {
                        order = ordersList.get(i);
                        if (Id == order.getUid()) {
                            list.add(order);
                        }
                    }
                    refreshData(list);
                } else if (name.equals("") && (!date.equals(""))) {
                    for (int i = 0; i < ordersList.size(); i++) {
                        order = ordersList.get(i);
                        double d=Double.parseDouble(date);
                        double d1=Double.parseDouble(order.getDate().substring(0,6));
                        if (d<=d1) {
                            list.add(order);
                        }
                    }
                    refreshData(list);
                } else {
                    for (int i = 0; i < ordersList.size(); i++) {
                        order = ordersList.get(i);
                        double d=Double.parseDouble(date);
                        double d1=Double.parseDouble(order.getDate().substring(0,6));
                        if (Id != order.getUid()) {
                            continue;
                        }
                        if (d>d1) {
                            continue;
                        }
                        list.add(order);
                    }
                    refreshData(list);
                }
            }
        });
        adapter.setOnOrdersItemClickListener(new OrderAdapter.OnOrdersItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Orders order) {

            }
        });
    }

    private void refreshData(List<Orders> list) {
        if (ordersList.size() != 0)
            if (adapter != null) {
                adapter.setData(list);
            }
    }
}