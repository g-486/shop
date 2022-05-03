package com.app.shop.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.shop.R;
import com.app.shop.tools.CommenTips;
import com.app.shop.tools.UserInfo;
import com.app.shop.utils.ToastUtils;

public class RefoundActivity extends BarActivity {

    private EditText ed_user,ed_pass,ed_repass;
    private Button btn_refound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refound);
        setToolbar();
        setTitle("找回密码");
        ininview();
        ininevent();
    }
    private void ininview(){
        ed_user=findViewById(R.id.refound_user);
        ed_pass=findViewById(R.id.refound_pass);
        ed_repass=findViewById(R.id.refound_repass);
        btn_refound=findViewById(R.id.refound_on);
    }
    private void ininevent(){
        btn_refound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refind();
            }
        });
    }
    private void refind(){
        String user=ed_user.getText().toString();
        String pass=ed_pass.getText().toString();
        String repass=ed_repass.getText().toString();
        if (TextUtils.isEmpty(user)){
            ToastUtils.shortToast(this,"用户名不能为空，请输入用户名");
        }
        if(TextUtils.isEmpty(pass)||TextUtils.isEmpty(repass)){
            ToastUtils.shortToast(this,"输入密码不一致，请确认后重新输入！");
        }
        if (pass.equals(repass)&& UserInfo.saveInfo(this,user,pass)){
            Intent intent=new Intent(RefoundActivity.this,LoginActivity.class);
            startActivity(intent);
        }else {
            ToastUtils.longToast(this,"注册失败");
        }
    }
}