package com.app.shop.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.GoodsHodler> {

    private List<Goods> data;
    private Context context;

    public GoodsAdapter(List<Goods> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public void setData(List<Goods> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GoodsHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_goods, parent, false);
        return new GoodsHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsHodler holder, int position) {
        String path = data.get(position).getImage();
        if (path != null && !path.equals("")) {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            holder.icon.setImageBitmap(bitmap);
        } else {
            //使用默认的图片
            holder.icon.setImageResource(R.drawable.ic_food);
        }
        holder.name.setText(data.get(position).getGname());
        holder.desc.setText(data.get(position).getDesc());
        holder.price.setText(String.valueOf(data.get(position).getPrice()));
        holder.num.setText(String.valueOf(data.get(position).getSalenum()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener != null) {
                    clickListener.onItemClick(view, holder.getAdapterPosition(), data.get(holder.getAdapterPosition()));
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (clickListener != null) {
                    clickListener.onItemLongClick(view, holder.getAdapterPosition(), data.get(holder.getAdapterPosition()));
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public Goods getItem(int position) {
        if (data != null && (data.size() >= position)) {
            return data.get(position);
        }
        return null;
    }

    public class GoodsHodler extends RecyclerView.ViewHolder {

        private ImageView icon;
        private TextView name, desc, price, num;

        public GoodsHodler(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.iv_good);
            name = itemView.findViewById(R.id.goods_name);
            desc = itemView.findViewById(R.id.goods_desc);
            price = itemView.findViewById(R.id.goods_price);
            num = itemView.findViewById(R.id.sale_num);

        }
    }

    //监听器   点击事件接口  添加点击事件的方法
    private onGoodsItemClickListener clickListener;
    public interface onGoodsItemClickListener {
        void onItemClick(View view,int position,Goods goods);
        void onItemLongClick(View view, int position, Goods goods);
    }
    public void setOnGoodsItemClickListener(onGoodsItemClickListener listener) {
        clickListener = listener;
    }
}
