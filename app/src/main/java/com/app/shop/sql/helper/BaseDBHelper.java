package com.app.shop.sql.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * create by 呵呵 on 2022/3/18.
 */
public class BaseDBHelper extends SQLiteOpenHelper {
    private static final String DBname = "shop.db";

    public BaseDBHelper(@Nullable Context context) {
        super(context, DBname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_goods ="create table if not exists goods(" +
                "_id integer primary key autoincrement not null," +
                "gname varchar,price float,desc varchar,salenum integer," +
                "type varchar,image varchar,weight varchar," +
                "taste varchar)";
        sqLiteDatabase.execSQL(create_goods);
        String create_orders = "create table if not exists orders("
                + "_id integer primary key autoincrement not null,"
                + "uid integer," + "sumprice float," + "level integer,"
                + "foods varchar," + "date varchar," + "other varchar)";
        sqLiteDatabase.execSQL(create_orders);
        String create_say="create table if not exists say("+
                "_id integer primary key autoincrement not null,"+
                "gid integer,oid integer,datetime varchar,str varchar)";
        sqLiteDatabase.execSQL(create_say);
        String create_user = "create table if not exists user(" +
                "_id integer primary key autoincrement not null,"
                + "uname varcher)";
        sqLiteDatabase.execSQL(create_user);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //关闭数据库
    public void closeLink(){};

    //打卡数据库读链接
    public void openReadLink(){};

    //打开写链接
    public void openWriteLink(){};
}