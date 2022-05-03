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

    private SayDBHelper(@Nullable Context context) {
        super(context, DBname, null, DB_VERSION);
    }
    private SayDBHelper(Context context, int version){
        super(context,DBname,null,version);
    }

    public static SayDBHelper getInstance(Context context,int version){
        if (mHelper==null &&version>0){
            mHelper=new SayDBHelper(context,version);
        }else {
            mHelper=new SayDBHelper(context);
        }
        return mHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String drop = "drop table if exists " + table_name + ";";
        sqLiteDatabase.execSQL(drop);
        String create=String.format("create table if not exists %s("+
                "_id integer primery key autoincrement not null,"+
                "gid integer,oid integer,datatime varchar,str varchar)");
        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public SQLiteDatabase openReadLink() {
        if (mDB==null||!mDB.isOpen()){
            mDB=mHelper.getReadableDatabase();
        }
        return mDB;
    }

    @Override
    public SQLiteDatabase openWriteLink() {
        if (mDB==null||!mDB.isOpen()){
            mDB=mHelper.getWritableDatabase();
        }
        return mDB;
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
    public boolean insert(Say say) {
        openWriteLink();
        ContentValues cv=new ContentValues();
        cv.put("gid",say.gid);
        cv.put("oid",say.oid);
        cv.put("datatime",say.datatime);
        cv.put("str",say.sid);
        if (mDB.insert(table_name,null,cv)>0){
            closeLink();
            return true;
        }else {
            closeLink();
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
        Cursor cursor=mDB.query(table_name,null,"oid=?",new String[]{String.valueOf(id)},null,null,null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                Say say=new Say();
                say.sid=cursor.getInt(cursor.getColumnIndex("rowid"));
                say.datatime=cursor.getString(cursor.getColumnIndex("datatime"));
                say.str=cursor.getString(cursor.getColumnIndex("str"));
                list.add(say);
            }
        }closeLink();
        return list;
    }
    public List<Say> findByGood(int id){
        openReadLink();
        openWriteLink();
        List<Say> list=new ArrayList<>();
//        String sql=String.format("select _id,datatime,str from %s where gid=%s;",table_name,id);
//        Cursor cursor=mDB.rawQuery(sql,null);
        Cursor cursor=mDB.query(table_name,null,"gid=?",new String[]{String.valueOf(id)},null,null,null);
        if (cursor!=null) {
            while (cursor.moveToNext()) {
                Say say = new Say();
                say.sid = cursor.getInt(cursor.getColumnIndex("rowid"));
                say.datatime = cursor.getString(cursor.getColumnIndex("datatime"));
                say.str = cursor.getString(cursor.getColumnIndex("str"));
                list.add(say);
            }
        }closeLink();
        return list;
    }
}
