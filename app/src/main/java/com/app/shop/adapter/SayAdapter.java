package com.app.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.shop.R;
import com.app.shop.sql.table.Say;

import java.util.List;
import java.util.zip.Inflater;

/**
 * create by 呵呵 on 2022/5/3.
 */
public class SayAdapter extends RecyclerView.Adapter<SayAdapter.Holder> {
    private List<Say> data;
    private Context context;

    public SayAdapter(List<Say> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_say,null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        String time=data.get(position).getDatetime().toString();
        holder.time.setText(time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8)+" "+time.substring(8,10)+":"+time.substring(10));
        holder.str.setText(data.get(position).getStr());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView time,str;
        public Holder(@NonNull View itemView) {
            super(itemView);
            time=itemView.findViewById(R.id.say_time);
            str=itemView.findViewById(R.id.say_str);
        }
    }

}
