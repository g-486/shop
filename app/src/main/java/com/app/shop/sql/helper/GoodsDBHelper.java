package com.app.shop.sql.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

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

    public GoodsDBHelper(@Nullable Context context) {
        super(context);
    }

    public static GoodsDBHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper=new GoodsDBHelper(context);
        }
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

    @Override
    public void onCreate(SQLiteDatabase db) {
//        String drop =String.format("drop table if exists %s;",table_name);
//        db.execSQL(drop);
//        String create =String.format("create table if not exists %s(" +
//                "_id integer primary key autoincrement not null," +
//                "gname varchar,price float,desc varchar,salenum integer," +
//                "type varchar,image varchar,weight varchar," +
//                "taste varchar)",table_name);
//        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }

    @Override
    public boolean insert(Goods goods) {
        openWriteLink();
        ContentValues cv=new ContentValues();
        cv.put("gname",goods.getGname());
        cv.put("price",goods.getPrice());
        cv.put("desc",goods.getDesc());
        cv.put("weight",goods.getWeight());
        cv.put("type",goods.getType());
        cv.put("image",goods.getImage());
        cv.put("taste",goods.getTaste());
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
//        int flag=0;
        ContentValues cv=new ContentValues();
        cv.put("gname",item.getGname());
        cv.put("price",item.getPrice());
        cv.put("desc",item.getDesc());
        cv.put("salenum",item.getSalenum());
        cv.put("type",item.getType());
        cv.put("image",item.getImage());
        cv.put("weight",item.getWeight());
        cv.put("taste",item.getTaste());

        return mDB.update(table_name,cv,"gname=?",new String[]{name})>0;
//        String sql="update goods set price=?,desc=?,type=?,image=?,weight=?,taste=?) where gname=?";
//        mDB.execSQL(sql,new Object[]{item.price,item.desc,item.type,item.image,item.weight,item.taste,item.Gname});
//        flag=mDB.update(table_name,cv,name,null);
    }

    public boolean updataNum(Goods item){
        openWriteLink();
        ContentValues cv=new ContentValues();
        cv.put("gname",item.getGname());
        cv.put("price",item.getPrice());
        cv.put("desc",item.getDesc());
        cv.put("salenum",item.getSalenum());
        cv.put("type",item.getType());
        cv.put("image",item.getImage());
        cv.put("weight",item.getWeight());
        cv.put("taste",item.getTaste());
        return mDB.update(table_name,cv,"gname=?",new String[]{item.getGname()})>0;
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
                good.setGid(cursor.getInt(cursor.getColumnIndex("_id")));
                good.setGname(cursor.getString(cursor.getColumnIndex("gname")));
                good.setPrice(cursor.getDouble(cursor.getColumnIndex("price")));
                good.setDesc(cursor.getString(cursor.getColumnIndex("desc")));
                good.setSalenum(cursor.getInt(cursor.getColumnIndex("salenum")));
                good.setType(cursor.getString(cursor.getColumnIndex("type")));
                good.setImage(cursor.getString(cursor.getColumnIndex("image")));
                good.setWeight(cursor.getString(cursor.getColumnIndex("weight")));
                good.setTaste(cursor.getString(cursor.getColumnIndex("taste")));
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
        if (cursor.moveToNext()){
            Goods good=new Goods();
//            good.setGid(cursor.getInt(cursor.getColumnIndex("_id")));
            good.setGname(cursor.getString(cursor.getColumnIndex("gname")));
            good.setPrice(cursor.getDouble(cursor.getColumnIndex("price")));
            good.setDesc(cursor.getString(cursor.getColumnIndex("desc")));
            good.setSalenum(cursor.getInt(cursor.getColumnIndex("salenum")));
            good.setType(cursor.getString(cursor.getColumnIndex("type")));
            good.setImage(cursor.getString(cursor.getColumnIndex("image")));
            good.setWeight(cursor.getString(cursor.getColumnIndex("weight")));
            good.setTaste(cursor.getString(cursor.getColumnIndex("taste")));
            list.add(good);
        }
        return list;
    }
}
