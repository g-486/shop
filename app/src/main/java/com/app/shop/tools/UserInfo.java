package com.app.shop.tools;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * create by 呵呵 on 2022/3/16.
 */
public class UserInfo {
    private static SharedPreferences share;
    private static SharedPreferences.Editor editor;

    public static boolean saveInfo(Context context,String user,String pass){
        share=context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        editor.putString("user",user);
        editor.putString("pass",pass);
        editor.commit();
        return true;
    }

    public static boolean checkInfo(Context context,String user,String pass){
        share=context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        editor=share.edit();
        return user.equals(share.getString("user", "")) && pass.equals(share.getString("pass", ""));
    }

    public static Map<String,String> getInfo(Context context){
        Map<String,String> map=new HashMap<>();
        share=context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        String user=share.getString("user","");
        String pass=share.getString("pass","");
        map.put("user",user);
        map.put("pass",pass);
        return map;
    }
    public static boolean getRemember(Context context){
        share=context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        return share.getBoolean("isRemember",false);
    }
    public static void setRemember(Context context,Boolean bool){
        share=context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        editor=share.edit();
        editor.putBoolean("isRemember",bool);
        editor.commit();
    }
}
