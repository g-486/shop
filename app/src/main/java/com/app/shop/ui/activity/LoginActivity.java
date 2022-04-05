package com.app.shop.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.app.shop.R;
import com.app.shop.tools.CommenTips;
import com.app.shop.tools.UserInfo;

import java.util.Map;

public class LoginActivity extends BarActivity implements View.OnClickListener {

    private SharedPreferences share;
    private SharedPreferences.Editor editor;
    private EditText ed_user;
    private EditText ed_pass;
    private Button login;
    private TextView to_register;
    private TextView to_refound;
    private CheckBox remember;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setToolbar();
        setTitle("登录");
        init();
        remember.setChecked(UserInfo.getRemember(this));
        //自动获取
        if(UserInfo.getRemember(LoginActivity.this)){
            Map<String, String> info=UserInfo.getInfo(LoginActivity.this);
            ed_user.setText(info.get("user"));
            ed_pass.setText(info.get("pass"));
        }
        event();
    }

    private void init() {
        ed_pass = findViewById(R.id.login_pass);
        ed_user = findViewById(R.id.login_user);
        login = findViewById(R.id.login_on);
        to_register = findViewById(R.id.to_register);
        to_refound = findViewById(R.id.to_refound);
        remember = findViewById(R.id.login_remember);
    }

    private void event() {
        login.setOnClickListener(this);
        to_register.setOnClickListener(this);
        to_refound.setOnClickListener(this);
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                UserInfo.setRemember(LoginActivity.this,remember.isChecked());
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_on:
                onLogin();
                break;
            case R.id.to_register:
                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.to_refound:
                intent = new Intent(this, RefoundActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void onLogin(){
        String user=ed_user.getText().toString();
        String pass=ed_pass.getText().toString();
        if(TextUtils.isEmpty(user)||TextUtils.isEmpty(pass)){
            CommenTips.shortTips(this,"用户名或密码不能为空！");
        }
        if(UserInfo.checkInfo(LoginActivity.this,user,pass)){
            UserInfo.setRemember(LoginActivity.this,remember.isChecked());
            intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);

        }else {
            CommenTips.longTips(LoginActivity.this,"账号或密码错误，请重新输入！");
        }
    }
}