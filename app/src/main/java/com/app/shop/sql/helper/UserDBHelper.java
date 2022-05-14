package com.app.shop.sql.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.app.shop.sql.Dao.UserDao;
import com.app.shop.sql.table.User;

import java.util.List;

/**
 * create by 呵呵 on 2022/3/19.
 */
public class UserDBHelper extends BaseDBHelper implements UserDao {
    private static final String DBname = "shop.db";
    private static final int DB_VERSION = 1;
    private final String table_name = "user";
    private static UserDBHelper mHelper = null;
    private SQLiteDatabase mDB = null;

    public UserDBHelper(@Nullable Context context) {
        super(context);
    }

    public static UserDBHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new UserDBHelper(context);
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
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        String drop = "drop table if exists " + table_name + ";";
//        sqLiteDatabase.execSQL(drop);
//        String create = "create table if not exists " + table_name + "(" +
//                "_id integer primary key autoincrement not null,"
//                + "uname varcher)";
//        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    @Override
    public boolean insert(User user) {
        openWriteLink();
        ContentValues cv=new ContentValues();
        cv.put("uname",user.getUname());
        return mDB.insert(table_name,null,cv)>0;
    }

    @Override
    public boolean delete(String condition) {
        return false;
    }

    @Override
    public boolean update(User item, String name) {
        return false;
    }

    @Override
    public List<User> queryAll() {
        return null;
    }

    @Override
    public List<User> queryBy(String conditon) {
        return null;
    }

    public User findById(String id){
        openReadLink();
        User user=new User();
        Cursor cursor=mDB.query(table_name,null,"_id=?",new String[]{id},null,null,null);
        if (cursor!=null){
            cursor.moveToNext();
            user.setUname(cursor.getString(cursor.getColumnIndex("uname")));
            user.setUid(cursor.getInt(cursor.getColumnIndex("_id")));
        }
        return user;
    }
    public User findByName(String name){
        openReadLink();
        User user=new User();
        Cursor cursor=mDB.query(table_name,null,"uname=?",new String[]{name},null,null,null);
        if (cursor!=null){
            cursor.moveToNext();
            user.setUname(cursor.getString(cursor.getColumnIndex("uname")));
            user.setUid(cursor.getInt(cursor.getColumnIndex("_id")));
        }
        return user;
    }
}
