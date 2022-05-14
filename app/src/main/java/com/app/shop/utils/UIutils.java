package com.app.shop.utils;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

/**
 * create by 呵呵 on 2022/4/30.
 */
public class UIutils {
    public static int dip2px(Context context, double dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5);
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
