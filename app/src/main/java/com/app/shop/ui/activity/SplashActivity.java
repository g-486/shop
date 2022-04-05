package com.app.shop.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.app.shop.R;

public class SplashActivity extends BarActivity {

    private Button jump;
    private final Handler handler=new Handler();

    private final Runnable runnable=new Runnable() {
        @Override
        public void run() {
            toNextActivity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        init();
        event();
        handler.postDelayed(runnable,3000);
    }
    private void event(){
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.removeCallbacks(runnable);
                toNextActivity();
            }
        });
    }
    private void init(){
        jump=findViewById(R.id.splash_jump);
    }
    private void toNextActivity(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}