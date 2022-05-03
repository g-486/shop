package com.app.shop.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * create by 呵呵 on 2022/5/3.
 */
public class SharedPreferencesUtils {
    private String APP_CONFIG="app_config";

    public static void setSignState(Context context,Boolean b){
        SharedPreferences sp = context.getSharedPreferences("app_config",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("sign",b);
        editor.commit();
    }
    public static boolean getSignState(Context context){
        SharedPreferences sp = context.getSharedPreferences("app_config",Context.MODE_PRIVATE);
        return sp.getBoolean("sign",false);
    }
}
