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
import com.app.shop.tools.UserInfo;
import com.app.shop.utils.SharedPreferencesUtils;
import com.app.shop.utils.ToastUtils;

import java.util.Map;

public class LoginActivity extends BarActivity implements View.OnClickListener {

    private SharedPreferences share;
    private SharedPreferences.Editor editor;
    private EditText ed_user;
    private EditText ed_pass;
    private Button login;
    private TextView to_register;
    private TextView to_refound;
    private TextView shopName;
    private CheckBox remember;
    private Intent intent;
    private TextView loginIn;

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
        loginIn = findViewById(R.id.login_in);
        to_register = findViewById(R.id.to_register);
        to_refound = findViewById(R.id.to_refound);
        remember = findViewById(R.id.login_remember);
        shopName = findViewById(R.id.shop_name1);
    }

    private void event() {
        login.setOnClickListener(this);
        loginIn.setOnClickListener(this);
        to_register.setOnClickListener(this);
        to_refound.setOnClickListener(this);
        String name = SharedPreferencesUtils.getAppName(this);
        shopName.setText(name);
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
                finish();
                break;
            case R.id.to_register:
                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.to_refound:
                intent = new Intent(this, RefoundActivity.class);
                startActivity(intent);
                break;
            case R.id.login_in:
                SharedPreferencesUtils.setSignState(this,false);
                intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void onLogin(){
        String user=ed_user.getText().toString();
        String pass=ed_pass.getText().toString();
        if(TextUtils.isEmpty(user)||TextUtils.isEmpty(pass)){
            ToastUtils.shortToast(this,"用户名或密码不能为空！");
        }
        if(UserInfo.checkInfo(LoginActivity.this,user,pass)){
            UserInfo.setRemember(LoginActivity.this,remember.isChecked());
            SharedPreferencesUtils.setSignState(this,true);
            intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }else {
            ToastUtils.shortToast(LoginActivity.this,"账号或密码错误，请重新输入！");
        }
    }
}