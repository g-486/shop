package com.app.shop.sql.helper;

import android.content.Context;
import android.database.Cursor;
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
    public int delete(String condition) {
        return 0;
    }

    @Override
    public long insert(List list) {
        return 0;
    }

    @Override
    public int update(Object item, String name) {
        return 0;
    }

    @Override
    public List query(String... condition) {
        return null;
    }

    @Override
    public User queryBy(String conditon) {
        if (mDB.isOpen()) {
            User user = new User();
            String sql = "select * from " + table_name + " where _id=?" + conditon;
            Cursor cursor = mDB.rawQuery(sql, null);
            user.setUid(cursor.getInt(0));
            user.setUname(cursor.getString(1));
            return user;
        } else {
            openReadLink();
            openWriteLink();
            return queryBy(conditon);
        }
    }
}
