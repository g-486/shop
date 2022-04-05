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

public class RegisterActivity extends BarActivity {

    private EditText ed_user;
    private EditText ed_pass;
    private EditText ed_repass;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setToolbar();
        setTitle("注册");
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        event();
    }

    private void init(){
        ed_user=findViewById(R.id.register_user);
        ed_pass=findViewById(R.id.register_pass);
        ed_repass=findViewById(R.id.register_repass);
        btn_register=findViewById(R.id.register_on);
    }
    private void event(){
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }
    private void save(){
        String user=ed_user.getText().toString();
        String pass=ed_pass.getText().toString();
        String repass=ed_repass.getText().toString();
        if (TextUtils.isEmpty(user)){
            CommenTips.longTips(this,"用户名不能为空，请输入用户名");
        }
        if(TextUtils.isEmpty(pass)||TextUtils.isEmpty(repass)){
            CommenTips.longTips(this,"输入密码不一致，请确认后重新输入！");
        }
        if (pass.equals(repass)&&UserInfo.saveInfo(this,user,pass)){
            Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(intent);
        }else {
            CommenTips.longTips(this,"注册失败");
        }
    }
}