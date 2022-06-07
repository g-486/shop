package com.app.shop.sql.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

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

    public OrdersDBHelper(@Nullable Context context) {
        super(context);
    }

    public static OrdersDBHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new OrdersDBHelper(context);
        }
        mHelper.getReadableDatabase();
        mHelper.getWritableDatabase();
        return mHelper;
    }

    public void closeLink() {
        if (mDB.isOpen()) {
            mDB.close();
        }
        if (mDB != null) {
            mDB = null;
        }
    }

    public void openReadLink() {
        if (mDB == null || !mDB.isOpen()) {
            mDB = mHelper.getReadableDatabase();
        }
    }

    public void openWriteLink() {
        if (mDB == null || !mDB.isOpen()) {
            mDB = mHelper.getWritableDatabase();
        }
    }

    //只在第一次打开数据库时执行
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        String drop = String.format("drop table if exists %s ;", table_name);
//        sqLiteDatabase.execSQL(drop);
//        String create = String.format("create table if not exists %s("
//                + "_id integer primary key autoincrement not null,"
//                + "uid integer," + "sumprice float," + "level integer,"
//                + "foods varchar," + "date varchar," + "other varchar)", table_name);
//        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public boolean insert(Orders orders) {
        openWriteLink();
        ContentValues cv = new ContentValues();
        cv.put("uid", orders.getUid());
        cv.put("date", orders.getDate());
        cv.put("sumprice", orders.getSumPrice());
        cv.put("level", orders.getLevel());
        cv.put("foods", orders.getFoods());
        cv.put("other", orders.getOther());
//        String sql="insert into orders(uid,sumprice,level,foods,date,other) values (?,?,?,?,?,?)";
//        mDB.execSQL(sql,new Object[]{orders.getUid(), orders.getSumPrice(),orders.getLevel(),orders.getFoods(),orders.getDate(),orders.getOther()});
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
        Log.e("test", mDB.toString() + "");
//        String sql = String.format("select _id,uid,sumprice,level,foods,data,other from %s", table_name);
        List<Orders> list = new ArrayList<>();
        Cursor cursor = mDB.query(table_name, null, null, null, null, null, "_id desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Orders order = new Orders();
                order.setOid(cursor.getInt(cursor.getColumnIndex("_id")));
                order.setUid(cursor.getInt(cursor.getColumnIndex("uid")));
                order.setSumPrice(cursor.getDouble(cursor.getColumnIndex("sumprice")));
                order.setLevel(cursor.getFloat(cursor.getColumnIndex("level")));
                order.setFoods(cursor.getString(cursor.getColumnIndex("foods")));
                order.setDate(cursor.getString(cursor.getColumnIndex("date")));
                order.setOther(cursor.getString(cursor.getColumnIndex("other")));
                list.add(order);
            }
        }
        cursor.close();
        return list;
    }

    @Override
    public List<Orders> queryBy(String conditon) {
        openReadLink();
        List<Orders> list = new ArrayList<>();
        Cursor cursor = mDB.query(table_name, null, null, null, null, null, "_id desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Orders order = new Orders();
                order.setOid(cursor.getInt(cursor.getColumnIndex("_id")));
                order.setUid(cursor.getInt(cursor.getColumnIndex("uid")));
                order.setSumPrice(cursor.getDouble(cursor.getColumnIndex("sumprice")));
                order.setLevel(cursor.getFloat(cursor.getColumnIndex("level")));
                order.setFoods(cursor.getString(cursor.getColumnIndex("foods")));
                order.setDate(cursor.getString(cursor.getColumnIndex("date")));
                order.setOther(cursor.getString(cursor.getColumnIndex("other")));
                list.add(order);
            }
        }
        cursor.close();
        return list;
    }

    public List<Orders> findBy(String id, String date) {
        openReadLink();
        String where = "";
        String[] position = {};
        if (!id.equals("") && date.equals("")) {
            where = "uid=?";
            position = new String[]{id};
        }
        if (id.equals("") && !date.equals("")) {
            where = "uid=?";
            position = new String[]{id};
        }
        if (!id.equals("") && !date.equals("")) {
            where = "uid=? and date>?";
            position = new String[]{id, date};
        }
        List<Orders> list = new ArrayList<>();
        Cursor cursor = mDB.query(table_name, null, where, position, null, null, "_id desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                try {
                    Orders order = new Orders();
                    order.setOid(cursor.getInt(cursor.getColumnIndex("_id")));
                    order.setUid(cursor.getInt(cursor.getColumnIndex("uid")));
                    order.setSumPrice(cursor.getDouble(cursor.getColumnIndex("sumprice")));
                    order.setLevel(cursor.getFloat(cursor.getColumnIndex("level")));
                    order.setFoods(cursor.getString(cursor.getColumnIndex("foods")));
                    order.setDate(cursor.getString(cursor.getColumnIndex("date")));
//                    order.setOther(cursor.getString(cursor.getColumnIndex("other")));
                    list.add(order);
                    Log.e("aaa", list.size() + "");
                } catch (Exception e) {
                    e.printStackTrace();
                    e.getMessage();
                }

            }
            Log.e("aa", "list.size()=" + list.size());
        }
        cursor.close();
        return list;
    }
}
