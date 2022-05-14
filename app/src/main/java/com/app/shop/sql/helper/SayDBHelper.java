package com.app.shop.sql.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.app.shop.sql.Dao.SayDao;
import com.app.shop.sql.table.Say;

import java.util.ArrayList;
import java.util.List;

/**
 * create by 呵呵 on 2022/4/17.
 */
public class SayDBHelper extends BaseDBHelper implements SayDao {
    private static final String DBname = "shop.db";
    private static final int DB_VERSION = 1;
    private final String table_name = "say";
    private static SayDBHelper mHelper = null;
    private SQLiteDatabase mDB = null;

    public SayDBHelper(@Nullable Context context) {
        super(context);
    }

    public static SayDBHelper getInstance(Context context){
        if (mHelper==null){
            mHelper=new SayDBHelper(context);
        }
        return mHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        String drop = "drop table if exists " + table_name + ";";
//        sqLiteDatabase.execSQL(drop);
//        String create=String.format("create table if not exists %s("+
//                "_id integer primery key autoincrement not null,"+
//                "gid integer,oid integer,datatime varchar,str varchar)");
//        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void openReadLink() {
        if (mDB==null||!mDB.isOpen()){
            mDB=mHelper.getReadableDatabase();
        }
    }

    public void openWriteLink() {
        if (mDB==null||!mDB.isOpen()){
            mDB=mHelper.getWritableDatabase();
        }
    }

    public void closeLink() {
        if (mDB.isOpen()) {
            mDB.close();
        }
        if (mDB != null) {
            mDB = null;
        }
    }

    @Override
    public boolean insert(Say say) {
        openWriteLink();
        ContentValues cv=new ContentValues();
        cv.put("gid",say.getGid());
        cv.put("oid",say.getOid());
        cv.put("datetime",say.getDatetime());
        cv.put("str",say.getStr());
        if (mDB.insert(table_name,null,cv)>0){
            return true;
        }else {
            return false;
        }
//        String sql="insert into say(gid,oid,datatime,str) values(?,?,?,?)";
//        mDB.execSQL(sql,new Object[]{say.gid,say.oid,say.datatime,say.str});
    }

    @Override
    public boolean delete(String condition) {
        return false;
    }

    @Override
    public boolean update(Say item, String name) {
        return false;
    }

    @Override
    public List<Say> queryAll() {
        return null;
    }

    @Override
    public List<Say> queryBy(String conditon) {
        return null;
    }

    public List<Say> findByOrder(int id){
        openReadLink();
        openWriteLink();
        List<Say> list=new ArrayList<>();
//        String sql=String.format("select _id,datatime,str from %s where oid=%s;",table_name,id);
//        Cursor cursor=mDB.rawQuery(sql,null);
        Cursor cursor=mDB.query(table_name,null,"oid=?",new String[]{String.valueOf(id)},null,null,"_id desc");
        if (cursor!=null){
            while (cursor.moveToNext()){
                Say say=new Say();
                say.setSid(cursor.getInt(cursor.getColumnIndex("_id")));
                say.setDatetime(cursor.getString(cursor.getColumnIndex("datetime")));
                say.setStr(cursor.getString(cursor.getColumnIndex("str")));
                list.add(say);
            }
        }
        return list;
    }
    public List<Say> findByGood(int id){
        openReadLink();
        openWriteLink();
        List<Say> list=new ArrayList<>();
//        String sql=String.format("select _id,datatime,str from %s where gid=%s;",table_name,id);
//        Cursor cursor=mDB.rawQuery(sql,null);
        Cursor cursor=mDB.query(table_name,null,"gid=?",new String[]{String.valueOf(id)},null,null,"_id desc");
        if (cursor!=null) {
            while (cursor.moveToNext()) {
                Say say = new Say();
                say.setSid(cursor.getInt(cursor.getColumnIndex("_id")));
                say.setDatetime(cursor.getString(cursor.getColumnIndex("datetime")));
                say.setStr(cursor.getString(cursor.getColumnIndex("str")));
                list.add(say);
            }
        }
        return list;
    }
}
