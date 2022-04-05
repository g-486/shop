package com.app.shop.sql.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.shop.sql.Dao.GoodsDao;
import com.app.shop.sql.table.Goods;

import java.util.ArrayList;
import java.util.List;

/**
 * create by 呵呵 on 2022/3/18.
 */
public class GoodsDBHelper extends BaseDBHelper implements GoodsDao {
    private static final String DBname = "shop.db";
    private static final int DB_VERSION = 1;
    private final String table_name = "goods";
    private static GoodsDBHelper mHelper = null;
    private SQLiteDatabase mDB = null;

    private GoodsDBHelper(Context context) {
        super(context, DBname, null, DB_VERSION);
    }

    private GoodsDBHelper(Context context, int version) {
        super(context, DBname, null, version);
    }

    public static GoodsDBHelper getInstance(Context context, int version) {
        if (mHelper == null && version > 0) {
            mHelper = new GoodsDBHelper(context, version);
        } else if (mHelper == null) {
            mHelper = new GoodsDBHelper(context);
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
    public void onCreate(SQLiteDatabase db) {
        String drop = "drop table if exists " + table_name + ";";
        db.execSQL(drop);
        String create = "create table if not exists " + table_name + "("
                + "_id integer primary key autoincrement not null,"
                + "Gname varchar," + "price float," + "desc varchar,"
                + "salenum integer" + ")";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }

    @Override
    public int delete(String name) {
        openReadLink();
        return mDB.delete(table_name, name, null);
    }

    //返回-1为该商品已存在
    @Override
    public long insert(List<Goods> goods) {
        long reault = -1;
        openReadLink();
        openWriteLink();
        for (int i = 0; i < goods.size(); i++) {
            Goods item = goods.get(i);
            List<Goods> lists = new ArrayList<>();
            if (item.getGname() != null) {
                String name = String.format("Gname=%s", item.getGname());
                lists = query(name);
                if (lists.size() > 0) {
                    update(item, name);
                    reault = lists.get(0).rowid;
                    continue;
                }
            }
            ContentValues cv = new ContentValues();
            cv.put("Gid", item.getGid());
            cv.put("Gname", item.getGname());
            cv.put("price", item.getPrice());
            cv.put("desc", item.getDesc());
            cv.put("salenum", item.getSalenum());
            reault = mDB.insert(table_name, "", cv);
            if (reault == -1) {
                return reault;
            }
        }
        return reault;
    }

    @Override
    public int update(Goods goods, String name) {
        openWriteLink();
        ContentValues cv = new ContentValues();
        cv.put("Gname", goods.getGname());
        cv.put("price", goods.getPrice());
        cv.put("desc", goods.getDesc());
        cv.put("salenum", goods.getSalenum());
        return mDB.update(table_name, cv, name, null);
    }

    @Override
    public List<Goods> query(String... name) {
        openReadLink();
        String sql = String.format("select goods(rowid,_id,Gname,price,desc,salenum) " +
                "from %s where %s;", table_name, name);
        if (name==null){
            sql="select * from "+table_name;
        }
        List<Goods> list = new ArrayList<>();
        Cursor cursor = mDB.rawQuery(sql, null);
        //取出记录
        while (cursor.moveToNext()) {
            Goods goods = new Goods();
            goods.rowid = cursor.getInt(0);
            goods.setGid(cursor.getInt(1));
            goods.setGname(cursor.getString(2));
            goods.setPrice(cursor.getDouble(3));
            goods.setDesc(cursor.getString(4));
            goods.setSalenum(cursor.getInt(5));
            list.add(goods);
        }
        cursor.close();
        return list;
    }

    public List<Goods> queryOrder4(){
        openReadLink();
        String sql="select * from goods order by salenum DESC LIMIT 0,4";
        List<Goods> list = new ArrayList<>();
        Cursor cursor = mDB.rawQuery(sql, null);
        //取出记录
        while (cursor.moveToNext()) {
            Goods goods = new Goods();
            goods.setGid(cursor.getInt(1));
            goods.setGname(cursor.getString(2));
            goods.setPrice(cursor.getDouble(3));
            goods.setSalenum(cursor.getInt(5));
            list.add(goods);
        }
        cursor.close();
        return list;
    }

    @Override
    public Goods queryBy(String name) {
        openReadLink();
        Goods goods = null;
        List<Goods> goodsList = query(String.format("Gname=%s", name));
        goods = goodsList.get(0);
        return goods;
    }
}
