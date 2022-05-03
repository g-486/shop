package com.app.shop.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * create by 呵呵 on 2022/4/30.
 */
public class ToastUtils {
    public static void shortToast(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
    public static void longToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
}
