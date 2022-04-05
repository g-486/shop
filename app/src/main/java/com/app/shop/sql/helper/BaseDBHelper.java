package com.app.shop.sql.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * create by 呵呵 on 2022/3/18.
 */
public abstract class BaseDBHelper extends SQLiteOpenHelper {

    public BaseDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //关闭数据库
    public abstract void closeLink();

    //打卡数据库读链接
    public abstract SQLiteDatabase openReadLink();

    //打开写链接
    public abstract SQLiteDatabase openWriteLink();
}