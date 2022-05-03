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
        String create =String.format("create table if not exists %s(" +
                "_id integer primary key autoincrement not null," +
                "gname varchar,price float,desc varchar,salenum integer," +
                "type varchar,image varchar,weight varchar," +
                "taste varchar)",table_name);
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }

    @Override
    public boolean insert(Goods goods) {
        openWriteLink();
        ContentValues cv=new ContentValues();
        cv.put("gname",goods.Gname);
        cv.put("price",goods.price);
        cv.put("desc",goods.desc);
        cv.put("weight",goods.weight);
        cv.put("type",goods.type);
        cv.put("image",goods.image);
        cv.put("taste",goods.taste);
        cv.put("salenum",0);
//        String sql="insert into goods(gname,price,desc,type,image,weight,taste,salenum) values (?,?,?,?,?,?,?,?)";
//        mDB.execSQL(sql,new Object[]{goods.Gname,goods.price,goods.desc,goods.type,goods.image,goods.weight,goods.taste,goods.salenum});
        return mDB.insert(table_name,null,cv)>0;
        //成功返回行号，失败返回-1
    }

    @Override
    public boolean delete(String condition) {
        openWriteLink();
        openReadLink();
        //删除  返回删除记录的条数
//        String sql="delete from goods where gname=?";
//        mDB.execSQL(sql,new Object[]{condition});
//        flag=mDB.delete(table_name,condition,null);
        return mDB.delete(table_name,"gname=?",new String[]{condition})>0;
    }
    @Override
    public boolean update(Goods item, String name) {
        openWriteLink();
        int flag=0;
        ContentValues cv=new ContentValues();
        cv.put("gname",item.Gname);
        cv.put("price",item.price);
        cv.put("desc",item.desc);
        cv.put("salenum",item.salenum);
        cv.put("type",item.type);
        cv.put("image",item.image);
        cv.put("weight",item.weight);
        cv.put("taste",item.taste);

        return mDB.update(table_name,cv,"gname=?",new String[]{name})>0;
//        String sql="update goods set price=?,desc=?,type=?,image=?,weight=?,taste=?) where gname=?";
//        mDB.execSQL(sql,new Object[]{item.price,item.desc,item.type,item.image,item.weight,item.taste,item.Gname});
//        flag=mDB.update(table_name,cv,name,null);
    }

    @Override
    public List<Goods> queryAll() {
        openReadLink();
        openWriteLink();
        List<Goods> list=new ArrayList<>();
//        String sql=String.format("select _id,gname,price,desc,salenum,type,image,weight,taste from %s",table_name);
        Cursor cursor=mDB.query(table_name,null,null,null,null,null,null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                Goods good=new Goods();
                good.Gid=cursor.getInt(cursor.getColumnIndex("_id"));
                good.Gname=cursor.getString(cursor.getColumnIndex("gname"));
                good.price=cursor.getDouble(cursor.getColumnIndex("price"));
                good.desc=cursor.getString(cursor.getColumnIndex("desc"));
                good.salenum=cursor.getInt(cursor.getColumnIndex("salenum"));
                good.type=cursor.getString(cursor.getColumnIndex("type"));
                good.image =cursor.getString(cursor.getColumnIndex("image"));
                good.weight=cursor.getString(cursor.getColumnIndex("weight"));
                good.taste=cursor.getString(cursor.getColumnIndex("taste"));
                list.add(good);
            }cursor.close();
        }
        return list;
    }

    @Override
    public List<Goods> queryBy(String conditon) {
        openReadLink();
        openWriteLink();
        List<Goods> list=new ArrayList<>();
        Cursor cursor=mDB.query(table_name,null,"gname=?",new String[]{conditon},null,null,null);
        if (cursor!=null){
            Goods good=new Goods();
            good.Gid=cursor.getInt(cursor.getColumnIndex("rowid"));
            good.Gname=cursor.getString(cursor.getColumnIndex("gname"));
            good.price=cursor.getDouble(cursor.getColumnIndex("price"));
            good.desc=cursor.getString(cursor.getColumnIndex("desc"));
            good.salenum=cursor.getInt(cursor.getColumnIndex("salename"));
            good.type=cursor.getString(cursor.getColumnIndex("type"));
            good.image =cursor.getString(cursor.getColumnIndex("image"));
            good.weight=cursor.getString(cursor.getColumnIndex("weight"));
            good.taste=cursor.getString(cursor.getColumnIndex("taste"));
            list.add(good);
        }
        return list;
    }
}
