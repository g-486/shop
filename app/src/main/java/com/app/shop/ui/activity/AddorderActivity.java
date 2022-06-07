package com.app.shop.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.app.shop.databinding.ActivityAddorderBinding;
import com.app.shop.sql.helper.OrdersDBHelper;
import com.app.shop.sql.table.Orders;
import com.app.shop.utils.ToastUtils;

public class AddorderActivity extends AppCompatActivity {
    private ActivityAddorderBinding binding;
    private OrdersDBHelper helper;
    private Orders order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddorderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        helper=new OrdersDBHelper(this);
        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    helper.openWriteLink();
                    getData();
                    if ( helper.insert(order)){
                        ToastUtils.shortToast(AddorderActivity.this,"添加成功");
                    }else ToastUtils.shortToast(AddorderActivity.this,"添加失败");
                }catch (Exception e){
                    e.printStackTrace();
                    e.getMessage();
                }
            }
        });
    }
    private void getData(){
        int uid=Integer.parseInt(binding.orderUid.getText().toString()+"");
        float level=Float.parseFloat(binding.orderLevel.getText().toString()+"");
        double price=Double.parseDouble(binding.orderSumprice.getText().toString()+"");
        String food=binding.orderFood.getText().toString()+"";
        String date=binding.orderData.getText().toString()+"";
        String other=binding.orderOther.getText().toString();
        helper = OrdersDBHelper.getInstance(AddorderActivity.this);
        order = new Orders();
        order.setUid(uid);
        order.setSumPrice(price);
        order.setLevel(level);
        order.setFoods(food);
        order.setOther(other);
        order.setDate(date);
    }
}