package com.app.shop.sql.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.shop.sql.Dao.OrdersDao;
import com.app.shop.sql.table.Orders;

import java.util.ArrayList;
import java.util.List;

/**
 * create by 呵呵 on 2022/3/18.
 */
public class OrdersDBHelper extends BaseDBHelper implements OrdersDao {
    private static final String DBname = "shop.db";
    private static final int DB_VERSION = 1;
    private final String table_name = "orders";
    private static OrdersDBHelper mHelper = null;
    private SQLiteDatabase mDB = null;

    public OrdersDBHelper(Context context, int version) {
        super(context, DBname, null, version);
    }

    public OrdersDBHelper(Context context) {
        super(context, DBname, null, DB_VERSION);
    }

    public OrdersDBHelper getInstance(Context context, int version) {
        if (mHelper == null && version > 0) {
            mHelper = new OrdersDBHelper(context, version);
        } else if (mHelper == null) {
            mHelper = new OrdersDBHelper(context);
        }
        return mHelper;
    }

    @Override
    public void closeLink() {
        if (mDB.isOpen()) {
            mDB.close();
        }
        if (mDB != null) {
            mDB = null;
        }
    }

    @Override
    public SQLiteDatabase openReadLink() {
        if (mDB == null || !mDB.isOpen()) {
            mDB = mHelper.getReadableDatabase();
        }
        return mDB;
    }

    @Override
    public SQLiteDatabase openWriteLink() {
        if (mDB == null || !mDB.isOpen()) {
            mDB = mHelper.getWritableDatabase();
        }
        return mDB;
    }

    //只在第一次打开数据库时执行
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String drop = String.format("drop table if exists %s ;", table_name);
        sqLiteDatabase.execSQL(drop);
        String create = String.format("create table if not exists %s("
                + "_id integer primary key autoincrement not null,"
                + "uid integer," + "sumprice float," + "level integer,"
                + "foods varchar," + "date varchar," + "other varchar)", table_name);
        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public boolean insert(Orders orders) {
        openWriteLink();
        ContentValues cv = new ContentValues();
        cv.put("Uid", orders.Uid);
        cv.put("sumprice", orders.sumPrice);
        cv.put("level", orders.level);
        cv.put("foods", orders.foods);
        cv.put("data", orders.date);
        cv.put("other", orders.other);
//        String sql="insert into orders(uid,sumprice,level,foods,data,other) values (?,?,?,?,?,?)";
//        mDB.execSQL(sql,new Object[]{orders.Uid, orders.sumPrice,orders.level,orders.foods,orders.date,orders.other});
        return mDB.insert(table_name, null, cv) > 0;
    }

    @Override
    public boolean delete(String condition) {
        return false;
    }

    @Override
    public boolean update(Orders item, String name) {
        return false;
    }

    @Override
    public List<Orders> queryAll() {
        openReadLink();
//        String sql = String.format("select _id,uid,sumprice,level,foods,data,other from %s", table_name);
        List<Orders> list = new ArrayList<>();
        Cursor cursor = mDB.query(table_name, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Orders order = new Orders();
                order.Oid = cursor.getInt(cursor.getColumnIndex("rowid"));
                order.Uid = cursor.getInt(cursor.getColumnIndex("uid"));
                order.sumPrice = cursor.getDouble(cursor.getColumnIndex("sumprice"));
                order.level = cursor.getInt(cursor.getColumnIndex("level"));
                order.foods = cursor.getString(cursor.getColumnIndex("foods"));
                order.date = cursor.getString(cursor.getColumnIndex("data"));
                order.other = cursor.getString(cursor.getColumnIndex("other"));
                list.add(order);
            }
        }cursor.close();
        return list;
    }

    @Override
    public List<Orders> queryBy(String conditon) {
        openReadLink();
        List<Orders> list=new ArrayList<>();
        Cursor cursor = mDB.query(table_name, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Orders order = new Orders();
                order.Oid = cursor.getInt(cursor.getColumnIndex("rowid"));
                order.Uid = cursor.getInt(cursor.getColumnIndex("uid"));
                order.sumPrice = cursor.getDouble(cursor.getColumnIndex("sumprice"));
                order.level = cursor.getInt(cursor.getColumnIndex("level"));
                order.foods = cursor.getString(cursor.getColumnIndex("foods"));
                order.date = cursor.getString(cursor.getColumnIndex("data"));
                order.other = cursor.getString(cursor.getColumnIndex("other"));
                list.add(order);
            }
        }cursor.close();
        return list;
    }
}
