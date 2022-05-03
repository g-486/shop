package com.app.shop.tools;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.app.shop.R;

/**
 * create by 呵呵 on 2022/4/11.
 */
public class GoodDetailShow {
    public static void getWindow(Context context,int layoutId){
        final Dialog dialog=new Dialog(context, R.style.DialogTheme);
        View view=View.inflate(context,layoutId,null);
        dialog.setContentView(view);

        Window window=dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.Bottom_popUpWindow);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        //控件和事件
    }
}
