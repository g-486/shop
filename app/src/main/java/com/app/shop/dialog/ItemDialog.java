package com.app.shop.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.app.shop.R;

/**
 * create by 呵呵 on 2022/4/29.
 */
public class ItemDialog extends Dialog {
    private TextView op1, op2;
    private String str1,str2;

    public ItemDialog(@NonNull Context context) {
        super(context, R.style.itemDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_dialog);
        setCanceledOnTouchOutside(false);
        initView();
        refreshView();
        event();
    }

    @Override
    public void show() {
        super.show();
        refreshView();
    }

    private void initView(){
        op1=findViewById(R.id.tv_op1);
        op2=findViewById(R.id.tv_op2);
    }
    private void event(){
        op1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onListener!=null){
                    onListener.onOp1Click();
                }
            }
        });
        op2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onListener!=null){
                    onListener.onOp2Click();
                }
            }
        });
    }

    public ItemDialog setOp1(String str1) {
        this.str1=str1;
        return this;
    }

    public ItemDialog setOp2(String str2) {
        this.str2=str2;
        return this;
    }
    private void refreshView(){
        op1.setText(str1);
        op1.setVisibility(View.VISIBLE);
        op2.setText(str2);
        op2.setVisibility(View.VISIBLE);
    }

    public OnListener onListener;
    public ItemDialog setOnOptionListener(OnListener listener){
        this.onListener=listener;
        return this;
    }
    public interface OnListener{
        public void onOp1Click();
        public void onOp2Click();
    }
}
