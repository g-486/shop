package com.app.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.shop.R;
import com.app.shop.sql.table.Orders;

import java.util.List;

/**
 * create by 呵呵 on 2022/3/23.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.orderViewHolder> {

    private Context context;
    private List<Orders> data;

    public OrderAdapter(Context context, List<Orders> date) {
        this.context = context;
        this.data = date;
    }
    public void setData(List<Orders> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public orderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new orderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull orderViewHolder holder, int position) {
        Orders order = data.get(position);
        holder.name.setText("顾客  "+position);//通过user表查询
        holder.time.setText(order.getDate());
        holder.foods.setText(order.getFoods());
        holder.level.setText(order.getLevel()+"");
        holder.price.setText(order.getSumPrice()+"");
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    public class orderViewHolder extends RecyclerView.ViewHolder {
        private TextView name, time, foods, level,price;

        public orderViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.order_name);
            time = itemView.findViewById(R.id.order_time);
            foods = itemView.findViewById(R.id.order_foods);
            level = itemView.findViewById(R.id.order_level);
            price = itemView.findViewById(R.id.order_price);
            //点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener!=null){
                        clickListener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }
    private onOrdersItemClickListener clickListener;
    public void setOnOrdersItemClickListener(onOrdersItemClickListener listener){
        clickListener=listener;
    };
    public interface onOrdersItemClickListener{
        void onItemClick(int position);
    }
}
