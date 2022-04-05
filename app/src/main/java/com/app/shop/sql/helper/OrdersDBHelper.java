package com.app.shop.sql.helper;

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

    public OrdersDBHelper(Context context,int version) {
        super(context, DBname, null ,version);
    }

    public OrdersDBHelper(Context context) {
        super(context, DBname, null, DB_VERSION);
    }

    public OrdersDBHelper getInstance(Context context, int version) {
        if (mHelper == null && version > 0) {
            mHelper = new OrdersDBHelper(context,version);
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

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String drop = "DROP TABLE IF EXISTS" + table_name + ";";
        sqLiteDatabase.execSQL(drop);
        String create = "CREATE TABLE IF NOT EXISTS " + table_name + "("
                + "_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + "Uid integer," + "sumPrice FLOAT," + "level INTEGER,"
                + "FOODS VARCHER,DATE VARCHER,OTHER VARCHER,SAYS VARCHER" + ")";
        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldV, int newV) { }

    @Override
    public int delete(String condition) {
        return mDB.delete(table_name, condition, null);
    }

    @Override
    public long insert(List<Orders> list) {
        long reault = -1;
//        for (int i = 0; i < list.size(); i++) {
//            Orders item = list.get(i);
//            List<Orders> lists = new ArrayList<>();
//            if (item.getGname() != null) {
//                String name=String.format("Gname=%s",item.getGname());
//                lists=query(name);
//                if (lists.size()>0){
//                    update(item,name);
//                    //未完成
//                }
//            }
//            ContentValues cv=new ContentValues();
//            cv.put("uid",item.getUid());
//            cv.put("sumPrice",item.getSumPrice());
//            cv.put("level",item.getLevel());
//            cv.put("foods",item.getFoods());
//            cv.put("date",item.getDate());
//            cv.put("other",item.getOther());
//            cv.put("saya",item.getSays());
//            reault= mDB.insert(table_name,"",cv);
//            if (reault==-1){
//                return reault;
//            }
//        }
        return reault;
    }

    @Override
    public int update(Orders item, String name) {
//        ContentValues cv=new ContentValues();
//        cv.put("Uid",item.getUid());
//        cv.put("sumPrice",item.getSumPrice());
//        cv.put("level",item.getLevel());
//        cv.put("foods",item.getFoods());
//        cv.put("date",item.getDate());
//        cv.put("other",item.getOther());
//        cv.put("says",item.getSays());
//        return mDB.update(table_name,cv,name,null);
        return 0;
    }
    //查询所有
    @Override
    public List<Orders> query(String... condition) {
        openReadLink();
        String sql=String.format("select order(_id,Uid,sumPrice,level,foods,date,other,says)"+
                "from %s ",table_name);
        if (condition!=null){
            //条件查询
        }
        List<Orders> list=new ArrayList<>();
        Cursor cursor=mDB.rawQuery(sql,null);
        //取出记录
        while (cursor.moveToNext()){
            Orders order=new Orders();
            order.rowid=cursor.getInt(0);
            order.setUid(cursor.getInt(1));
            order.setSumPrice(cursor.getInt(2));
            order.setLevel(cursor.getInt(3));
            order.setFoods(cursor.getString(4));
            order.setDate(cursor.getString(5));
            order.setOther(cursor.getString(6));
            order.setSays(cursor.getString(7));
            list.add(order);
        }
        cursor.close();
        return list;
    }

    @Override
    public Orders queryBy(String conditon) {
        return null;
    }
}
