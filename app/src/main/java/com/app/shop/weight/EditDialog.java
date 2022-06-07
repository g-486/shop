package com.app.shop.weight;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.app.shop.R;
import com.app.shop.databinding.EditDialogBinding;

/**
 * create by 呵呵 on 2022/5/16.
 */
public class EditDialog extends Dialog {
    private TextView ok,no;
    private EditText appName;
    private String name;

    public EditDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void show() {
        super.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_dialog);
        setCanceledOnTouchOutside(false);
        initView();
        refreshView();
        event();
    }
    public String getName(){
        return name;
    }
    public void initView(){
        ok=findViewById(R.id.ok);
        no=findViewById(R.id.no);
        appName=findViewById(R.id.app_name);
    }
    private void refreshView(){
        name=appName.getText().toString();
    }
    private void event(){
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.OnOkClick();
                }
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.OnNoClick();
                }
            }
        });
    }

    public OnDialogClickListener listener;
    public void setOnDialogClickListener(OnDialogClickListener listener){
        this.listener=listener;
    }

    public interface OnDialogClickListener {
        public void OnOkClick();
        public void OnNoClick();
    }
}
