package com.app.shop.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.app.shop.ui.activity.LoginActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * create by 呵呵 on 2022/3/8.
 */
public class CommenTips {
    private static final SimpleDateFormat DATE_FORMAT=new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static String DateToString(Date date){
        return DATE_FORMAT.format(date);
    }

    public static Date StringToDate(String date){
        Date date1=null;
        try {
            date1=DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    public static void shortTips(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
    public static void longTips(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
    public static void contentTips(Context context,String msg){
        new AlertDialog.Builder(context)
                .setTitle("")
                .setMessage(msg)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface ,int i) {
                        Intent intent=new Intent(context, LoginActivity.class);
                    }
                })
                .setNegativeButton("取消",null)
                .create().show();
    }
}
