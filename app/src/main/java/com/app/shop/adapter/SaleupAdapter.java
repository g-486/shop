package com.app.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.shop.R;
import com.app.shop.sql.table.Goods;

import java.util.List;

/**
 * create by 呵呵 on 2022/3/19.
 */
public class SaleupAdapter extends RecyclerView.Adapter<SaleupAdapter.GoodsHodler> {

    private List<Goods> data;
    private Context context;

    public SaleupAdapter(List<Goods> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public GoodsHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_saleup, parent, false);
        return new GoodsHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsHodler holder, int position) {
        holder.icon.setImageResource(R.mipmap.ic_launcher);
        holder.name.setText(data.get(position).getGname());
        holder.num.setText(String.valueOf(data.get(position).getSalenum()));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class GoodsHodler extends RecyclerView.ViewHolder {

        private ImageView icon;
        private TextView name,num;

        public GoodsHodler(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.saleup_icon);
            name = itemView.findViewById(R.id.saleup_name);
            num = itemView.findViewById(R.id.saleup_num);

            //点击事件   点解条目，可查看商品的更详细信息
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

    //监听器   点击事件接口  添加点击事件的方法
    private onGoodsItemClickListener clickListener;
    public interface onGoodsItemClickListener{
        void onItemClick(int position);
    }
    public void setOnGoodsItemClickListener(onGoodsItemClickListener listener){
        clickListener=listener;
    }
}
