package com.app.shop.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * create by 呵呵 on 2022/5/3.
 */
public class SharedPreferencesUtils {
    private String APP_CONFIG = "app_config";
    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;

    public static void setSignState(Context context, Boolean b) {
        sp = context.getSharedPreferences("app_config", Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.putBoolean("sign", b);
        editor.commit();
    }

    public static boolean getSignState(Context context) {
        sp = context.getSharedPreferences("app_config", Context.MODE_PRIVATE);
        return sp.getBoolean("sign", false);
    }

    public static void setAppName(Context context,String name){
        sp = context.getSharedPreferences("app_config", Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.putString("app_name", name);
        editor.commit();
    }
    public static String getAppName(Context context){
        sp = context.getSharedPreferences("app_config", Context.MODE_PRIVATE);
        return sp.getString("app_name", "app_name");
    }

}
