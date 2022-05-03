package com.app.shop.sql.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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

    public UserDBHelper(Context context, int version) {
        super(context, DBname, null, version);
    }

    public UserDBHelper(Context context) {
        super(context, DBname, null, DB_VERSION);
    }

    public static UserDBHelper getInstance(Context context, int version) {
        if (mHelper == null && version > 0) {
            mHelper = new UserDBHelper(context, version);
        } else if (mHelper == null) {
            mHelper = new UserDBHelper(context);
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
        String drop = "drop table if exists " + table_name + ";";
        sqLiteDatabase.execSQL(drop);
        String create = "create table if not exists " + table_name + "(" +
                "_id integer primary key autoincrement not null,"
                + "Uname varcher)";
        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    @Override
    public boolean insert(User user) {
        openWriteLink();
        ContentValues cv=new ContentValues();
        cv.put("name",user.Uname);

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
}
